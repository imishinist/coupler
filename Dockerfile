FROM strimzi/kafka:0.20.1-kafka-2.6.0
USER root:root
RUN mkdir -p /opt/kafka/plugins/coupler
COPY ./coupler/target/coupler-1.0-SNAPSHOT.jar /opt/kafka/plugins/coupler/
USER 1001

