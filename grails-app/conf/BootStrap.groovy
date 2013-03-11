import de.ist_dresden.vpdb.data.DataImporter;

class BootStrap {
  
  def init = { servletContext ->
    
    environments {
      development {
//		DataImporter.importData("test/versuchspersonen.xml", "test/studien.xml");
		DataImporter.importData("test/generated-users.xml", "test/generated-studien.xml");
      }
      test {
        DataImporter.importData("test/versuchspersonen.xml", "test/studien.xml");
      }
    }
    
  }
  
  def destroy = {
  }
} 