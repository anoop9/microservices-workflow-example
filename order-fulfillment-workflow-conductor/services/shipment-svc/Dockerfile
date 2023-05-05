FROM openjdk:19
EXPOSE 8084
ARG JAR_FILE=build/libs/shipment-svc.jar
ADD ${JAR_FILE} shipment-svc.jar
ENTRYPOINT ["java","-jar","/shipment-svc.jar"]