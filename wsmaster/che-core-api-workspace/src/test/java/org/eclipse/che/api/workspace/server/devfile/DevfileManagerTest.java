/*
 * Copyright (c) 2012-2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.workspace.server.devfile;

import static org.eclipse.che.api.workspace.server.devfile.Constants.KUBERNETES_COMPONENT_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.eclipse.che.account.spi.AccountImpl;
import org.eclipse.che.api.core.model.workspace.WorkspaceStatus;
import org.eclipse.che.api.workspace.server.devfile.exception.DevfileException;
import org.eclipse.che.api.workspace.server.devfile.exception.DevfileFormatException;
import org.eclipse.che.api.workspace.server.devfile.validator.DevfileIntegrityValidator;
import org.eclipse.che.api.workspace.server.devfile.validator.DevfileSchemaValidator;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceConfigImpl;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceImpl;
import org.eclipse.che.api.workspace.server.model.impl.devfile.ActionImpl;
import org.eclipse.che.api.workspace.server.model.impl.devfile.CommandImpl;
import org.eclipse.che.api.workspace.server.model.impl.devfile.ComponentImpl;
import org.eclipse.che.api.workspace.server.model.impl.devfile.DevfileImpl;
import org.eclipse.che.api.workspace.server.model.impl.devfile.EndpointImpl;
import org.eclipse.che.commons.json.JsonHelper;
import org.eclipse.che.commons.json.JsonParseException;
import org.eclipse.che.commons.subject.Subject;
import org.eclipse.che.commons.subject.SubjectImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

@Listeners(MockitoTestNGListener.class)
public class DevfileManagerTest {

  private static final Subject TEST_SUBJECT = new SubjectImpl("name", "id", "token", false);
  private static final String DEVFILE_YAML_CONTENT = "devfile yaml stub";

  @Mock private DevfileSchemaValidator schemaValidator;
  @Mock private DevfileIntegrityValidator integrityValidator;
  @Mock private ObjectMapper objectMapper;
  @Mock private FileContentProvider contentProvider;

  @Mock private JsonNode devfileJsonNode;
  private DevfileImpl devfile;

  @InjectMocks private DevfileManager devfileManager;

  @BeforeMethod
  public void setUp() throws Exception {
    devfile = new DevfileImpl();

    lenient().when(schemaValidator.validateYaml(any())).thenReturn(devfileJsonNode);
    lenient().when(objectMapper.treeToValue(any(), eq(DevfileImpl.class))).thenReturn(devfile);
  }

  @Test
  public void testValidateAndParse() throws Exception {
    // when
    DevfileImpl parsed = devfileManager.parseYaml(DEVFILE_YAML_CONTENT);

    // then
    assertEquals(parsed, devfile);
    verify(schemaValidator).validateYaml(DEVFILE_YAML_CONTENT);
    verify(objectMapper).treeToValue(devfileJsonNode, DevfileImpl.class);
    verify(integrityValidator).validateDevfile(devfile);
  }

  @Test
  public void testInitializingDevfileMapsAfterParsing() throws Exception {
    // given
    CommandImpl command = new CommandImpl();
    command.getActions().add(new ActionImpl());
    devfile.getCommands().add(command);

    ComponentImpl component = new ComponentImpl();
    component.getEndpoints().add(new EndpointImpl());
    devfile.getComponents().add(component);

    // when
    DevfileImpl parsed = devfileManager.parseYaml(DEVFILE_YAML_CONTENT);

    // then
    assertNotNull(parsed.getCommands().get(0).getAttributes());
    assertNotNull(parsed.getComponents().get(0).getSelector());
    assertNotNull(parsed.getComponents().get(0).getEndpoints().get(0).getAttributes());
  }

  @Test
  public void shouldResolveReferencesIntoReferenceContentForFactories() throws Exception {

    String referenceContent = "my_content_yaml_v3";
    when(contentProvider.fetchContent(anyString())).thenReturn(referenceContent);

    ComponentImpl component = new ComponentImpl();
    component.setType(KUBERNETES_COMPONENT_TYPE);
    component.setReference("myfile.yaml");
    devfile.getComponents().add(component);

    // when
    devfileManager.resolveReference(devfile, contentProvider);

    // then
    verify(contentProvider).fetchContent(eq("myfile.yaml"));
    assertEquals(referenceContent, devfile.getComponents().get(0).getReferenceContent());
  }

  @Test(
      expectedExceptions = DevfileException.class,
      expectedExceptionsMessageRegExp = "Unable to resolve reference of component: test")
  public void shouldThrowDevfileExceptionWhenReferenceIsNotResolvable() throws Exception {

    when(contentProvider.fetchContent(anyString())).thenThrow(IOException.class);

    ComponentImpl component = new ComponentImpl();
    component.setType(KUBERNETES_COMPONENT_TYPE);
    component.setAlias("test");
    component.setReference("myfile.yaml");
    devfile.getComponents().add(component);

    // when
    devfileManager.resolveReference(devfile, contentProvider);

    // then exception is thrown
  }

  @Test(
      expectedExceptions = DevfileFormatException.class,
      expectedExceptionsMessageRegExp = "non valid")
  public void shouldThrowExceptionWhenExceptionOccurredDuringSchemaValidation() throws Exception {
    // given
    doThrow(new DevfileFormatException("non valid")).when(schemaValidator).validateYaml(any());

    // when
    devfileManager.parseYaml(DEVFILE_YAML_CONTENT);
  }

  @Test(
      expectedExceptions = DevfileFormatException.class,
      expectedExceptionsMessageRegExp = "non valid")
  public void shouldThrowExceptionWhenErrorOccurredDuringDevfileParsing() throws Exception {
    // given
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    when(jsonException.getMessage()).thenReturn("non valid");
    doThrow(jsonException).when(objectMapper).treeToValue(any(), any());

    // when
    devfileManager.parseYaml(DEVFILE_YAML_CONTENT);
  }

  private WorkspaceImpl createWorkspace(WorkspaceStatus status)
      throws IOException, JsonParseException {
    return WorkspaceImpl.builder()
        .generateId()
        .setConfig(createConfig())
        .setAccount(new AccountImpl("anyId", TEST_SUBJECT.getUserName(), "test"))
        .setStatus(status)
        .build();
  }

  private WorkspaceConfigImpl createConfig() throws IOException, JsonParseException {
    String jsonContent =
        Files.readFile(getClass().getClassLoader().getResourceAsStream("workspace_config.json"));
    return JsonHelper.fromJson(jsonContent, WorkspaceConfigImpl.class, null);
  }
}
