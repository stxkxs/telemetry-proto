#!/usr/bin/env bash

mvn clean install
docker build -t proto/telemetry-proto:0.1 .
kind load docker-image proto/telemetry-proto:0.1 --name data-cluster
kubectl apply -f deployment.local.yaml
