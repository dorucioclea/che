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
package org.eclipse.che.api.core.model.factory;

/**
 * Defines factory button.
 *
 * @author Anton Korneta
 */
public interface Button {

  enum Type {
    LOGO {
      @Override
      public String toString() {
        return "logo";
      }
    },
    NOLOGO {
      @Override
      public String toString() {
        return "nologo";
      }
    };

    public static Type getIgnoreCase(String name) {
      for (Type type : values()) {
        if (name.equalsIgnoreCase(type.toString())) {
          return type;
        }
      }
      throw new IllegalArgumentException();
    }
  }

  /** Returns type of this button instance */
  Type getType();

  /** Returns attributes of this button instance */
  ButtonAttributes getAttributes();
}
