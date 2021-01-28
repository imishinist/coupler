
.PHONY: bootstrap
bootstrap:
	kubectl create namespace kafka

.PHONY: kafka-deploy
kafka-deploy:
	kubectl apply -f kafka-operator.yml -n kafka && \
	kubectl apply -f kafka-persistent-single.yaml -n kafka
