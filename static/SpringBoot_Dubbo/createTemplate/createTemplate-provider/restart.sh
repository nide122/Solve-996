#!/bin/bash -ilex
echo stop createTemplate-provider-0.0.1-SNAPSHOT
source /home/docker/tomcat/provider/stop.sh
echo start createTemplate-provider-0.0.1-SNAPSHOT
source /home/docker/tomcat/provider/start.sh