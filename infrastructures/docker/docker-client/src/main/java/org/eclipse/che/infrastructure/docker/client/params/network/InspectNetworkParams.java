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
package org.eclipse.che.infrastructure.docker.client.params.network;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.eclipse.che.infrastructure.docker.client.DockerConnector;

/**
 * Arguments holder for {@link DockerConnector#inspectNetwork(InspectNetworkParams)}.
 *
 * @author Alexander Garagatyi
 */
public class InspectNetworkParams {
  private String netId;

  private InspectNetworkParams() {}

  /**
   * Creates arguments holder with required parameters.
   *
   * @param netId network identifier
   * @return arguments holder with required parameters
   * @throws NullPointerException if {@code netId} is null
   */
  public static InspectNetworkParams create(@NotNull String netId) {
    return new InspectNetworkParams().withNetworkId(netId);
  }

  /**
   * Adds network identifier to this parameters.
   *
   * @param netId network identifier
   * @return this params instance
   * @throws NullPointerException if {@code netId} is null
   */
  public InspectNetworkParams withNetworkId(@NotNull String netId) {
    requireNonNull(netId);
    this.netId = netId;
    return this;
  }

  public String getNetworkId() {
    return netId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof InspectNetworkParams)) {
      return false;
    }
    final InspectNetworkParams that = (InspectNetworkParams) obj;
    return Objects.equals(netId, that.netId);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + Objects.hashCode(netId);
    return hash;
  }

  @Override
  public String toString() {
    return "InspectNetworkParams{" + "netId='" + netId + '\'' + '}';
  }
}
