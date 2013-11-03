learning servlet & backbone & mysql(jdbc)


How to run
---------------

1. Step1 - download
  1. front-end optimized version: Download **target/learning.war** and move to tomcat/webapps
  2. front-end src version:	Download **target/learning-src.war** and move to tomcat/webapps
  3. start tomcat server(tomcat/bin/catalina.sh start), tomcat will automatically deploy the WAR ball. 

2. Step2 - config database
  1. start a MySQL server
  2. download **learning.sql** and run it in your MySQL server
  3. config MySQL connection parameters,find the file: **tomcat/webapps/learning/WEB-INF/web.xml**, change **context-param**
  4. restart tomcat server

    ```
      $ tomcat/bin/catalina.sh stop
      $ tomcat/bin/catalina.sh start
    ```

