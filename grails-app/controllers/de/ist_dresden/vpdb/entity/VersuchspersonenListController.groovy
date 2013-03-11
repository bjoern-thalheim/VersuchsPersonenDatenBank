package de.ist_dresden.vpdb.entity

import de.ist_dresden.vpdb.logic.VersuchsPersonenToIdHelper;

class VersuchspersonenListController {
  
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
    def versuchspersonenLists = VersuchspersonenList.findAll(sort:sortAttribute, order:sortOrder)
    [versuchspersonenListInstanceList:versuchspersonenLists, versuchspersonenListInstanceTotal: versuchspersonenLists.size()]
  }
  
  def createorupdate = {
	def aktuelleVPs = VersuchsPersonenToIdHelper.idsToVersuchspersonen (session.currentVersuchspersonenIds)
    render(view:'createorupdate', model:[versuchspersonInstanceList:aktuelleVPs])
  }
  
  def edit = {
    def versuchspersonenList = VersuchspersonenList.findById(params.id)
	def vpsToDisplay = getPersonsWithThisList(versuchspersonenList.id)
    render(view:'createorupdate', model:[versuchspersonenListInstance:versuchspersonenList, versuchspersonInstanceList:vpsToDisplay])
  }
	
  def getPersonsWithThisList = {
	listId ->
	def criteria = Versuchsperson.createCriteria()
	def result = criteria.listDistinct {
		vpList {
			'eq' ('id', listId)
		}
	}
  }

  def cancel = {
    redirect(action:list)    
  }
  
  def delete = {
    if (params.id) {
      def versuchspersonenList = VersuchspersonenList.findById(params.id)
      if(versuchspersonenList) {
		  removeAllVersuchspersonen(versuchspersonenList)
          versuchspersonenList.delete()
          flash.message = "\"" + versuchspersonenList.name + "\" geloescht."
      }
    }
    redirect(action:'list')
  }

	/**
	 * Methode, welche von einer Auswahl alle Relationen entfernt.
	 * 
	 * @param existing Die Versuchspersonenauswahl.
	 */
	private removeAllVersuchspersonen(VersuchspersonenList versuchspersonenList) {
		// Das Umkopieren muss sein, damit es keine ConcurrentModificationException gibt.
		def attachedVersuchspersonen = new ArrayList(versuchspersonenList.versuchspersonen)
		attachedVersuchspersonen.each { it.removeFromVpList(versuchspersonenList) }
	}
  
	def save = {
		VersuchspersonenList versuchspersonenList
		if (params.id) {
			versuchspersonenList = VersuchspersonenList.findById(params.id)
			versuchspersonenList.name = params.name
		} else {
			if (params.existingName && VersuchspersonenList.findByName(params.existingName)) {
				versuchspersonenList = VersuchspersonenList.findByName(params.existingName)
				removeAllVersuchspersonen(versuchspersonenList)
			} else {
				versuchspersonenList = new VersuchspersonenList()
				versuchspersonenList.properties = params
			}
			session.currentVersuchspersonenIds?.each {
				Versuchsperson vp = Versuchsperson.findById(it)
				vp.addToVpList(versuchspersonenList)
			}
		}
	  
	  if(!versuchspersonenList.validate()) {
	    flash.error = "versuchspersonenList.validation.error"
	    render(view:'createorupdate', model:[versuchspersonenListInstance:versuchspersonenList])
	    return
	  } else {
	    versuchspersonenList.save()
	  }
	    
	  redirect(view:'list')
	}
  
	def restore = {
	    def versuchspersonenList = VersuchspersonenList.findById(params.id).versuchspersonen
		session.currentVersuchspersonenIds = VersuchsPersonenToIdHelper.versuchspersonenToIds(versuchspersonenList)
		redirect(controller:'versuchsperson', view:'list')
	}
}
