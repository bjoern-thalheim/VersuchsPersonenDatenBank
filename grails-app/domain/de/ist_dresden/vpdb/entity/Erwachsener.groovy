package de.ist_dresden.vpdb.entity;

class Erwachsener {
	def belongsTo = de.ist_dresden.vpdb.entity.Versuchsperson
	
	Integer ausbildungsjahre
	String genaueAngabe
	Bildungsabschluss abschluss
	
	static constraints = {
		ausbildungsjahre(nullable:true)
		genaueAngabe(nullable:true)
		abschluss(nullable:true)
	}
	
	def String toString() {
		return "Ausbildungsjahre: " + ausbildungsjahre + ", genaue Angabe: " + genaueAngabe + ", Abschluss: " + abschluss  
	}
}
