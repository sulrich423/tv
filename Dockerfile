FROM eclipse-temurin:17.0.2_8-jdk-focal

COPY target/tv-*.war tv.war

RUN echo "Europe/Berlin" > /etc/timezone && ln -sf /usr/share/zoneinfo/Europe/Berlin /etc/localtime

CMD java -jar -Dspring.profiles.active=prod tv.war
