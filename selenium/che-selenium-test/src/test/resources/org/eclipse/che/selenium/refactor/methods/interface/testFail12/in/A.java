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
package renameMethodsInInterface.testFail12;
//can't rename m to k - defined in subclass
class A implements I{
    public void m(){}
}
class B extends A{
    public void k(){}
}
interface I{
    void m();
}
