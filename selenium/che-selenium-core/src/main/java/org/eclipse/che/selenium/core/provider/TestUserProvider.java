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
package org.eclipse.che.selenium.core.provider;

import org.eclipse.che.selenium.core.user.TestUser;

/**
 * New test user provider.
 *
 * @author Dmytro Nochevnov
 */
public interface TestUserProvider extends RemovableUserProvider<TestUser> {}
