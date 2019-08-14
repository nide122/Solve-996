#!/bin/bash -ilex
echo stop createTemplate-personal-web-0.0.1-SNAPSHOT
source /home/docker/tomcat/personal/stop.sh
echo start createTemplate-personal-web-0.0.1-SNAPSHOT
source /home/docker/tomcat/personal/start.sh