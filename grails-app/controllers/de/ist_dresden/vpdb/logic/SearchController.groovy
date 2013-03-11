package de.ist_dresden.vpdb.logic

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.lang.Boolean;

import de.ist_dresden.vpdb.entity.Bildungsabschluss;
import de.ist_dresden.vpdb.entity.Geschlecht;
import de.ist_dresden.vpdb.entity.Altersklasse;
import de.ist_dresden.vpdb.entity.Schulform;
import de.ist_dresden.vpdb.entity.Versuchsperson;

class SearchController {
	
	public static final int AUSBILDUNGSJAHRE_MAXIMUM = 50
	
	Collection<Integer> getPossibleAusbildungsJahre() {
		Collection<Integer> possibleAusbildungsJahre = new ArrayList<Integer>(AUSBILDUNGSJAHRE_MAXIMUM)
		for (int i = 0; i<AUSBILDUNGSJAHRE_MAXIMUM; i++)
			possibleAusbildungsJahre.add(new Integer(i))
		return possibleAusbildungsJahre
	}
	
	String getNinetyNineYearsAgoAsString() {
		def cal = Calendar.getInstance()
		def ninetyNineYearsAgo = cal.get(Calendar.YEAR) - 99
		String.valueOf(ninetyNineYearsAgo)
	}
	
	Date minimumDatum = Date.valueOf(getNinetyNineYearsAgoAsString() + "-01-01")
	Date geburtsdatumuntergrenze = minimumDatum
	Date letzterkontaktuntergrenze = minimumDatum
	Collection<Integer> possibleAusbildungsJahre = getPossibleAusbildungsJahre()
	
	def index = {
		redirect(action:search, model:["geburtsdatumuntergrenze":geburtsdatumuntergrenze, "letzterkontaktuntergrenze":letzterkontaktuntergrenze, "possibleAusbildungsJahre":possibleAusbildungsJahre])
	}
	
	def search = {
	}
	
	def cancel = {
		redirect(action:search, model:["geburtsdatumuntergrenze":geburtsdatumuntergrenze, "letzterkontaktuntergrenze":letzterkontaktuntergrenze, "possibleAusbildungsJahre":possibleAusbildungsJahre])        
	}
	
	def alleVersuchspersonen
	
	def execute = {
		alleVersuchspersonen = Versuchsperson.findAll()
		
		List result = alleVersuchspersonen
		if (params.whitelisttest) result.retainAll(getPersonenWithTests(params.whitelisttest))
		if (params.blacklisttest) result.removeAll(getPersonenWithTests(params.blacklisttest))
		if (params.whiteliststudien) result.retainAll(getPersonenWithStudien(params.whiteliststudien))
		if (params.blackliststudien) result.removeAll(getPersonenWithStudien(params.blackliststudien))
		if (!params.geschlecht.equals("BOTH")) result.retainAll(getPersonenWithGeschlecht(params.geschlecht))
		if (params.geburtsdatumuntergrenze) result.retainAll(getPersonenYoungerThan(params.geburtsdatumuntergrenze))
		if (params.geburtsdatumobergrenze) result.retainAll(getPersonenOlderThan(params.geburtsdatumobergrenze))
		if (!params.inStudie.equals("BOTH")) result.retainAll(getPersonenInStudie(params.inStudie))
		if (!params.blacklisted.equals("BOTH")) result.retainAll(getPersonenBlacklisted(params.blacklisted))
		if (params.letzterkontaktuntergrenze) result.retainAll(getPersonenAfterLetzterKontakt(params.letzterkontaktuntergrenze))
		if (params.letzterkontaktobergrenze) result.retainAll(getPersonenBeforeLetzterKontakt(params.letzterkontaktobergrenze))
		if (!params.altersklasse.equals("BOTH")) result.retainAll(getPersonenWithAltersklasse(params.altersklasse))
		if (!params.altersklasse.equals("BOTH")) result.retainAll(getPersonenAccordingToBildungsdaten())
		
		if(params?.format && params.format != "html"){ 
			chain(controller:'export', action:'exportVersuchspersonen', params:params, model:[versuchspersonen:result])
		}        
		
		session.currentVersuchspersonenIds = VersuchsPersonenToIdHelper.versuchspersonenToIds(result)
		
		redirect(controller:"versuchsperson", action:"list")
	}
	
	def getPersonenWithGeschlecht = {
		geschlecht -> 
		assert geschlecht != null
		assert (!geschlecht.equals("BOTH"))
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct { 
			eq('geschlecht', Geschlecht.valueOf(geschlecht))
		}
	}
	
	def getPersonenAccordingToBildungsdaten = {
		assert (params.altersklasse != null)
		assert (!params.altersklasse.equals("BOTH"))
		if (Altersklasse.valueOf(params.altersklasse) == Altersklasse.KIND) {
			return params.schulformen ? getPersonenWithSchulform(params.schulformen) : alleVersuchspersonen
		} else if (Altersklasse.valueOf(params.altersklasse) == Altersklasse.ERWACHSENER) {
			Set result = alleVersuchspersonen
			if (params.schulabschluesse) result.retainAll(getPersonenWithSchulabschluss(params.schulabschluesse))
			if (params.bildungsjahre_upper) result.retainAll(getPersonenAccordingToBildungsjahreObergrenze(params.bildungsjahre_upper))
			if (params.bildungsjahre_lower) result.retainAll(getPersonenAccordingToBildungsjahreUntergrenze(params.bildungsjahre_lower))
			return result
		} else {
			return alleVersuchspersonen
		}
	}
	
	def getPersonenAccordingToBildungsjahreObergrenze = {
		obergrenzeParam ->
		assert (obergrenzeParam != null)
		Versuchsperson.createCriteria().listDistinct { 
			erwachsener {
				'le'('ausbildungsjahre', Integer.valueOf(obergrenzeParam))
			}
		}
	}
	
	def getPersonenAccordingToBildungsjahreUntergrenze = {
		untergrenzeParam ->
		assert (untergrenzeParam != null) 
		Versuchsperson.createCriteria().listDistinct { 
			erwachsener {
				'ge'('ausbildungsjahre', Integer.valueOf(untergrenzeParam))
			}
		}
	}
	
	def getPersonenWithSchulabschluss = {
		schulabschluesse ->
		assert (schulabschluesse != null)
		Set<Bildungsabschluss> abschluesse = Bildungsabschluss.getBildungsAbschluesse(schulabschluesse)
		Versuchsperson.createCriteria().listDistinct { 
			erwachsener {
				'in'('abschluss', abschluesse)
			}
		}
	}
	
	def getPersonenWithSchulform = {
		schulformen ->
		assert (schulformen != null)
		Set<Schulform> schulen = Schulform.getSchulFormen(schulformen)
		Versuchsperson.createCriteria().listDistinct { 
			kind {
				'in'('schulform', schulen)
			}
		}
	}
	
	def getPersonenWithAltersklasse = {
		altersklasse ->
		assert altersklasse != null
		assert (!altersklasse.equals("BOTH"))
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct { 
			eq('altersklasse', Altersklasse.valueOf(altersklasse))
		}
	}
	
	def getPersonenYoungerThan = {
		geburtsdatumUntergrenze ->
		assert geburtsdatumUntergrenze != null
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			ge('geburtsdatum', geburtsdatumUntergrenze)
		}
	}
	
	def getPersonenOlderThan = {
		geburtsdatumObergrenze ->
		assert geburtsdatumObergrenze != null
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			le('geburtsdatum', geburtsdatumObergrenze)
		}
	}
	
	def getPersonenBeforeLetzterKontakt = {
		letzterKontaktObergrenze ->
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			le('letzterKontakt', letzterKontaktObergrenze)
		}
	}
	
	def getPersonenAfterLetzterKontakt = {
		letzterKontaktUntergrenze ->
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			ge('letzterKontakt', letzterKontaktUntergrenze)
		}
	}
	
	def getPersonenInStudie = {
		inStudie ->
		assert inStudie != null
		assert (!inStudie.equals("BOTH"))
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			eq('inStudie', Boolean.parseBoolean(inStudie))
		}
	}
	
	def getPersonenBlacklisted = {
		blacklisted ->
		assert blacklisted != null
		assert (!blacklisted.equals("BOTH"))
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			eq('blacklisted', Boolean.parseBoolean(blacklisted))
		}
	}
	
	/**
	 * Gibt alle Personen zurueck, welche an mindestens einem der gegebenen Tests teilgenommen haben.
	 * 
	 * @param testIdsAsStrings Darf nicht null sein.
	 */
	def getPersonenWithTests = {
		testIdsAsStrings -> 
		assert testIdsAsStrings != null
		return getPersonsWithOneOfTheseTests(IdConversionHelper.getListOfLongsFromListOfStrings(testIdsAsStrings))
	}
	
	def getPersonsWithOneOfTheseTests = {
		testIds ->
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct { 
			studien {
				tests {
					'in'('id', testIds)
				} 
			}
		}		
	}
	
	/**
	 * Gibt alle Personen zurueck, welche an mindestens einer der gegebenen Studien teilgenommen haben.
	 * 
	 * @param studienIdsAsStrings Darf nicht null sein.
	 */
	def getPersonenWithStudien = {
		studienIdsAsStrings ->
		assert studienIdsAsStrings != null
		return getPersonsWithOneOfTheseStudien(IdConversionHelper.getListOfLongsFromListOfStrings(studienIdsAsStrings))
	}
	
	def getPersonsWithOneOfTheseStudien = {
		studienIds ->
		def criteria = Versuchsperson.createCriteria()
		def result = criteria.listDistinct {
			studien {
				'in' ('id', studienIds)
			}
		}
	}
	
	def showBildung = {
		if (params.selectedAltersklasse == Altersklasse.ERWACHSENER.toString()) {
			render(template:'erwachsener_search')        
		} else if (params.selectedAltersklasse == Altersklasse.KIND.toString()) {
			render(template:'kind_search')    
		} else {
			render(template:'blank')
		}
	}
}