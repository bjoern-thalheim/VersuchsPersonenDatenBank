package de.ist_dresden.vpdb.logic

import java.util.Collection;

abstract class IdConversionHelper {
	
	static def getListOfLongsFromListOfStrings = {
		givenList ->
			def listOfStrings = ParamsListExtractionHelper.getListOfStringsFromParams(givenList)
			if (listOfStrings) {
				List<Long> arrayOfLongs = new ArrayList<Long>(listOfStrings.size())
				listOfStrings.each() {
					arrayOfLongs.add(Long.valueOf(it))
				}
				return arrayOfLongs
			} else {
				return Collections.emptyList()
			}
	}
}
