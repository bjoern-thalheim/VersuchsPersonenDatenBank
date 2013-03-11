
      
      <!-- Kind -->
      <tr>
        <td valign="top" class="name">
            <label for="altersklasse"><g:message code="versuchsperson.view.label.kind.schulform" />:</label>
        </td>
        <td class="value ${hasErrors(bean:kindInstance,field:'schulform','errors')}">
          <select name="kind:schulform" id="kind:schulform">
            <g:each in="${de.ist_dresden.vpdb.entity.Schulform?.values()}">
              <g:if test="${it.equals(kindInstance?.schulform)}">
                <option value="${it}" selected><g:message code="versuchsperson.view.value.kind.schulform.${it}" /></option>
              </g:if>
              <g:if test="${!it.equals(kindInstance?.schulform)}">
                <option value="${it}"><g:message code="versuchsperson.view.value.kind.schulform.${it}" /></option>
              </g:if>
            </g:each>
          </select>
        </td>
      </tr>
      <tr>
        <td valign="top" class="name">
            <label for="altersklasse"><g:message code="versuchsperson.view.label.kind.genaueAngabe" />:</label>
        </td>
        <td class="value ${hasErrors(bean:kindInstance,field:'genaueAngabe','errors')}">
            <input type="text" id="kind:genaueAngabe" name="kind:genaueAngabe" value="${fieldValue(bean:kindInstance,field:'genaueAngabe')}" />
        </td>
      </tr>
