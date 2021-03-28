FROM openjdk:8-jdk-alpine

MAINTAINER Ivan Hontarenko <callicoder@gmail.com>

VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=../chat-app/target/jWsChat.jar
ADD ${JAR_FILE} jWsChat.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/jWsChat.jar"]

