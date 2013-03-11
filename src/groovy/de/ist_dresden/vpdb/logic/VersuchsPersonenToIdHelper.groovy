package de.ist_dresden.vpdb.logic

import java.util.Collection;

import de.ist_dresden.vpdb.entity.Versuchsperson;

abstract class VersuchsPersonenToIdHelper {
	
	static def versuchspersonenToIds = {
		given ->
			if (given == null) {
				given = new ArrayList(0)
			}
			def values = ParamsListExtractionHelper.getListOfStringsFromParams(given)
			List result = new ArrayList(values.size())
			values.each {
				result.add(it.id)
			}
			return result
	}
	
	static def idsToVersuchspersonen = {
		given ->
			if (given == null) {
				given = new ArrayList(0)
			}
			def values = ParamsListExtractionHelper.getListOfStringsFromParams(given)
			List result = new ArrayList(values.size())
			values.each {
				result.add(Versuchsperson.findById(it))
			}
			return result
	}
}
