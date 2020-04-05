FROM maven:3.6.3-jdk-11
ARG JAR_FILE=target/*.jar
COPY . /jee-server/
WORKDIR /jee-server/
RUN mvn package
ENTRYPOINT ["java", "-jar", "/jee-server/target/server-last.jar"]

