rem run minikube docker-env
@ECHO run "minikube docker-env" with rule administrator
@ECHO add variables
@pause

REM To point your shell to minikube's docker-daemon, run:
REM @FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env') DO @%i  
call mvn clean package -DskipTests=true 
call mvn k8s:build
call mvn k8s:resource
call kubectl delete deploy calculator-adapter
call mvn k8s:apply