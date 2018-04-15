FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY target/kalah-0.1.jar $PROJECT_HOME/kalah-0.1.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://springboot-mongo:27017/kalah-0.1","-Djava.security.egd=file:/dev/./urandom","-jar","./kalah-0.1.jar"]