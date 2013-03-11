package de.ist_dresden.vpdb.entity;

import java.util.HashSet;
import java.util.Set;

public enum Schulform {
	KEINE_ANGABE, KINDERGARTEN, GRUNDSCHULE, HAUPTSCHULE, REALSCHULE, GYMNASIUM, SONDERSCHULE, ANDERE_SCHULE;
	
	public static Set<Schulform> getSchulFormen (String... strings) {
		Set<Schulform> schulformen = new HashSet<Schulform>();
		for (String string : strings) {
			schulformen.add(Schulform.valueOf(string));
		}
		return schulformen;
	}
}
