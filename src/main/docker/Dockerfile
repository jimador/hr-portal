FROM java:openjdk-8-jdk-alpine

# add directly the war
ADD target/*.war /app.war

RUN sh -c 'touch /app.war'
VOLUME /tmp
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]

EXPOSE 8080
