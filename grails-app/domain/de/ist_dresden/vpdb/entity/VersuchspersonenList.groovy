package de.ist_dresden.vpdb.entity

class VersuchspersonenList {
	
	static hasMany = [ versuchspersonen : Versuchsperson ]
	def belongsTo = de.ist_dresden.vpdb.entity.Versuchsperson
	
	String name
	
    static constraints = {
		name(unique:true,nullable:false,blank:false)
    }
}
