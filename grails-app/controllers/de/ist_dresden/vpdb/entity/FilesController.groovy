package de.ist_dresden.vpdb.entity;

import de.ist_dresden.vpdb.data.DataImporter;
import de.ist_dresden.vpdb.data.XmlExporter 

class FilesController {
    
    def index = {
    	redirect(action:'files')
    }
    
    def files = {
        
    }
    
    def download = {
        String data = XmlExporter.databaseAsXml
        response.setHeader("Content-disposition", "attachment; filename=vpdb.xml");
        render(text:data, contentType:"text/xml", encoding:"UTF-8")
    }
	
	def upload = {
    	def uploadedFile = request.getFile("versuchspersonendaten")
    	def uploadedFileText = uploadedFile.inputStream.getText("UTF-8")
    	DataImporter.importStudien(uploadedFileText)    
    	DataImporter.importVersuchspersonen(uploadedFileText)
        flash.message = "Upload der Datei erfolgreich."
        redirect(action:'files')
	}
}
