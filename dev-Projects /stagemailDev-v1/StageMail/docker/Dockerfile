FROM ubuntu:14.04
MAINTAINER Vikash <vikash.singh@bizruntime.com>

#Oracle Java 7
RUN \
	apt-get update && \
	apt-get -y upgrade && \
	apt-get install -y python-software-properties software-properties-common wget supervisor && \
	add-apt-repository -y ppa:webupd8team/java && \
	echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
	apt-get update && apt-get install -y oracle-java7-installer 
ENV JAVA_HOME /usr/lib/jvm/java-7-oracle
#Add Bitbucket Authentication
RUN mkdir -p /root/.ssh
ADD id_rsa /root/.ssh/id_rsa
RUN chmod 700 /root/.ssh/id_rsa && \
	echo "Host bitbucket.org\n\tStrictHostKeyChecking no\n" > /root/.ssh/config

#Git clone the repository
RUN apt-get install -y git && \
	cd /opt && \
	git clone  ssh://git@bitbucket.org/guypSyml-admin/stagemail.git && \
	echo "deb http://dl.bintray.com/sbt/debian /" > /etc/apt/sources.list.d/sbt.list && \
	apt-get update && apt-get install --force-yes -y sbt && \
	cd /opt/stagemail/StageMail && sbt compile 
	
# Generate Configuration file from Environment Variables
RUN echo '[program:play]\ndirectory=/opt/stagemail/StageMail\ncommand=sbt run' > /etc/supervisor/conf.d/play.conf && \
	echo '#!/bin/bash\nsed -i "s/POSTGRES_URL/$POSTGRES_URL/;s/POSTGRES_USER_NAME/$POSTGRES_USER_NAME/;s/$POSTGRES_PASSWORD/$$POSTGRES_PASSWORD/;s/COUCHBASE_HOST_1/$COUCHBASE_HOST_1/;s/COUCHBASE_HOST_2/$COUCHBASE_HOST_2/;s/COUCHBASE_HOST_3/$COUCHBASE_HOST_3/;s/COUCHBASE_BUCKET_NAME/$COUCHBASE_BUCKET_NAME/;s/COUCHBASE_BUCKET_PASSWORD/$COUCHBASE_BUCKET_PASSWORD/;" /opt/stagemail/StageMail/conf/config.properties\nsupervisord -n -c /etc/supervisor/supervisord.conf' > /start && \
	chmod +x /start

CMD /start
#CMD [ "/usr/bin/supervisord", "-n", "-c", "/etc/supervisor/supervisord.conf" ]
