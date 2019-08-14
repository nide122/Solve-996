#!/bin/bash -ilex
echo stop createTemplate-admin-web-0.0.1-SNAPSHOT
source /home/docker/tomcat/admin/stop.sh
echo start createTemplate-admin-web-0.0.1-SNAPSHOT
source /home/docker/tomcat/admin/start.sh