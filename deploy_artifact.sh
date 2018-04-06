#!/bin/sh

KUBERNETES_PROJECT_NAME=$1

mvn clean verify package

oc delete bc,dc,service,route zuul
oc new-app . --strategy=docker --name=zuul
oc start-build zuul -n ${KUBERNETES_PROJECT_NAME} --from-dir=spring-cloud-kubenernetes-example-zuul
