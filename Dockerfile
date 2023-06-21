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

## Ustalamy z jakiego obrazu bazowego korzystamy
#FROM ubuntu:20.04
#
## Ustawiamy strefę czasową - konieczne jest to aby przy instalacji
## javy nie było prośby o ustawienie strefy czasowej
#ENV TZ=Europe/Warsaw
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#
## Uruchamiamy zaktualizowanie system i instalację wymaganych pakietów
#RUN apt update && apt upgrade -y && \
#apt install -y \
#git \
#openjdk-17-jdk \
#maven
#
## Budujemy aplikację w oparciu o repozytorim gitowe i przenosimy
## zbudowaną aplikację do odpowiedniego katalogu
## Na koniec usuwamy zbędne pliki
#RUN mkdir /code && \
#cd /code && \
#git clone https://github.com/karolina-wisniewska/All-You-Need-Is-Diet-Plan.git . && \
#mvn package && \
#mkdir /opt/app && \
#mv /code/target/DemoApp.jar /opt/app && \
#cd / && \
#rm -r /code
#
#
## Usuwamy zbędne aplikacje - nie potrzebujemy ich bo
## już skompilowaliśmy naszą aplikację
#RUN apt remove -y \
#git \
#maven && \
#apt clean && \
#apt autoremove --purge && \
#rm -rf /var/lib/apt/lists
#
#EXPOSE 9090
#
## Ustalamy jaki katalog będzie domyślnym katalogiem roboczym
## po uruchomieniu kontenera, dzięki temu
#WORKDIR /opt/app
#
#CMD java -jar /opt/app/AllYouNeedIsDietPlan-0.0.1-SNAPSHOT.jar
