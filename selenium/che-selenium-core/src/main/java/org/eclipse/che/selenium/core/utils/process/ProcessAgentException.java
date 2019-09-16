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
package org.eclipse.che.selenium.core.utils.process;

import java.io.IOException;

/** @author Dmytro Nochevnov */
public class ProcessAgentException extends IOException {
  public ProcessAgentException(String message, Throwable cause) {
    super(message, cause);
  }
}
