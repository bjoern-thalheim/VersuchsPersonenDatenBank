package de.ist_dresden.vpdb.entity

class TestController {
  
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
    def tests = Test.findAll(sort:sortAttribute, order:sortOrder)
    [testInstanceList:tests, testInstanceTotal: tests.size()]
  }
  
  def createorupdate = {
    
  }
  
  def edit = {
    def test = Test.findById(params.id)
    
    render(view:'createorupdate', model:[testInstance:test])
  }

  def cancel = {
    redirect(action:list)    
  }
  
  def delete = {
    if (params.id) {
      def test = Test.findById(params.id)
      if(test) {
        if (test.studien) {
          flash.error = "Test hat Referenzen auf die Studien " + test.studien
        } else {
          test.delete()
          flash.message = test.id + " " + test.name + " gelöscht."
        }
      }
    }
    redirect(action:'list')
  }
  
  def save = {
    Test test
    
    if (params.id) {
      test = Test.findById(params.id)
    } else {
      test = new Test() 
    }
    
    test.properties = params
    if(!test.validate()) {
      flash.error = "test.validation.error"
      render(view:'createorupdate', model:[testInstance:test])
      return
    } else {
      test.save()
    }
    
    redirect(view:'list')
  }
}
