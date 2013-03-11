package de.ist_dresden.vpdb.entity

class StudieController {
  
  def index = {
    redirect(action:'list')
  }
  
  def list = {
        def sortAttribute = 'name'
        if (params.sort) {
            sortAttribute = params.sort
        }
        def sortOrder = 'asc'
        if (params.order) {
            sortOrder = params.order
        }
    def studien = Studie.findAll(sort:sortAttribute, order:sortOrder)
    [studieInstanceList:studien, studieInstanceTotal: studien.size()]
  }
  
  def createorupdate = {
	chain(action:'edit')    
  }
  
  def edit = {
	def studie
	def availableTests = Test.findAll()
	if (params.id) {
      studie = Studie.findById(params.id)
      availableTests.removeAll(studie.tests)
	}
    
    render(view:'createorupdate', model:[studieInstance:studie, 'availableTests':availableTests])
  }

  def cancel = {
    redirect(action:list)    
  }
  
  def delete = {
    if (params.id) {
      def studie = Studie.findById(params.id)
      if(studie) {
        if (studie.versuchspersonen) {
          flash.error = "Studie ist noch " + studie.versuchspersonen.size() + " Versuchspersonen zugeordnet"
        } else {
          studie.delete()
          flash.message = studie.id + " " + studie.name + " gelöscht."
        }
      }
    }
    redirect(action:'list')
  }
  
  def save = {
    Studie studie
    
    if (params.id) {
      studie = Studie.findById(params.id)
    } else {
      studie = new Studie() 
    }
    
    studie.tests = []
    studie.properties = params
    if(!studie.validate()) {
      flash.error = "studie.validation.error"
      render(view:'createorupdate', model:[studieInstance:studie])
      return
    } else {
      studie.save()
    }
    
    redirect(view:'list')
  }
}
