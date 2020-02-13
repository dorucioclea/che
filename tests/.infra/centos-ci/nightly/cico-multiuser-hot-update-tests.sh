#!/usr/bin/env bash
# Copyright (c) 2018 Red Hat, Inc.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
set -e

etupEnvs
installKVM
installDependencies
installDockerCompose
installAndStartMinishift
loginToOpenshiftAndSetDevRole
installCheCtl
deployCheIntoCluster
seleniumTestsSetup

bash selenium-tests.sh \
--host=${CHE_ROUTE} \
 --port=80 \
 --multiuser \
 --threads=1 \
 --test=org.eclipse.che.selenium.hotupdate.rolling.**

echo "=========================== THIS IS POST TEST ACTIONS =============================="

saveSeleniumTestResult
getOpenshiftLogs
archiveArtifacts "nightly-hot-update-tests.sh"