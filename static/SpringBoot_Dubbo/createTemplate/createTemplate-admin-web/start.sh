#!/bin/bash
docker run -d -p 8082:8082 --name createTemplate-admin-web --rm  -v /etc/timezone:/etc/timezone -v /etc/localtime:/etc/localtime -v "$(pwd)":/mnt --workdir /mnt frolvlad/alpine-oraclejdk8 sh -c "java  -Xms256m -Xmx768m -XX:PermSize=256m -XX:MaxPermSize=512m -jar createTemplate-admin-web-0.0.1-SNAPSHOT.jar"
