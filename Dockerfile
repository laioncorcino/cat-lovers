FROM openjdk

ADD build/libs/cat-lovers-0.0.1-SNAPSHOT.jar /opt/src/cat-lovers.jar

EXPOSE 8080

WORKDIR /opt/src/

ENTRYPOINT exec java $JAVA_OPTS  -jar /opt/src/cat-lovers.jar