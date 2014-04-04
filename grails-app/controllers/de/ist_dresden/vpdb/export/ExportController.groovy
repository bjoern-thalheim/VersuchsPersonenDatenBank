package de.ist_dresden.vpdb.export

import java.text.DateFormat;
import java.util.Locale;

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import de.ist_dresden.vpdb.entity.Versuchsperson;
import de.ist_dresden.vpdb.logic.VersuchsPersonenToIdHelper;

class ExportController {
	
	def exportService
	
	def exportVersuchspersonen = {
		response.contentType = ConfigurationHolder.config.grails.mime.types[params.format] 
		response.setHeader("Content-disposition", "attachment; filename=versuchspersonen.${params.format}")
		
		def versuchspersonen = VersuchsPersonenToIdHelper.idsToVersuchspersonen(session.currentVersuchspersonenIds)
		
		List fields
		Map labels = ["nachname": "Name", "vorname": "Vorname", "adresse": "Adresse", "geschlecht":"Geschlecht", 
		              "geburtsdatum":"Geburtsdatum", "telefonnummer":"Telefonnummer", "emailadresse":"EMail" ,
		              "inStudie":"In Rekrutierungs-Liste aufgenommen", "blacklisted":"Schwarze Liste", 
					  "letzterKontakt":"Letzer Kontakt", "letzterKontaktNotizen":"Letzter Kontakt Notizen", 
					  "besonderheiten":"Besonderheiten", "altersklasse":"Schueler oder Erwachsener", 
					  "kind":"Bildung Schueler", "erwachsener":"Bildung Erwachsener", "studien": "Studien" ]
		
		if (params.exportAll) {
			fields = ["nachname", "vorname", "telefonnummer", "emailadresse", "geschlecht", "geburtsdatum", 
				      "adresse", "inStudie", "studien", "letzterKontakt", "letzterKontaktNotizen", 
					  "besonderheiten", "altersklasse", "kind", "erwachsener", "blacklisted" ] 
		} else {
			fields = ["nachname", "vorname", "geschlecht", "geburtsdatum", "emailadresse", "telefonnummer", "studien", 
				"blacklisted", "besonderheiten", "Telefonkontakt", "Teilnahme" ] 
		}
		
		def addressFormatter = { value ->
			def result = ""
			
			if(value) {
				result += value.strasseUndHausnummer + ", "
				result += value.plz + ", "
				result += value.ort
			}
			
			return result
		}
		
		def datumFormatter = {
			value ->
			def result = ""
			if(value) {
				result = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN).format(value)
			}
		}
		
		def firstCapitalFormatter = {
			value ->
			def result = value.toString()
			if (value) {
				result = value.toString().substring(0, 1) + value.toString().substring(1).toLowerCase()
			}
		}
		
		def blankFormatter = {
			value -> ""
		}
		
		def booleanFormatter = {
			value ->
			def result = ""
			if (value) {
				result = "Ja"
			} else {
				result = "Nein"
			}
		}
		
		def onlyFirstLetterFormatter = {
			value ->
			def result = ""
			if (value) {
				result = value.toString().substring(0,1).toLowerCase()
			}
		}
		
		def kindFormatter = {
			value ->
			def result = ""
			if (value) {
				result += firstCapitalFormatter(value.schulform)
				result += ", " + value.genaueAngabe
			}
		}
		
		def erwachsenerFormatter = {
			value ->
			def result = ""
			if (value) {
				result += firstCapitalFormatter(value.abschluss)
				result += " (" + value.ausbildungsjahre + " Jahre)"
				result += ", " + value.genaueAngabe
			}
		}
		
		Map formatters = ["adresse":addressFormatter, "geburtsdatum":datumFormatter, "letzterKontakt":datumFormatter, 
		                  "altersklasse":firstCapitalFormatter, "inStudie":booleanFormatter, "blacklisted":booleanFormatter,
						  "geschlecht":onlyFirstLetterFormatter, "kind":kindFormatter, "erwachsener":erwachsenerFormatter, 
						  "Telefonkontakt":blankFormatter, "Teilnahme":blankFormatter]
		Map parameters = [:]
		
		exportService.export(params.format, response.outputStream, versuchspersonen, fields, labels, formatters, parameters)
	}
}
