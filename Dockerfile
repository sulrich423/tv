FROM arm32v7/adoptopenjdk:12.0.1_12-jdk-hotspot

COPY target/tv-*.war tv.war

CMD echo "Europe/Berlin" > /etc/timezone && java -jar tv.war