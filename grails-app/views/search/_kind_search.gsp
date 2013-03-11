
				<!-- Auswahlbox für Schulform für Kinder -->
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="versuchsperson.view.label.kind.schulform" default="moegliche Schulformen" /></label>
                    </td>
                    <td valign="top" class="value">
                        <g:select name="schulformen" from="${de.ist_dresden.vpdb.entity.Schulform.values()}" multiple="yes" size="5" />
                    </td>
                </tr>