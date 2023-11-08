#!/bin/bash

docker pull wiremock/wiremock:2.35.0
docker run --name rococo-mock -p 8090:8090 -v "/$(pwd)/wiremock/rest:/home/wiremock" -d wiremock/wiremock:2.35.0 --global-response-templating --enable-stub-cors