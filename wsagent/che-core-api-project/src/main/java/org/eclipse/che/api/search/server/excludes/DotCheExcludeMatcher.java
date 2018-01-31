/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.search.server.excludes;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import javax.inject.Singleton;

@Singleton
public class DotCheExcludeMatcher implements PathMatcher {

  @Override
  public boolean matches(Path fsPath) {
    for (Path pathElement : fsPath) {
      if (pathElement == null || ".che".equals(pathElement.toString())) {
        return true;
      }
    }
    return false;
  }
}