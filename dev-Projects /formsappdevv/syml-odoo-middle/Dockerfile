## FORMS APPLICATION
FROM ubuntu:15.04
MAINTAINER Vijay <vijay.kumar@bizruntime.com>

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
        echo "Host github.com\n\tStrictHostKeyChecking no\n" > /root/.ssh/config

#Download tomcat
#RUN cd /opt
#RUN wget mirrors.koehn.com/apache/tomcat/tomcat-8/v8.0.23/bin/apache-tomcat-8.0.23.tar.gz
#RUN tar -xvf apache-tomcat-8.0.23.tar.gz

#Git clone the repository
RUN apt-get install -y git && \
        cd /opt && \
        git clone git@github.com:guysyml/formsapp.git && \
        apt-get update && apt-get install --force-yes -y maven && \
        cd /opt/formsapp/syml-odoo-middle && mvn clean install && \
        cd /opt && wget mirrors.koehn.com/apache/tomcat/tomcat-8/v8.0.23/bin/apache-tomcat-8.0.23.tar.gz && tar -xvf apache-tomcat-8.0.23.tar.gz && \
        cd /opt/formsapp/syml-odoo-middle/target && mv *.war formsapp.war && cp formsapp.war  /opt/apache-tomcat-8.0.23/webapps

# Expose the default tomcat port
EXPOSE 8080

RUN  /opt/apache-tomcat-8.0.23/bin/catalina.sh start && sleep 10 && \
     echo '#!/bin/bash\nsed -i "s/OPENERP_HOST_NAME/$OPENERP_HOST_NAME/;s/OPENERP_PORT/$OPENERP_PORT/;s/OPENERP_DB_NAME/$OPENERP_DB_NAME/;s/OPENERP_USERNAME/$OPENERP_USERNAME/;s/OPENERP_PASSWORD/$OPENERP_PASSWORD/;s/COUCHBASE_HOST_2/$COUCHBASE_HOST_2/;s/COUCHBASE_BUCKET_NAME/$COUCHBASE_BUCKET_NAME/;s/COUCHBASE_BUCKET_PASSWORD/$COUCHBASE_BUCKET_PASSWORD/;" /opt/apache-tomcat-8.0.23/webapps/formsapp/WEB-INF/classes/config.properties' > /start && \

        chmod +x /start

# Start the tomcat
#CMD service tomcat7 start && tail -f /var/lib/tomcat7/logs/catalina.out

#CMD [ "/usr/bin/supervisord", "-n", "-c", "/etc/supervisor/supervisord.conf" ]
