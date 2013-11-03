Learning servlet & backbone & mysql(jdbc)


How to run
---------------

#### Prerequisites
- java1.6
- tomcat6
- mysql server

#### Step1 - download
- Download **target/learning.war** and move to tomcat/webapps
- start tomcat server(tomcat/bin/catalina.sh start), tomcat will automatically deploy the WAR ball. 

#### Step2 - config database
- start a MySQL server
- download **learning.sql** and run it in your MySQL server
- config MySQL connection parameters,find the file: **tomcat/webapps/learning/WEB-INF/web.xml**, change **context-param**
- restart tomcat server

  ```
    $ tomcat/bin/catalina.sh stop
    $ tomcat/bin/catalina.sh start
  ```

Development & Build
--------------------
#### Prerequisites
- jdk1.6
- tomcat6
- mysql server
- node.js
- Apache Ant

#### Front-end Development Environment
```
  # Install Grunt.js & Bower
  npm install -g grunt-cli
  npm install -g bower
  
  # Install 3rd-party libs for frontend dev
  cd web
  bower install
  npm install
  
  # Begin to Front-end development
  grunt init
  grunt watch
```

#### Application Build
```
  ant                 # front-end optimized building
  ant debug           # front-end not-optimized building
```
