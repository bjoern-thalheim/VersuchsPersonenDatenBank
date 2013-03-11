package de.ist_dresden.vpdb.entity;

class Versuchsperson {
	
	String vorname
	String nachname
    String telefonnummer
    String emailadresse
    Date geburtsdatum
    boolean inStudie
    boolean blacklisted
    Date letzterKontakt
    String letzterKontaktNotizen
    String besonderheiten
    
    Geschlecht geschlecht
	Altersklasse altersklasse
	
	Adresse adresse
    Kind kind
    Erwachsener erwachsener
	
	
    static mapping = {
		adresse fetch:'join'
		kind fetch:'join'
		erwachsener fetch:'join'
		studien fetch:'join'
		vpList fetch:'join'
    }
    
    static hasMany = [ studien : Studie, vpList : VersuchspersonenList ]
	
    static constraints = {
		nachname(blank:false,nullable:false)
		vorname(nullable:true)
        emailadresse(email:true, nullable:true)
        letzterKontakt(nullable:true)
        letzterKontaktNotizen(nullable:true)
        geburtsdatum(nullable:true)
        letzterKontakt(nullable:true)
        telefonnummer(nullable:true)
		geschlecht(nullable:true)
		altersklasse(nullable:true)
        letzterKontaktNotizen(nullable:true)
        besonderheiten(nullable:true)
		adresse(nullable:true)
        kind(nullable:true)
        erwachsener(nullable:true)
    }
	
	public String toString() {
		StringBuffer result = new StringBuffer()
		result.append(vorname + " " + nachname + "\n")
		result.append("Mail: " + emailadresse + ", Telefon: " + telefonnummer + "\n")
		result.append("Adresse: " + adresse + "\n")
		result.append("Geburtstag: " + geburtsdatum + "\n")
		result.append("Letzter Kontakt: " + letzterKontakt + ", Notizen: " + letzterKontaktNotizen + "\n")
		result.append("In Studie eingeschlossen: " + inStudie + "\n")
		result.append("Schwarze Liste: " + blacklisted + "\n")
		result.append("Besonderheiten: " + besonderheiten + "\n")
		result.append("Geschlecht: " + geschlecht + "\n")
		if (altersklasse == Altersklasse.ERWACHSENER)
			result.append(altersklasse.toString() + ", " + erwachsener)
		if (altersklasse == Altersklasse.KIND)
			result.append(altersklasse.toString() + ", " + kind + "\n")
		return result.toString()
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Versuchsperson))
			return false;
		Versuchsperson other = (Versuchsperson) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
