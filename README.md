# telemetry-proto

spring boot api using micrometer to send metrics, traces, and logs to observabilty oss. oss includes grafana, prometheus, loki, and tempo.

### required

+ [mvn](https://maven.apache.org/download.cgi)
+ [java 21](https://sdkman.io/jdks)
  + [sdkman](https://sdkman.io/install)
+ [docker](https://docs.docker.com/get-docker/)

## local setup

```shell
# start
docker compose up

# app
while true; do http localhost:8080/v1/trace; done
curl -v -H 'Accept: application/openmetrics-text; version=1.0.0; charset=utf-8' localhost:8080/actuator/prometheus
curl -v localhost:8080/actuator/health
curl -v localhost:8080/actuator/info

# grafana ui
http://localhost:3000

# prometheus admin ui
http://localhost:9090/graph
```

## kind cluster

```shell
# create kind cluster
https://github.com/stxkxs/data-cluster

# configure kubectl context
kubectl config set-context data-cluster --cluster=data-cluster

# load docker image 
## ./kind-deploy.sh
mvn clean install
docker build -t proto/telemetry-proto:0.1 .
kind load docker-image proto/telemetry-proto:0.1 --name data-cluster
kubectl apply -f deployment.local.yaml

# app
kubectl port-forward service/telemetry-proto 8080:8080 -n api

while true; do http localhost:8080/v1/trace; done
curl -v -H 'Accept: application/openmetrics-text; version=1.0.0; charset=utf-8' localhost:8080/actuator/prometheus
curl -v localhost:8080/actuator/health
curl -v localhost:8080/actuator/info

# grafana ui
# username: admin
# password: prom-operator
kubectl port-forward service/kube-prometheus-stack-grafana 8888:80 -n o11y
http://localhost:8888

# prometheus ui
kubectl port-forward service/kube-prometheus-stack-prometheus 9090:9090 -n o11y
http://localhost:9090/graph
```

_references_

+ https://spring.io/blog/2022/10/12/observability-with-spring-boot-3
+ https://tanzu.vmware.com/developer/guides/observability-reactive-spring-boot-3
+ https://micrometer.io/
+ https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.metrics
+ https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.micrometer-tracing
+ https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.logging.custom-log-configuration
+ https://github.com/loki4j/loki-logback-appender
+ https://prometheus.io/docs/introduction/overview/
+ https://grafana.com/docs/tempo/latest/
+ https://grafana.com/docs/grafana/latest/
+ https://grafana.com/docs/loki/latest/
