#!/bin/bash

user=$1

docker build . -t $user/sample-kafka-connector
docker push $user/sample-kafka-connector
