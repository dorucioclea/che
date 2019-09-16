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
package org.eclipse.che.workspace.infrastructure.docker;

import java.util.Map;
import javax.inject.Singleton;
import org.eclipse.che.api.core.ValidationException;
import org.eclipse.che.api.core.model.workspace.runtime.Machine;
import org.eclipse.che.api.workspace.server.spi.InfrastructureException;
import org.eclipse.che.api.workspace.server.spi.environment.InternalEnvironment;
import org.eclipse.che.api.workspace.server.spi.environment.InternalMachineConfig;

/** Checks whether runtime is consistent with its configuration. */
@Singleton
class RuntimeConsistencyChecker {
  void check(InternalEnvironment environment, DockerInternalRuntime runtime)
      throws ValidationException, InfrastructureException {
    Map<String, InternalMachineConfig> configs = environment.getMachines();
    Map<String, ? extends Machine> machines = runtime.getMachines();
    if (configs.size() != machines.size()) {
      throw new ValidationException(
          "Runtime has '%d' machines while configuration defines '%d'."
              + "Runtime machines: %s. Configuration machines: %s",
          machines.size(), configs.size(), machines.keySet(), configs.keySet());
    }
    if (!configs.keySet().containsAll(machines.keySet())) {
      throw new ValidationException(
          "Runtime has different set of machines than defined by configuration. "
              + "Runtime machines: %s. Configuration machines: %s",
          machines.keySet(), configs.keySet());
    }
  }
}
