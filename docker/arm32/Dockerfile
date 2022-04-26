FROM sulrich423/adoptopenjdk:12.0.1_12-jdk-hotspot-arm

COPY tv-*.war tv.war

CMD echo "Europe/Berlin" > /etc/timezone && java -jar -Dspring.profiles.active=prod tv.war
