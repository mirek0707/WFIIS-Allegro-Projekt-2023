ARG GRADLE_IMAGE=gradle:jdk17
ARG JAVA_IMAGE=eclipse-temurin:17
# BUILD
FROM ${GRADLE_IMAGE} as build

WORKDIR /src
COPY ./ /src

RUN apt update
RUN apt install -y unzip
RUN rm -rf /var/lib/apt/lists/*
RUN gradle --no-daemon clean build
RUN unzip ./build/libs/livescore-service.jar -d /app

FROM ${JAVA_IMAGE} as run
VOLUME /tmp
COPY --from=build app/BOOT-INF/lib /app/lib
COPY --from=build app/META-INF /app/META-INF
COPY --from=build app/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","pl.flashgoal.livescore.LivescoreServiceApp"]