ARG DOCKER
FROM $DOCKER
ARG APP_VER
ENV VER=${APP_VER}
ENV JAVA_OPTS=""
WORKDIR /application
COPY rococo-gateway-$VER.jar ./

EXPOSE 8080
CMD java -jar -Dspring.profiles.active=docker $JAVA_OPTS rococo-gateway-$VER.jar
