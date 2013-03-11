package de.ist_dresden.vpdb.entity;

class Adresse {
	
	String strasseUndHausnummer
	String plz
	String ort
	
	def String toString() {
		return "Strasse: " + strasseUndHausnummer + ", Plz: " + plz + ", Ort: " + ort  
	}
	
	static constraints = {
		strasseUndHausnummer(nullable:true)
		plz(nullable:true)
		ort(nullable:true)
	}
}
