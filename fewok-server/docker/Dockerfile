FROM java:8u111-jre
RUN echo "Asia/shanghai" > /etc/timezone
RUN cp -f /usr/share/zoneinfo/PRC /etc/localtime
VOLUME /tmp
ADD fewok.jar fewok.jar
ENV JAVA_OPTS="-server -Duser.timezone=Asia/Shanghai"
EXPOSE 39368
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-Dsun.jnu.encoding=UTF-8","-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector","-Djava.io.tmpdir=/tmp","-Djava.net.preferIPv6Addresses=false","-server","-Xms1024M","-Xmx1024M","-Xmn256M","-XX:ReservedCodeCacheSize=128M","-XX:InitialCodeCacheSize=128M","-XX:SurvivorRatio=8","-XX:+DisableExplicitGC","-XX:+UseConcMarkSweepGC","-XX:CMSFullGCsBeforeCompaction=0","-XX:+UseCMSCompactAtFullCollection","-XX:CMSInitiatingOccupancyFraction=80","-XX:+HeapDumpOnOutOfMemoryError","-jar","fewok.jar","--spring.profiles.active=prod"]