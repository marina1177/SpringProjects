# !/bin/bash

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q env_search-service)
# docker rmi $(docker images -q application)
docker rmi $(docker images -q prom/prometheus)
docker rmi $(docker images -q grafana/grafana)
