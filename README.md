## REST service-adaptor for SOAP webservice calculator

### Description
Project REST-service-adaptor for SOAP webservice http://www.dneonline.com/calculator.asmx<br/>
xx.xxx.xxx.xxx:31677/swagger-ui.html<br/>
xx.xxx.xxx.xxx address minikube<br/>
### setting
rabbit <br/>
    execute import rabbit_rabbitmq-minikube.json by admin console.<br/>
keycloak<br/>
    execute import realm-export.json by admin console.<br/>
### file application.yml
in application.yml change next variables:<br/>
 spring:<br/>
   rabbitmq:<br/>
      host: XXX<br/>
      port: XXXX<br/>
keycloak:<br/>
    auth-server-url: http://host:port<br/>

### run

run in cmd  with admin rules  <br/>
     >"minikube docker-env"<br/>
add all veribles to build.bat<br/>
execute: <br/>
    >build.bat<br/>
create external service for calculator.<br/> 
    >"kubectl apply -f kubernetes\calculator-adapter.yml" 