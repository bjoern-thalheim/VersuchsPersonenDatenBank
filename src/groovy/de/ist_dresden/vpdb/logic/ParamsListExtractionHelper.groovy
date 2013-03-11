package de.ist_dresden.vpdb.logic

import java.util.Collection;

abstract class ParamsListExtractionHelper {
	
	static def getListOfStringsFromParams = {
		givenList ->
			(givenList instanceof String) ? 
				Collections.singletonList(givenList) : 
				givenList as List
	}
}
