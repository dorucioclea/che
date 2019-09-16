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

import static java.lang.String.format;

import org.eclipse.che.api.core.ValidationException;

/** @author Alexander Garagatyi */
public class ArgumentsValidator {
  public static void checkArgument(boolean expression, String error) throws ValidationException {
    if (!expression) {
      throw new ValidationException(error);
    }
  }

  public static void checkArgument(
      boolean expression, String errorMessageTemplate, Object... errorMessageParams)
      throws ValidationException {
    if (!expression) {
      throw new ValidationException(format(errorMessageTemplate, errorMessageParams));
    }
  }

  private ArgumentsValidator() {}
}
