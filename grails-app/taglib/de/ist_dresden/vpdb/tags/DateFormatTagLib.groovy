package de.ist_dresden.vpdb.tags

class DateFormatTagLib {
    def dateFormat = { attrs ->
        if (attrs.value)
          out << new java.text.SimpleDateFormat(attrs.format).format(attrs.value)
    } 
}
