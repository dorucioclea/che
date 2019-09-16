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
/**
 * This is complete factory templates.
 *
 * @author Oleksii Orel
 */
export class CheFactoryTemplates {

  static get MINIMAL(): string {
    return JSON.stringify({
      'v': '4.0',
      'workspace': {
        'attributes': {
          'editor': 'eclipse/che-theia/next',
          'plugins': 'eclipse/che-machine-exec-plugin/0.0.1'
        },
        'projects': [
          {
            'name': 'Spring',
            'attributes': {
              'languageVersion': [
                '1.6'
              ],
              'language': [
                'java'
              ]
            },
            'type': 'maven',
            'source': {
              'location': 'https://github.com/codenvy-templates/web-spring-java-simple.git',
              'type': 'git',
              'parameters': {
                'keepVcs': 'false',
                'branch': '3.1.0'
              }
            },
            'modules': [],
            'path': '/Spring',
            'mixins': [
              'git'
            ],
            'problems': []
          }
        ],
        'defaultEnv': 'wss',
        'name': 'wss',
        'environments': {
          'wss': {
            'machines': {
              'dev-machine': {
                'servers': {},
                'attributes': {
                  'memoryLimitBytes': '2147483648'
                }
              }
            },
            'recipe': {
              'content': 'eclipse/ubuntu_jdk8',
              'type': 'dockerimage'
            }
          }
        }
      }
    });
  }

  static get COMPLETE(): string {
    return JSON.stringify({
      'v': '4.0',
      'workspace': {
        'attributes': {
          'editor': 'eclipse/che-theia/next',
          'plugins': 'eclipse/che-machine-exec-plugin/0.0.1'
        },
        'commands': [],
        'projects': [
          {
            'name': 'Spring',
            'attributes': {
              'languageVersion': [
                '1.6'
              ],
              'language': [
                'java'
              ]
            },
            'type': 'maven',
            'source': {
              'location': 'https://github.com/codenvy-templates/web-spring-java-simple.git',
              'type': 'git',
              'parameters': {
                'keepVcs': 'false',
                'branch': '3.1.0'
              }
            },
            'modules': [],
            'path': '/Spring',
            'mixins': [
              'git'
            ],
            'problems': []
          }
        ],
        'defaultEnv': 'wss',
        'name': 'wss',
        'environments': {
          'wss': {
            'machines': {
              'dev-machine': {
                'servers': {},
                'attributes': {
                  'memoryLimitBytes': '2147483648'
                }
              }
            },
            'recipe': {
              'content': 'eclipse/ubuntu_jdk8',
              'type': 'dockerimage'
            }
          }
        }
      },
      'name': 'My Complete Template',
      'creator': {
        'name': 'Codenvy Factory',
        'email': 'factories@codenvy.com'
      }
    });
  }

  static get GIT(): string {
    return JSON.stringify({
      'v': '4.0',
      'workspace': {
        'attributes': {
          'editor': 'eclipse/che-theia/next',
          'plugins': 'eclipse/che-machine-exec-plugin/0.0.1'
        },
        'commands': [],
        'projects': [
          {
            'name': 'my-project',
            'type': 'blank',
            'source': {
              'location': 'http://git-project',
              'type': 'git'
            },
            'modules': [],
            'path': '/my-project',
            'mixins': [
              'git'
            ],
            'problems': []
          }
        ],
        'defaultEnv': 'wss',
        'name': 'wss',
        'environments': {
          'wss': {
            'machines': {
              'dev-machine': {
                'servers': {},
                'attributes': {
                  'memoryLimitBytes': '2147483648'
                }
              }
            },
            'recipe': {
              'content': 'eclipse/ubuntu_jdk8',
              'type': 'dockerimage'
            }
          }
        }
      }
    });
  }

}
