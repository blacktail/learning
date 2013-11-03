#!/bin/sh

ant

cp target/learning.war ~/soft/apache-tomcat-6.0.37/webapps

~/soft/apache-tomcat-6.0.37/bin/catalina.sh run
