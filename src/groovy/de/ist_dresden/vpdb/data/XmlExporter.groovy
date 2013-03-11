package de.ist_dresden.vpdb.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import de.ist_dresden.vpdb.entity.Altersklasse 
import de.ist_dresden.vpdb.entity.Studie;
import de.ist_dresden.vpdb.entity.Test;
import de.ist_dresden.vpdb.entity.Versuchsperson 
import de.ist_dresden.vpdb.entity.VersuchspersonenList 

class XmlExporter {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
	
	public static String getDatabaseAsXml() {
		def writer = new StringWriter()
		XMLDataGenerator generator = new XMLDataGenerator(writer)
		generator.openTag("versuchspersonendaten", 0, true)
		writeStudien(generator)
		writeVersuchspersonen(generator)
		generator.closeTag("versuchspersonendaten", 0, true)
		generator.close()
		return writer.toString()
	}
	
	public static void writeStudien(XMLDataGenerator generator) {
		def studien = Studie.findAll()
		for (Studie studie:studien) {
			generator.openTag("studie", 1, true)
			generator.writeTagAndContent("id", 2, Long.toString(studie.id))
			generator.writeTagAndContent("name", 2, studie.name)
			generator.writeTagAndContent("year", 2, df.format(studie.year))
			generator.openTag("tests", 2, true)
			for (Test test:studie.tests) {
				generator.writeTagAndContent("test", 3, test.name)
			}
			generator.closeTag("tests", 2, true)
			generator.closeTag("studie", 1, true)
		}
	}
	
	public static void writeVersuchspersonen(XMLDataGenerator generator) {
		def personen = Versuchsperson.findAll()
		for (Versuchsperson person:personen) {
			generator.openTag("versuchsperson", 1, true)
			if (person.nachname != null) generator.writeTagAndContent("nachname", 2, person.nachname)
			if (person.vorname != null) generator.writeTagAndContent("vorname", 2, person.vorname)
			if (person.geschlecht != null) generator.writeTagAndContent("geschlecht", 2, person.geschlecht.toString())
			if (person.emailadresse != null) generator.writeTagAndContent("emailadresse", 2, person.emailadresse)
			if (person.telefonnummer != null) generator.writeTagAndContent("telefonnummer", 2, person.telefonnummer)
			if (person.geburtsdatum != null) generator.writeTagAndContent("geburtsdatum", 2, df.format(person.geburtsdatum))
			if (person.inStudie != null) generator.writeTagAndContent("inStudie", 2, person.inStudie.toString())
			if (person.blacklisted != null) generator.writeTagAndContent("blacklisted", 2, person.blacklisted.toString())
			if (person.letzterKontakt != null) generator.writeTagAndContent("letzterKontakt", 2, df.format(person.letzterKontakt))
			if (person.letzterKontaktNotizen != null) generator.writeTagAndContent("letzterKontaktNotizen", 2, person.letzterKontaktNotizen)
			if (person.besonderheiten != null) generator.writeTagAndContent("besonderheiten", 2, person.besonderheiten)
			if (person.altersklasse != null) generator.writeTagAndContent("altersklasse", 2, person.altersklasse.toString())
			if (person.adresse != null) generator.openTag("adresse", 2, true)
			if (person.adresse.strasseUndHausnummer != null) generator.writeTagAndContent("strasse", 3, person.adresse.strasseUndHausnummer)
			if (person.adresse.plz != null) generator.writeTagAndContent("plz", 3, person.adresse.plz)
			if (person.adresse.ort != null) generator.writeTagAndContent("ort", 3, person.adresse.ort)
			if (person.adresse != null) generator.closeTag("adresse", 2, true)
			generator.openTag("bildung", 2, true)
			if (person.altersklasse.equals(Altersklasse.KIND) && person.kind != null) {
				if (person.kind.schulform) generator.writeTagAndContent("schulform", 3, person.kind.schulform.toString())
				if (person.kind.genaueAngabe) generator.writeTagAndContent("genaueAngabe", 3, person.kind.genaueAngabe)
			}
			if (person.altersklasse.equals(Altersklasse.ERWACHSENER) && person.erwachsener != null) {
				if (person.erwachsener.ausbildungsjahre) generator.writeTagAndContent("ausbildungsjahre", 3, Integer.toString(person.erwachsener.ausbildungsjahre))
				if (person.erwachsener.genaueAngabe) generator.writeTagAndContent("genaueAngabe", 3, person.erwachsener.genaueAngabe)
				if (person.erwachsener.abschluss) generator.writeTagAndContent("schulabschluss", 3, person.erwachsener.abschluss.toString())
			}
			generator.closeTag("bildung", 2, true);
			if (person.studien) {
				generator.openTag("studien", 2, true)
				for (Studie studie:person.studien) {
					generator.writeTagAndContent("studie", 3, studie.name)
				}
				generator.closeTag("studien", 2, true)
			}
			if (person.vpList) {
				generator.openTag("vpLists", 2, true)
				for (VersuchspersonenList vpList:person.vpList) {
					generator.writeTagAndContent("vpList", 3, vpList.name)
				}
				generator.closeTag("vpLists", 2, true)
			}
			generator.closeTag("versuchsperson", 1, true)
		}
	}

}
