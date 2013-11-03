#!/bin/sh

javac -d classes -classpath $CLASSPATH:./lib/gson-2.2.4.jar:./lib/mysql-connector-java-5.0.8-bin.jar src/com/learning/**/*.java src/com/learning/*.java

cp -r lib ~/soft/apache-tomcat-6.0.37/webapps/learningservlet/WEB-INF
cp -r classes ~/soft/apache-tomcat-6.0.37/webapps/learningservlet/WEB-INF
cp -r etc/web.xml  ~/soft/apache-tomcat-6.0.37/webapps/learningservlet/WEB-INF
cp -r web/js ~/soft/apache-tomcat-6.0.37/webapps/learningservlet/
