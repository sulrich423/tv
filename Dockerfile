FROM eclipse-temurin:17.0.2_8-jdk

COPY target/tv-*.war tv.war

CMD echo "Europe/Berlin" > /etc/timezone && java -jar -Dspring.profiles.active=prod tv.war
