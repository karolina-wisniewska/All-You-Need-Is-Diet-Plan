FROM maven:3.8.4-openjdk-17 AS build

RUN mkdir /code && \
    	cd /code && \
    	git clone https://github.com/karolina-wisniewska/All-You-Need-Is-Diet-Plan.git . && \
    	mvn package && \
    	mkdir /opt/app && \
    	mv /code/target/AllYouNeedIsDietPlan-0.0.1-SNAPSHOT.jar /opt/app && \
    	cd / && \
    	rm -r /code

EXPOSE 9090

WORKDIR /opt/app

CMD java -jar /opt/app/AllYouNeedIsDietPlan-0.0.1-SNAPSHOT.jar
