#!/bin/bash
PROJ_HOME=/home/ec2-user/git/kdsedori
JETTY_PROCESS_ID=`ps -ef | grep -v grep | grep jetty | awk '{print $2}'`
kill -9 $JETTY_PROCESS_ID

cd $PROJ_HOME
/usr/bin/mvn jetty:run &
cd -
