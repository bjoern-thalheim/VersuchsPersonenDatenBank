package de.ist_dresden.vpdb.entity

import java.util.Calendar;

class Studie {
  
  
  static hasMany = [ tests : Test , versuchspersonen : Versuchsperson ]
  def belongsTo = de.ist_dresden.vpdb.entity.Versuchsperson
  
  String name
  Date year
  
  static constraints = {
    name(unique:true,nullable:false,blank:false)
	year(nullable:true)
  }
  
  public String toString() {
	Calendar cal = Calendar.getInstance()
	cal.setTime(year)
    return name + " (" + cal.get(Calendar.YEAR) + ")"
  }
}
