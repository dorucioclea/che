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
package com.codenvy.qa;


import java.util.Comparator;

public class SortSurname implements Comparator<Employee> {

    public int compare(Employee e1, Employee e2) {
        return e1.getSurname().compareTo(e2.getSurname());
    }
}
