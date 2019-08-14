#!/bin/bash 
docker run -d -p 8111:8111 --name createTemplate-provider --rm  -v /etc/timezone:/etc/timezone -v /etc/localtime:/etc/localtime -v "$(pwd)":/mnt --workdir /mnt frolvlad/alpine-oraclejdk8 sh -c "java  -Xms256m -Xmx768m -XX:PermSize=256m -XX:MaxPermSize=512m -jar createTemplate-provider-0.0.1-SNAPSHOT.jar"
