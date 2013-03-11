package de.ist_dresden.vpdb.entity

class Test {
	
	String name
	  
	static hasMany = [ studien : Studie ]
	def belongsTo = de.ist_dresden.vpdb.entity.Studie

    static constraints = {
		name(unique:true,nullable:false,blank:false)
    }
	
	public String toString() {
		return name
	}
}
