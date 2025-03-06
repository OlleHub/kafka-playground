OC=oc
HELM=helm
APPS=./apps

.PHONY: install deploy-apps

install:
	@echo "Installing Red Hat build of Apache Kafka"
	$(OC) apply -f kafka-application.yaml

deploy-apps:
	@echo "Deploying checkout apps"
	$(HELM) install backend $(APPS) --set appName=checkout-backend,buildConfigType=quarkus
	$(HELM) install eventgenerator $(APPS) --set appName=checkout-eventgenerator,buildConfigType=quarkus
	hostName=$(shell $(OC) get route checkout-backend -o jsonpath='{.spec.host}') && \
	$(HELM) install frontend $(APPS) --set appName=checkout-frontend,buildConfigType=angular,hostName=$$hostName
	$(OC) start-build checkout-backend
	$(OC) start-build checkout-eventgenerator
	$(OC) start-build checkout-frontend
