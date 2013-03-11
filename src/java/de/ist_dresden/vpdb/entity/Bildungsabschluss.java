package de.ist_dresden.vpdb.entity;

import java.util.HashSet;
import java.util.Set;

public enum Bildungsabschluss {
	KEINE_ANGABE, KEINER, HAUPTSCHULE, REALSCHULE, ABITUR, HOCHSCHULE, ANDERER;
	
	public static Set<Bildungsabschluss> getBildungsAbschluesse (String... strings) {
		Set<Bildungsabschluss> abschluesse = new HashSet<Bildungsabschluss>();
		for (String bildungsabschluss : strings) {
			abschluesse.add(Bildungsabschluss.valueOf(bildungsabschluss));
		}
		return abschluesse;
	}
}
