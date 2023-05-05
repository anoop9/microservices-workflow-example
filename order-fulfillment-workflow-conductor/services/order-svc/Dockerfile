FROM openjdk:19
EXPOSE 8081
ARG JAR_FILE=build/libs/order-svc.jar
ADD ${JAR_FILE} order-svc.jar
ENTRYPOINT ["java","-jar","/order-svc.jar"]