
      
      <!-- Erwachsener -->
      <tr>
        <td valign="top" class="name">
            <label for="altersklasse"><g:message code="versuchsperson.view.label.erwachsener.schulabschluss" />:</label>
        </td>
        <td class="value ${hasErrors(bean:erwachsenerInstance,field:'schulabschluss','errors')}">
          <select name="erwachsener:schulabschluss" id="erwachsener:schulabschluss">
            <g:each in="${de.ist_dresden.vpdb.entity.Bildungsabschluss?.values()}">
              <g:if test="${it.equals(erwachsenerInstance?.abschluss)}">
                <option value="${it}" selected><g:message code="versuchsperson.view.value.erwachsener.abschluss.${it}" /></option>
              </g:if>
              <g:if test="${!it.equals(erwachsenerInstance?.abschluss)}">
                <option value="${it}"><g:message code="versuchsperson.view.value.erwachsener.abschluss.${it}" /></option>
              </g:if>
            </g:each>
          </select>
        </td>
      </tr>
      <tr>
        <td valign="top" class="name">
            <label for="altersklasse"><g:message code="versuchsperson.view.label.erwachsener.genaueAngabe" />:</label>
        </td>
        <td class="value ${hasErrors(bean:erwachsenerInstance,field:'genaueAngabe','errors')}">
            <input type="text" id="erwachsener:genaueAngabe" name="erwachsener:genaueAngabe" value="${fieldValue(bean:erwachsenerInstance,field:'genaueAngabe')}" />
        </td>
      </tr>
      <tr>
        <td valign="top" class="name">
            <label for="altersklasse"><g:message code="versuchsperson.view.label.erwachsener.bildungsjahre" />:</label>
        </td>
        <td class="value ${hasErrors(bean:erwachsenerInstance,field:'ausbildungsjahre','errors')}">
            <input type="text" id="erwachsener:ausbildungsjahre" name="erwachsener:ausbildungsjahre" value="${fieldValue(bean:erwachsenerInstance,field:'ausbildungsjahre')}" />
        </td>
      </tr>