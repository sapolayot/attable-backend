FROM openjdk:8-jdk-alpine
# RUN mkdir ssl
# VOLUME /ssl
EXPOSE 8080
ENV TZ=Asia/Bangkok
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# ADD truststore.p12 /ssl
ADD target/AttableBackendGraphQL-0.0.1-SNAPSHOT.jar AttableBackendGraphQL-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","AttableBackendGraphQL-0.0.1-SNAPSHOT.jar","-Djavax.net.ssl.trustStore=/ssl/truststore.p12","-Djavax.net.ssl.trustStorePassword=truststore123"]
