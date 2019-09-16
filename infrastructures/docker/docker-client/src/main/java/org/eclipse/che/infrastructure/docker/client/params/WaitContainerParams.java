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
package org.eclipse.che.infrastructure.docker.client.params;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.eclipse.che.infrastructure.docker.client.DockerConnector;

/**
 * Arguments holder for {@link DockerConnector#waitContainer(WaitContainerParams)}.
 *
 * @author Mykola Morhun
 */
public class WaitContainerParams {

  private String container;

  /**
   * Creates arguments holder with required parameters.
   *
   * @param container container identifier, either id or name
   * @return arguments holder with required parameters
   * @throws NullPointerException if {@code container} is null
   */
  public static WaitContainerParams create(@NotNull String container) {
    return new WaitContainerParams().withContainer(container);
  }

  private WaitContainerParams() {}

  /**
   * Adds container to this parameters.
   *
   * @param container container identifier, either id or name
   * @return this params instance
   * @throws NullPointerException if {@code container} is null
   */
  public WaitContainerParams withContainer(@NotNull String container) {
    requireNonNull(container);
    this.container = container;
    return this;
  }

  public String getContainer() {
    return container;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WaitContainerParams that = (WaitContainerParams) o;
    return Objects.equals(container, that.container);
  }

  @Override
  public int hashCode() {
    return Objects.hash(container);
  }
}
