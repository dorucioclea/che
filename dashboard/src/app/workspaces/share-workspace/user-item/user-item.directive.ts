/*
 * Copyright (c) 2015-2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
'use strict';

/**
 * Defines a directive for user item in permissions list.
 *
 * @author Ann Shumilova
 */
export class UserItem implements ng.IDirective {
  restrict = 'E';

  templateUrl = 'app/workspaces/share-workspace/user-item/user-item.html';
  replace = false;

  controller = 'UserItemController';
  controllerAs = 'userItemController';

  bindToController = true;

  scope: {
    [propName: string]: string;
  };

  /**
   * Default constructor.
   */
  constructor() {
    // scope values
    this.scope = {
      user: '=cheUser',
      callback: '=callback'
    };
  }
}
