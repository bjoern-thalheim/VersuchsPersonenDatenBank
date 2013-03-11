package de.ist_dresden.vpdb.entity;

class Kind {
	def belongsTo = de.ist_dresden.vpdb.entity.Versuchsperson
	
	Schulform schulform
	String genaueAngabe
	
	static constraints = {
		schulform(nullable:true)
		genaueAngabe(nullable:true)
	}
	
	def String toString() {
		return "Schulform: " + schulform + ", genaue Angabe: " + genaueAngabe
	}
}
