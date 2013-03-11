package de.ist_dresden.vpdb.data;

import static de.ist_dresden.vpdb.entity.Altersklasse.ERWACHSENER;
import static de.ist_dresden.vpdb.entity.Altersklasse.KIND;
import static de.ist_dresden.vpdb.entity.Geschlecht.MAENNLICH;
import static de.ist_dresden.vpdb.entity.Geschlecht.WEIBLICH;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.ist_dresden.vpdb.entity.Bildungsabschluss;
import de.ist_dresden.vpdb.entity.Geschlecht;
import de.ist_dresden.vpdb.entity.Schulform;

public class VPXMLDataGenerator {

	private static final int NUMBER_OF_VPS = 20;
	private static final int BILDUNGSJAHRE_MAX = 20;
	public static final String[] nachnamen = { "Schaefer", "Farber", "Hirsch",
			"Gottschalk", "Baum", "Blau", "Koester", "Schmid", "Urner",
			"Bieber", "Finkel", "Kuhn", "Reinhard", "Luft", "Schwartz",
			"Krause", "Frei", "Foerster", "Ebersbach", "Klein" }; // 20 insgesamt
	public static final String[] vornamen_weiblich = { "Lea", "Vanessa",
			"Frankziska", "Lisa", "Katja", "Christin", "Sabine", "Silke",
			"Kerstin", "Jana", "Anke", "Monika", "Ute", "Anett", "Lisa",
			"Katrin", "Tanja", "Brigitte", "Christina", "Diana" }; // 20 insgesamt
	public static final String[] vornamen_maennlich = { "Uwe", "Christian",
			"Sven", "Thomas", "Tobias", "Stephan", "Peter", "Florian",
			"Patrick", "Philipp", "Mario", "Luca", "Kevin", "Thorsten", "Sven",
			"Maximilian", "Ralf", "Dieter", "Rene", "Stefan" }; // 20 insgesamt
	public static final Map<Geschlecht, String[]> vornamen;
	static {
		vornamen = new HashMap<Geschlecht, String[]>();
		vornamen.put(MAENNLICH, vornamen_maennlich);
		vornamen.put(WEIBLICH, vornamen_weiblich);
	}
	public static final String[] emails = { "otturemma_383@yopmail.com",
			"ehadduri_818@yopmail.com", "ahixerre_294@yopmail.com",
			"esezoppo_917@yopmail.com", "agedufa_679@yopmail.com",
			"gandhi@gmx.de", "missile@email.de", "rocketman@googlemail.com",
			"mightyhobo@gmail.com", "lonelyguy@hotmail.com",
			"kanadianking@web.de", "enforcer@mms-dresden.de",
			"Rene.Obermann@telekom.de", "ChingSang@gmail.com",
			"Hello.World@gmx.de", "juliejankis@email.com",
			"cornerstonecathy@gmx.at", "Gyarados@web.de",
			"Exeggutor@hotmail.com", "Jynx@email.de", "Weezing@telekom.de",
			"Metapod@gmail.de", "Amaranth@gmx.de", "Morwenna@email.de" };
	public static final String[] telefonnummern = { "0353410503",
			"09901219343", "04129701502", "04180302874", "04541473925",
			"0833426889", "016442212980", "09229470897", "06341300762",
			"07403745761", "04651683849", "02554146606", "06587352648" };
	public static final String[] geburtsdaten = { "1951-04-26", "1967-02-06",
			"1967-10-21", "1940-05-10", "1963-03-15", "1993-08-04",
			"1998-03-22", "2001-02-15", "2002-08-26", "1995-04-10",
			"1974-04-19", "1944-04-20", "1984-09-10", "1950-12-28",
			"1994-04-13", "2002-04-20", "1997-02-01", "1994-02-13",
			"1970-05-10", "1964-04-18", "1981-07-24", "1964-08-22",
			"1994-11-02", "2001-07-21", "1998-01-01", "2004-12-03",
			"1957-01-01", "1960-07-16", "1954-08-19", "1972-04-07" }; // 30
																		// Insgesamt
	public static final String[] trueOrFalse = { "true", "false" };
	public static final String[] letzteKontakte = { "2009-09-16", "2008-11-11",
			"2007-07-21", "2008-10-17", "2009-07-26", "2010-01-14" };
	public static final String[] strassen = { "Alt Reinickendorf 58",
			"Knesebeckstrasse 96", "Gotzkowskystrasse 76", "Grolmanstrasse 56",
			"Friedrichstrasse 51" };
	public static final String[] zips = { "40221", "26831", "39361", "52388",
			"22587" };
	public static final String[] staedte = { "Hamburg", "Reichartshausen",
			"Meissen", "Jucken", "Altscheid" };
	public static final String[] genaueAngaben = { "Montesori-Schule",
			"Waldorf-Schule", "Abendschule", "Volkshochschule", "Internat",
			"katholische Schule", "evangelische Schule" };
	public static final String[] bildungsJahre;
	static {
		bildungsJahre = new String[BILDUNGSJAHRE_MAX];
		for (int i = 0; i < BILDUNGSJAHRE_MAX; i++) {
			bildungsJahre[i] = String.valueOf(i);
		}
	}
	public static String[] tests = { "Rohrschach", "Tower of London", "HaWIK",
			"Elektroschock" };
	public static String[] studienNamen = { "Katharinas Studie",
			"Mattias' Studie", "Lucias Studie",
			"Bjoerns ethisch fragwuerdige Studie", "Studie5", "Studie6", "Studie7", 
			"Studie8", "Studie9", "Studie10", "Studie11", "Studie12" };
	public static String[] studienDaten = { "2009-01-01", "2010-01-01", "2007-01-01" };

	private static XMLDataGenerator versuchspersonenFile;
	private static XMLDataGenerator studienFile;
	private static Random random = new Random();

	public static void main(String[] args) throws IOException {
		writeStudien();
		writeVersuchsPersonen();
	}

	private static void writeStudien() throws IOException {
		studienFile = new XMLDataGenerator(new FileWriter("test/generated-studien.xml"));
		studienFile.openTag("studien", 0, true);
		for (int i = 0; i < studienNamen.length; i++) {
			studienFile.openTag("studie", 1, true);
			studienFile.writeTagAndContent("name", 2, studienNamen[i]);
			studienFile.writeZufaellig("year", 2, studienDaten);
			studienFile.openTag("tests", 2, true);
			int anzahlTests = random.nextInt(4) + 1;
			Set<String> testsFuerStudie = new HashSet<String>(anzahlTests);
			for (int j = 0; j < anzahlTests; j++) {
				testsFuerStudie.add(studienFile.getZufaellig(tests));
			}
			for (String testFuerStudie : testsFuerStudie) {
				studienFile.writeTagAndContent("test", 3, testFuerStudie);
			}
			studienFile.closeTag("tests", 2, true);
			studienFile.closeTag("studie", 1, true);
		}
		studienFile.closeTag("studien", 0, true);
		studienFile.close();
	}

	private static void writeVersuchsPersonen() throws IOException {
		versuchspersonenFile = new XMLDataGenerator(new FileWriter("test/generated-users.xml"));
		versuchspersonenFile.openTag("versuchspersonen", 0, true);
		for (int i = 0; i < NUMBER_OF_VPS; i++) {
			versuchspersonenFile.openTag("versuchsperson", 1, true);
			writeVersuchspersonAttributes();
			versuchspersonenFile.closeTag("versuchsperson", 1, true);
		}
		versuchspersonenFile.closeTag("versuchspersonen", 0, true);
		versuchspersonenFile.close();
	}

	private static void writeAdresse() throws IOException {
		String tagNameAdresse = "adresse";
		versuchspersonenFile.openTag(tagNameAdresse, 2, true);
		versuchspersonenFile.writeZufaellig("strasse", 3, strassen);
		versuchspersonenFile.writeZufaellig("plz", 3, zips);
		versuchspersonenFile.writeZufaellig("ort", 3, staedte);
		versuchspersonenFile.closeTag(tagNameAdresse, 2, true);
	}

	private static void writeBildung(boolean istErwachsener) throws IOException {
		String tagNameBildung = "bildung";
		versuchspersonenFile.openTag(tagNameBildung, 2, true);
		if (istErwachsener) {
			Bildungsabschluss abschluss = versuchspersonenFile
					.getZufaellig(Bildungsabschluss.values());
			versuchspersonenFile.writeTagAndContent("schulabschluss", 3,
					abschluss.toString());
			versuchspersonenFile.writeZufaellig("ausbildungsjahre", 3,
					bildungsJahre);
		} else {
			Schulform schulform = versuchspersonenFile.getZufaellig(Schulform
					.values());
			versuchspersonenFile.writeTagAndContent("schulform", 3, schulform
					.toString());
		}
		versuchspersonenFile.writeZufaellig("genaueAngabe", 3, genaueAngaben);
		versuchspersonenFile.closeTag(tagNameBildung, 2, true);

	}

	private static void writeAltersklasse(boolean bornBefore92)
			throws IOException {
		versuchspersonenFile.writeTagAndContent("altersklasse", 2,
				(bornBefore92 ? ERWACHSENER : KIND).toString());
	}

	private static void writeEMail() throws IOException {
		versuchspersonenFile.writeZufaellig("emailadresse", 2, emails);
	}

	private static void writeGeburtsdatum(String geburtsdatum)
			throws IOException {
		versuchspersonenFile
				.writeTagAndContent("geburtsdatum", 2, geburtsdatum);
	}

	private static void writeGeschlecht(Geschlecht geschlecht)
			throws IOException {
		versuchspersonenFile.writeTagAndContent("geschlecht", 2, geschlecht
				.toString());
	}

	private static void writeInStudie() throws IOException {
		versuchspersonenFile.writeZufaellig("inStudie", 2, trueOrFalse);
	}

	private static void writeBlacklisted() throws IOException {
		versuchspersonenFile.writeZufaellig("blacklisted", 2, trueOrFalse);
	}

	private static void writeLetzterKontakt() throws IOException {
		versuchspersonenFile
				.writeZufaellig("letzterKontakt", 2, letzteKontakte);
	}

	private static void writeNachname() throws IOException {
		versuchspersonenFile.writeZufaellig("nachname", 2, nachnamen);
	}

	private static void writeTelefon() throws IOException {
		versuchspersonenFile.writeZufaellig("telefonnummer", 2, telefonnummern);
	}

	private static void writeVersuchspersonAttributes() throws IOException {
		writeNachname();
		Geschlecht geschlecht = versuchspersonenFile.getZufaellig(Geschlecht
				.values());
		writeVorname(geschlecht);
		writeGeschlecht(geschlecht);
		writeEMail();
		writeTelefon();
		String geburtsDatum = versuchspersonenFile.getZufaellig(geburtsdaten);
		writeGeburtsdatum(geburtsDatum);
		writeInStudie();
		writeBlacklisted();
		writeLetzterKontakt();
		boolean istErwachsener = bornBefore92(geburtsDatum);
		writeAltersklasse(istErwachsener);
		writeAdresse();
		writeBildung(istErwachsener);
		writeStudienForVersuchsperson();
	}

	private static void writeStudienForVersuchsperson() throws IOException {
		versuchspersonenFile.openTag("studien", 2, true);
		Set<String> studienFuerVersuchsPerson = new HashSet<String>();
		for (int i = 0; i < random.nextInt(3) + 1; i++) {
			studienFuerVersuchsPerson.add(versuchspersonenFile
					.getZufaellig(studienNamen));
		}
		for (String studienName : studienFuerVersuchsPerson) {
			versuchspersonenFile.writeTagAndContent("studie", 3, studienName);
		}
		versuchspersonenFile.closeTag("studien", 2, true);

	}

	private static void writeVorname(Geschlecht geschlecht) throws IOException {
		versuchspersonenFile.writeZufaellig("vorname", 2, vornamen
				.get(geschlecht));
	}

	private static boolean bornBefore92(String geburtsDatum) {
		return (Integer.valueOf(extractYearFromDate(geburtsDatum)) < 1992);
	}

	private static String extractYearFromDate(String geburtsDatum) {
		return geburtsDatum.split("-")[0];
	}

}
