package de.ist_dresden.taglibs

class AssignmentBoxesTagLib {
  def assignmentBoxes = { attrs ->
    	out << """
    		<script type='text/javascript'>
    			var selectOption = function() {
                    var elem = document.getElementById('${attrs.prefix}_availableList');
                    var elem2 = document.getElementById('${attrs.selectedParam}');
                    for (var i = 0; i < elem.length; i++) { 
                        if (elem.options[i].selected) {
                            var selectedItemText = elem.options[i].text;
                            var selectedItemValue = elem.options[i].value;
                            
                            elem2.options[elem2.options.length] = new Option(selectedItemText, selectedItemValue); 
    	
                            elem.remove(i);
                            i--;
                        } 
                    }
                    sortValues('${attrs.selectedParam}');
                }
                
                var deselectOption = function() {
                    var elem = document.getElementById('${attrs.selectedParam}');
                    var elem2 = document.getElementById('${attrs.prefix}_availableList');
                    for (var i = 0; i < elem.length; i++) { 
                        if (elem.options[i].selected) { 
                            var selectedItemText = elem.options[i].text;
                            var selectedItemValue = elem.options[i].value;
                            
                            elem2.options[elem2.options.length] = new Option(selectedItemText, selectedItemValue);
                                                        
                            elem.remove(i);
                            i--;
                        } 
                    } 
                    sortValues('${attrs.prefix}_availableList');
                }

                function updateValueList(selectBoxId){
                  var arr = new Array;
                  jQuery('#' + selectBoxId + ' option').each(function(){
                    arr.push(jQuery(this).text());
                  });
                };

                function sortValues(selectBoxId){
    	          var unsortedVals = jQuery.makeArray(jQuery('#'+ selectBoxId + ' option'));
    		      var sortedVals = unsortedVals.sort(function(a,b){
    	            return jQuery(a).text().toLowerCase() > jQuery(b).text().toLowerCase() ? 1: -1;
                  });
                  jQuery('#' + selectBoxId).empty().html(sortedVals);
                  updateValueList(selectBoxId);
                };
    	
                jQuery('form').submit(function() {        
    			  jQuery('#${attrs.selectedParam}').each(function(){
                    jQuery('#${attrs.selectedParam} option').attr('selected','selected'); }
    			  );
                  return true;
                });

                jQuery(document).ready(function() {
                	sortValues('${attrs.prefix}_availableList');
                	sortValues('${attrs.selectedParam}');
                	});
             </script>

"""
    
    	out << "\t<div id='availableListBox'>\n\t\t<select id='${attrs.prefix}_availableList' name='${attrs.prefix}_availableList' multiple='yes' optionKey='id' size='5'>\n"
        attrs.availableList.each {
            out << "\t\t\t<option value='${it.id}'>${it}</option>\n"
        } 
        out << "\t\t</select>\n\t</div>\n"
        // Buttons
        out << "\t<div id='pushButtons'>\n"
		out << "\t\t<button type='button' name='shift-right' onclick='javascript:selectOption();'>&gt;&gt;</button><br/>\n"
		out << "\t\t<button type='button' name='shift-left' onclick='javascript:deselectOption();'>&lt;&lt;</button>\n"
//        out << "<a onclick='javascript:selectOption();' id='selectOption'><img src='../../images/skin/shift-right.png' /></a><br/>\n"
//        out << "<a onclick='javascript:deselectOption();' id='deselectOption'><img src='../../images/skin/shift-left.png' /></a>\n"
        out << "\t</div>\n"
		out << "\t<div id='selectedListBox'>\n\t\t<select id='${attrs.selectedParam}' name='${attrs.selectedParam}' multiple='yes' optionKey='id' size='5'>\n"
		attrs.selectedList.each {
			out << "\t\t\t<option value='${it.id}'>${it}</option>\n"
		}
        out << "\t\t</select>\n\t</div>\n"
    }
}