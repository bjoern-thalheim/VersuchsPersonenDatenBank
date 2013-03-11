package de.ist_dresden.vpdb.data;

import java.sql.Date;

import de.ist_dresden.vpdb.entity.Erwachsener;
import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.NodeChild;
import de.ist_dresden.vpdb.entity.Adresse;
import de.ist_dresden.vpdb.entity.Altersklasse;
import de.ist_dresden.vpdb.entity.Bildungsabschluss;
import de.ist_dresden.vpdb.entity.Geschlecht;
import de.ist_dresden.vpdb.entity.Kind;
import de.ist_dresden.vpdb.entity.Studie;
import de.ist_dresden.vpdb.entity.Test;
import de.ist_dresden.vpdb.entity.Versuchsperson;
import de.ist_dresden.vpdb.entity.VersuchspersonenList;

/**
 * Liest uebergebene XML-Files ein und speichert die Daten in der DB.
 */
class DataImporter {
	
	static void importStudien(String studienAsXml) {
		def studien = new XmlSlurper().parseText(studienAsXml)
		def alleStudien = studien.studie
        
        Studie.withTransaction{
    		for(String studieAsXml:alleStudien) {
    			def studienId = studieAsXml.id.text()
    			def studie = new Studie()
    			studie.name = studieAsXml.name.text()
    			studie.year = Date.valueOf(studieAsXml.year.text())
    			def testsFuerStudie = studieAsXml.tests.test
    			for (String testAsXml:testsFuerStudie) {
    				def testName = testAsXml.text()
    				def test = Test.findByName(testName)
    				if (!test) {
    					test = new Test()
    					test.name = testName
    					test.save(flush:true)
    				}
    				test.addToStudien(studie)
    			}
    			studie.save(flush:true, validate:false)
    		}
        }
	}
	
	static void importVersuchspersonen(String versuchspersonenAsXml) {
		def versuchspersonen = new XmlSlurper().parseText(versuchspersonenAsXml)
		def alleVersuchspersonen = versuchspersonen.versuchsperson
        
        Versuchsperson.withTransaction{
    		for(int i=0; i<alleVersuchspersonen.size(); i++) {
    			def vp = new Versuchsperson()
    			def vpXml = versuchspersonen.versuchsperson[i]
    			def studienFuerVP = vpXml.studien.studie
    			for (int j=0; j<studienFuerVP.size(); j++) {
    				def studie = Studie.findByName(studienFuerVP[j].text())
    				if(studie) {
    					vp.addToStudien(studie)
    				}
    			}
    			def listsFuerVP = vpXml.vpLists.vpList
    			for (int j=0; j<listsFuerVP.size(); j++) {
    				def vpList = VersuchspersonenList.findByName(listsFuerVP[j].text())
    				if(!vpList) {
						vpList = new VersuchspersonenList()
						vpList.setName(listsFuerVP[j].text())
    				}
    				vp.addToVpList(vpList)
    			}
    			
    			vp.properties = [nachname:vpXml.nachname.text(),
    					vorname:vpXml.vorname.text(),
    					emailadresse:vpXml.emailadresse.text(),
    					telefonnummer:vpXml.telefonnummer.text(),
    					inStudie:Boolean.valueOf(vpXml.inStudie.text()),
    					blacklisted:Boolean.valueOf(vpXml.blacklisted.text()),
    					letzterKontakt:Date.valueOf(vpXml.letzterKontakt.text()),
    					altersklasse:Altersklasse.valueOf(vpXml.altersklasse.text()),
    					geschlecht:vpXml.geschlecht.text(),
    					geburtsdatum:Date.valueOf(vpXml.geburtsdatum.text()),
    					letzterKontaktNotizen:vpXml.letzterKontaktNotizen.text(),
    					besonderheiten:vpXml.besonderheiten.text()
    					]
    			if (vp.altersklasse == Altersklasse.ERWACHSENER) {
    				Erwachsener erwachsener = new Erwachsener()
    				erwachsener.properties = [
    						abschluss:vpXml.bildung.schulabschluss.text(),
    						genaueAngabe:vpXml.bildung.genaueAngabe.text(),
    						ausbildungsjahre:vpXml.bildung.ausbildungsjahre.text()
    						]
    				erwachsener.save(validate:false)
    				vp.erwachsener = erwachsener
    			}
    			if (vp.altersklasse == Altersklasse.KIND) {
    				Kind kind = new Kind()
    				kind.properties = [genaueAngabe:vpXml.bildung.genaueAngabe.text(),
    						schulform:vpXml.bildung.schulform.text()]
    				kind.save(validate:false)
    				vp.kind = kind
    			}
    			Adresse adresse = new Adresse()
    			adresse.properties = [strasseUndHausnummer:vpXml.adresse.strasse.text(),
    					plz:vpXml.adresse.plz.text(),
    					ort:vpXml.adresse.ort.text()]
    			adresse.save(validate:false)
    			vp.adresse = adresse
    			vp.save(validate:false)
    		}
        }
	}
	
	static void importData(String personenfile, String studienfile) {
		importStudien(new File(studienfile).text)
		importVersuchspersonen(new File(personenfile).text)
	}
}
