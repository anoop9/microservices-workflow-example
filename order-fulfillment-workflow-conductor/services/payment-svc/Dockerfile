FROM openjdk:19
EXPOSE 8083
ARG JAR_FILE=build/libs/payment-svc.jar
ADD ${JAR_FILE} payment-svc.jar
ENTRYPOINT ["java","-jar","/payment-svc.jar"]