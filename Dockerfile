FROM openjdk:8u121-jre-alpine

EXPOSE 8080

ENV JAVA_VERSON 1.8.0
ENV MAVEN_VERSION 3.3.9

RUN apk add --no-cache curl \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
    && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

RUN mkdir -p /data/app/bin /data/app/logs /data/app/config /data/app/src
ADD . /data/app/src

WORKDIR /data/app/src
RUN cp target/app.jar /data/app/bin/app.jar \
    && rm -rf /data/app/src /root/.m2/ \
    && chown -R 1001:0 /opt/app-root

USER 1001

WORKDIR /opt/app-root
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","bin/app.jar"]