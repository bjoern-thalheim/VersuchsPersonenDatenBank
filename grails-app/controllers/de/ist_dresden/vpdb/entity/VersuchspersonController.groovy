package de.ist_dresden.vpdb.entity

import java.util.Collections;
import de.ist_dresden.vpdb.entity.Adresse;
import de.ist_dresden.vpdb.entity.Versuchsperson;
import de.ist_dresden.vpdb.entity.Erwachsener;
import de.ist_dresden.vpdb.entity.Kind;

import de.ist_dresden.vpdb.logic.IdConversionHelper;
import de.ist_dresden.vpdb.logic.ParamsListExtractionHelper;
import de.ist_dresden.vpdb.logic.SearchController;
import de.ist_dresden.vpdb.logic.VersuchsPersonenToIdHelper;

class VersuchspersonController {
    
    def index = {
        redirect(action:list)
    }
    
    def list = {
    	def aktuelleVPs
		// Null bedeutet Start, leer ist OK, daher ist !session.currentVersuchspersonenIds falsch.
		if (session.currentVersuchspersonenIds == null) {
			aktuelleVPs = Versuchsperson.findAll()
        	session.currentVersuchspersonenIds = VersuchsPersonenToIdHelper.versuchspersonenToIds (aktuelleVPs)
		} else {
			aktuelleVPs = VersuchsPersonenToIdHelper.idsToVersuchspersonen (session.currentVersuchspersonenIds)
		}
			
		aktuelleVPs.sort {
          it.getProperty(params.sort ? params.sort : 'nachname')
        }
        if (params.order == 'desc') {
            aktuelleVPs = aktuelleVPs.reverse()
        }
		
		session.currentVersuchspersonenIds = VersuchsPersonenToIdHelper.versuchspersonenToIds (aktuelleVPs)
		
        exportIfNecessary(params.format)
		
        renderVersuchspersonen()
    }
    
    def exportIfNecessary(def format) {
        if(params?.format && params.format != "html"){ 
            chain(controller:'export', action:'exportVersuchspersonen', params:params)
        }
    }
    
    def listSpecific = {
		exportIfNecessary(params.format)
		if (params.selectedIds) {
			session.currentVersuchspersonenIds = ParamsListExtractionHelper.getListOfStringsFromParams(params.selectedIds)
		}
		if (params.setAttributes) {
			updateBatch()
		}
        
        def aktuelleVPs = VersuchsPersonenToIdHelper.idsToVersuchspersonen (session.currentVersuchspersonenIds)        
        aktuelleVPs.sort {
            it.getProperty(params.sort ? params.sort : 'nachname')
        }
        if (params.order == 'desc') {
            aktuelleVPs = aktuelleVPs.reverse()
        }        
        session.currentVersuchspersonenIds = VersuchsPersonenToIdHelper.versuchspersonenToIds (aktuelleVPs)
        
		renderVersuchspersonen()
    }
    
    private renderVersuchspersonen () {
        def availableStudien = Studie.findAll()
		def vpsToDisplay = VersuchsPersonenToIdHelper.idsToVersuchspersonen (session.currentVersuchspersonenIds)
        render(view:'list', model:['versuchspersonInstanceList':vpsToDisplay, 'availableStudien':availableStudien])
    }
    
    def createorupdate = {
        chain(action:'edit')
    }
    
    def edit = {
        def person 
        if (params.id) {
            person = Versuchsperson.findById(params.id)
        }
        def adresse
        def availableStudien = Studie.findAll()
        
        if (person) {
            adresse = person.adresse
            availableStudien.removeAll(person.studien)
        }
        
        render(view:'createorupdate', model:[versuchspersonInstance:person, adresseInstance:adresse, "availableStudien":availableStudien])
    }
    
    def cancel = {
        redirect(action:list)    
    }
    
    def delete = {
        if (params.id) {
            def person = Versuchsperson.findById(params.id)
            if(person) {
            	session.currentVersuchspersonenIds.remove(person.id)
                person.delete()
                flash.message = person.nachname + ", " + person.vorname + " geloescht."
            }
        }
        redirect(action:list)
    }
    
    def save = {
        Versuchsperson person = findOrCreatePerson()
        
        def availableStudien = Studie.findAll()
        if (person.studien)
        	availableStudien.removeAll(person.studien)
        
        Adresse adresse = getAdresseForPerson(person)
        Kind kind = getKindForPerson(person)
        Erwachsener erwachsener = getErwachenserForPerson(person)
        
        person.properties = params
        adresse.properties = [plz:params.plz , ort:params.ort , strasseUndHausnummer:params.strasseUndHausnummer]
        erwachsener.properties = [ausbildungsjahre:params.'erwachsener:ausbildungsjahre', 
        genaueAngabe:params.'erwachsener:genaueAngabe' , 
        abschluss:params.'erwachsener:schulabschluss']
        kind.properties = [schulform:params.'kind:schulform' , genaueAngabe:params.'kind:genaueAngabe']
        
        if(!adresse.validate()) {
            flash.error = "adresse.validation.error"
            render(view:'createorupdate', model:[versuchspersonInstance:person, 
            adresseInstance:adresse, 
            erwachsenerInstance:erwachsener, 
            kindInstance:kind, 
            "availableStudien":availableStudien])
            return
        }
        // Wenn jemand die Altersklasse per Tastatur umstellt, wird leider darunter nicht die Bildungsinformationsbox getauscht.
        // Dies erfolgt leider nur onClick. Dann sind die abgespeicherten Daten inkonsistent.
        // Dieses Szenario erkennen wir an dieser Bedingung und kehren einfach zur Eingabemaske zurück.
        // Dann wird auch schon die richtige Bildungsinformationsbox angezeigt.
		if ((person.altersklasse == Altersklasse.KIND && !kind.schulform) ||
			(person.altersklasse == Altersklasse.ERWACHSENER && !erwachsener.abschluss)) {
            flash.error = "bildung.validation.consistency.error"
            render(view:'createorupdate', model:[versuchspersonInstance:person, 
            adresseInstance:adresse, 
            erwachsenerInstance:erwachsener, 
            kindInstance:kind, 
            "availableStudien":availableStudien])
            return
        }
        if (!erwachsener.validate() || !kind.validate()) {
            flash.error = "bildung.validation.error"
            render(view:'createorupdate', model:[versuchspersonInstance:person, 
            adresseInstance:adresse, 
            erwachsenerInstance:erwachsener, 
            kindInstance:kind, 
            "availableStudien":availableStudien])
            return
        }
        
        person.adresse = adresse
        person.erwachsener = erwachsener
        person.kind = kind
        
        if(!person.validate()) {
            flash.error = "versuchsperson.validation.error"
            render(view:'createorupdate', model:[versuchspersonInstance:person, 
            adresseInstance:adresse, 
            erwachsenerInstance:erwachsener, 
            kindInstance:kind, 
            "availableStudien":availableStudien])
            return
        } else {
            person.studien = []
			def studienToAttach = ParamsListExtractionHelper.getListOfStringsFromParams(params.selectedStudien)
            studienToAttach.each {
                if (it.isLong()) {
                    def studie = Studie.findById(it.toLong())
                    if (studie) {
                        person.addToStudien(studie)
                    }
                }
            }
            adresse.save()
            kind.save()
            erwachsener.save()
            person.save()
        }
		        
		if (params.id) {
			redirect(view:'list')
		} else { 
			chain(action:'edit')
		}
    }
    
    def Adresse getAdresseForPerson(Versuchsperson person) {
        Adresse adresse = person.adresse
        if(!adresse) {
            adresse = new Adresse()
        }
        return adresse
    }
    
    def Erwachsener getErwachenserForPerson (Versuchsperson person) {
        Erwachsener erwachsener = person.erwachsener
        if (!erwachsener) {
            erwachsener = new Erwachsener()
        }
        person.erwachsener = erwachsener
        return erwachsener
    }
    
    def Kind getKindForPerson(Versuchsperson person) {
        Kind kind = person.kind
        if (!kind) {
            kind = new Kind()
        }
        person.kind = kind
        return kind
    }
    
    def Versuchsperson findOrCreatePerson() {
        Versuchsperson person
        if (params.id) {
            person = Versuchsperson.findById(params.id)
        }
        if(!person) {
            person = new Versuchsperson()
        }
        return person
    }
    
    def showBildung = {
        Versuchsperson vp
        if (params.personId != "null") {
            vp = Versuchsperson.findById(params.personId)
        }
        if (params.selectedAltersklasse == Altersklasse.ERWACHSENER.toString()) {
            render(template:'erwachsener', model:[erwachsenerInstance:vp?.erwachsener])        
        } else if (params.selectedAltersklasse == Altersklasse.KIND.toString()) {
            render(template:'kind', model:[kindInstance:vp?.kind])    
        } else {
            render(template:'erwachsener', model:[erwachsenerInstance:vp?.erwachsener])
        }
    }
		
	def updateBatch = {
		def versuchsPersonen = getVersuchsPersonenFromSession()
		for (Versuchsperson versuchsperson: versuchsPersonen) {
			if (params.setInStudie) versuchsperson.properties = [ inStudie:(params.inStudie != null) ]
			if (params.setLetzterKontakt) versuchsperson.properties = [ letzterKontakt:params.letzterKontakt ]
			if (params.setLetzterKontaktNotizen) versuchsperson.properties = [ letzterKontaktNotizen:params.letzterKontaktNotizen ]
			if (params.setStudien && params.selectedStudien) {
				def studienToAppend = Studie.createCriteria().listDistinct { 
					'in'('id', IdConversionHelper.getListOfLongsFromListOfStrings(params.selectedStudien))
				}
				versuchsperson.studien += studienToAppend
			}
			versuchsperson.save()
		}
	}
	
	def getVersuchsPersonenFromSession = {
		return Versuchsperson.createCriteria().listDistinct {
			'in'('id', IdConversionHelper.getListOfLongsFromListOfStrings(session.currentVersuchspersonenIds))
		}
	}
	
	def loadAll = {
		List result = Versuchsperson.findAll()
		session.currentVersuchspersonenIds = VersuchsPersonenToIdHelper.versuchspersonenToIds(result)
		redirect(action:"list")
	}
}
