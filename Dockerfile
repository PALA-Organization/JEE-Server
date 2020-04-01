FROM tomcat
COPY /target/server-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/jee-server.jar