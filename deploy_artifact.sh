#!/bin/sh

KUBERNETES_PROJECT_NAME=$1
APP_NAME=$2

# run tests and build artifact
mvn clean verify package

# flush k8s project
oc delete bc,dc,service,route ${APP_NAME}

# build & deploy
oc new-app . --strategy=docker --name=${APP_NAME}
oc start-build ${APP_NAME} -n ${KUBERNETES_PROJECT_NAME} --from-dir=.
