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
package org.eclipse.che.api.auth.shared.dto;

import org.eclipse.che.dto.shared.DTO;

/** @author gazarenkov */
@DTO
public interface Credentials {

  String getRealm();

  void setRealm(String realm);

  Credentials withRealm(String realm);

  String getUsername();

  void setUsername(String name);

  Credentials withUsername(String name);

  String getPassword();

  void setPassword(String password);

  Credentials withPassword(String password);
}
