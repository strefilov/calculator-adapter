## REST service-adaptor for SOAP webservice calculator

### Description
Project REST-service-adaptor for SOAP webservice http://www.dneonline.com/calculator.asmx
xx.xxx.xxx.xxx:31677/swagger-ui.html
xx.xxx.xxx.xxx address minikube
### setting
rabbit 
    execute import rabbit_rabbitmq-minikube.json by admin console.
keycloak
    execute import realm-export.json by admin console.
### file application.yml
in application.yml change next variables:
 spring:
   rabbitmq:
      host: XXX
      port: XXXX
keycloak:
    auth-server-url: http://host:port

### run

run in cmd  with admin rules  
 >"minikube docker-env"
add all veribles to build.bat
execute: 
    >build.bat
create external service for calculator. 
    >"kubectl apply -f kubernetes\calculator-adapter.yml" 