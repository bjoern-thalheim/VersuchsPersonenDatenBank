
				<!-- Auswahlbox für Schulabschluss für Erwachsene -->
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.schulabschluesse.label" default="moegliche Schulabschluesse" /></label>
                    </td>
                    <td valign="top" class="value">
                        <g:select name="schulabschluesse" from="${de.ist_dresden.vpdb.entity.Bildungsabschluss.values()}" multiple="yes" size="5" />
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.bildung.jahre.upper" default="Ausbildungsjahre-Obergrenze" /></label>
                    </td>
                    <td valign="top" class="value">
			          <select name="bildungsjahre_upper" id="bildungsjahre_upper">
			            <option value=""></option>
			            <g:each in="${possibleAusbildungsJahre.iterator().reverse()}">
			            	<option value="${it}">${it}</option>
			            </g:each>
			          </select>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.bildung.jahre.lower" default="Ausbildungsjahre-Untergrenze" /></label>
                    </td>
                    <td valign="top" class="value">
			          <select name="bildungsjahre_lower" id="bildungsjahre_lower">
			            <option value=""></option>
			            <g:each in="${possibleAusbildungsJahre.iterator()}">
			            	<option value="${it}">${it}</option>
			            </g:each>
			          </select>
                    </td>
                </tr>