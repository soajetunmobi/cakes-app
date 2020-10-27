FROM openjdk:11-slim

RUN \
    apt-get update && \
    apt-get -y install unzip curl git

RUN \
    cd /usr/local && \
    curl -L https://services.gradle.org/distributions/gradle-5.6.4-bin.zip -o gradle-5.6.4-bin.zip && \
    unzip gradle-5.6.4-bin.zip && \
    rm gradle-5.6.4-bin.zip


ENV GRADLE_HOME=/usr/local/gradle-5.6.4
ENV PATH=$PATH:$GRADLE_HOME/bin

ADD build.gradle /backend/build.gradle
ADD settings.gradle /backend/settings.gradle

WORKDIR "/backend"

RUN gradle --no-daemon downloadDependencies

ENTRYPOINT ["gradle"]

CMD ["bootRun"]