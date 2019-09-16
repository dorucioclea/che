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
package org.eclipse.che.workspace.infrastructure.kubernetes.wsplugins.brokerphases;

import static org.eclipse.che.workspace.infrastructure.kubernetes.util.TracingSpanConstants.PREPARE_STORAGE_PHASE;

import com.google.common.annotations.Beta;
import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.List;
import org.eclipse.che.api.workspace.server.spi.InfrastructureException;
import org.eclipse.che.api.workspace.server.wsplugins.model.ChePlugin;
import org.eclipse.che.commons.tracing.TracingTags;
import org.eclipse.che.workspace.infrastructure.kubernetes.StartSynchronizer;
import org.eclipse.che.workspace.infrastructure.kubernetes.environment.KubernetesEnvironment;
import org.eclipse.che.workspace.infrastructure.kubernetes.namespace.pvc.WorkspaceVolumesStrategy;

/**
 * Prepares PVC in a workspace and calls next {@link BrokerPhase}.
 *
 * <p>This API is in <b>Beta</b> and is subject to changes or removal.
 *
 * @author Oleksandr Garagatyi
 */
@Beta
public class PrepareStorage extends BrokerPhase {

  private final String workspaceId;
  private final KubernetesEnvironment brokerEnvironment;
  private final WorkspaceVolumesStrategy volumesStrategy;
  private final StartSynchronizer startSynchronizer;
  private final Tracer tracer;

  public PrepareStorage(
      String workspaceId,
      KubernetesEnvironment brokerEnvironment,
      WorkspaceVolumesStrategy volumesStrategy,
      StartSynchronizer startSynchronizer,
      Tracer tracer) {
    this.workspaceId = workspaceId;
    this.brokerEnvironment = brokerEnvironment;
    this.volumesStrategy = volumesStrategy;
    this.startSynchronizer = startSynchronizer;
    this.tracer = tracer;
  }

  @Override
  public List<ChePlugin> execute() throws InfrastructureException {
    Span tracingSpan = tracer.buildSpan(PREPARE_STORAGE_PHASE).start();
    TracingTags.WORKSPACE_ID.set(tracingSpan, workspaceId);

    try {
      volumesStrategy.prepare(
          brokerEnvironment, workspaceId, startSynchronizer.getStartTimeoutMillis());
    } catch (InfrastructureException e) {
      TracingTags.setErrorStatus(tracingSpan, e);
      throw e;
    } finally {
      tracingSpan.finish();
    }

    return nextPhase.execute();
  }
}
