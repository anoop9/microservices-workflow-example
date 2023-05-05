# Introduction

This is an order fulfillment workflow example project, containing 3 microservices in spring-boot  and one of the microservices (order-svc) integrated with Netflix Conductor client for Workflow orchestration. The way how these microservices
interact is depicted in the following diagram. ![Alt text](pics/orderfullfillment.png?raw=true "order fulfillment workflow")

1) User initiate a checkout via UI (simulated as a REST endpoint call for our testing purpose by creating a new order using POST operation)
2) Order-svc (order microservice) create an order, with initially order status as 'PENDING'
3) Order microservice initiate the workflow/business process flow by starting the workflow using Netflix conductor client library - The workflow has two tasks - debiting payment and shpping the order
4) First task in workflow (HTTP task in Netflix conductor) get executed by debiting payment for the order via calling payment-svc (Payment microservice) and sending order (OrderDTO) details
5)Second task in workflow (HTTP task in Netflix conductor) gets executed which is shipping the order by calling shipment-svc (Shipment microservice) and sending order (OrderDTO) details
5) After both steps are completed, order-svc complete the order by setting order status as 'COMPLETED' and saving it. 

## Modules

### common

Contains common interface for services/calls that need to be used by order service inorder to finish an order. 
The respective services will implement these common interfaces

Also contains common object such as `OrderDTO` and error handling code.

### order-svc (Order Microservice)

Entry point of Order Fulfillment workflow. Contains Workflow Definition `OrderFulfillmentWorkflowService` and
implementation `OrderFulfillmentWorkflowServiceImpl`

Code is organized in the packages as below.
- service: contains business logic.
- common: common configuration such as Spring bean definition,
- persistence: Spring JPA related code.
- resource: REST API specific implementation and request and response mappers.
- conductor: has netflix conductor specific worker classes for starting a 'Task' in the workflow 

### payment-svc (Payment Microservice)

Contains implementation `DebitPaymentForOrderServiceImpl` which debit payment for the order based on OrderDTO

### shipment-svc (Shipment Microservice)

Contains implementation `ShipOrderServiceImpl`  which ship the order based on OrderDTO

# To run a local instance of Netflix conductor 
1) Install docker 
2) Follow the steps here to run conductor- https://conductor.netflix.com/devguide/running/docker.html 
3) If you have MAC with Apple M1 or M2 Chip, there are some issues with the **docker-compose.yaml** file in **/conductor/docker** folder. You need to make some changes specific for MAC which has ARM based architecture. Add "**platform: linux/x86_64**" extra line for elasticsearch and redis services in **docker-compose.yaml** as shown in snippet
![Alt text](pics/conductordocker.png?raw=true "docker compose changes")
4) Start conductor by `docker-compose up -d`. You can find the conductor UI at http://localhost:5000/ and the swagger endpoint at http://localhost:8080/

# Test create a workflow in Conductor
There are three separate curl request examples provided here in files - taskdef-curl, workflow-curl and testingworkflow-curl.
1) To create Conductor tasks - use the **taskdef-curl** file and POST that request. Make sure in the conductor UI, go to Tab ->Definitions->Tasks and you can find two new tasks - "http_task_debit_order_payment" and "http_task_ship_order". (NB: You can notice that due to MAC specific issues for docker, I have to provide task "uri": "http://host.docker.internal:8083/payments/debit" instead of http://localhost:8083/payments/debit)
2) To create Conductor Workflow which calls the previous tasks created - use the **workflow-curl** file and POST that request. Make sure in the conductor UI, go to Tab ->Definitions->Workflows and you can find one new workflow - "order_fullfillment_workflow".
3) Now you can test workflow by triggering manually using a curl request as given in **testingworkflow-curl** file (but to do this you have the microservices also running, which is explained in next section -**Build and Run Microservices**). You should be able to see a screen like this, if workflow is executed successfully
   ![Alt text](pics/workflow-example.png?raw=true "workflow executed manually")

# Build and Run Microservices

Make sure you have JDK 17 or higher and gradle installed.

To build `./gradlew clean build`


To run open 3 terminal windows/shell and enter
```commandline 
gradle :services:order-svc:bootRun
gradle :services:payment-svc:bootRun
gradle :services:shipment-svc:bootRun

or
//you might need to add 'chmod +x ./gradlew'  incase of permission issues and run following

./gradlew :services:order-svc:bootRun
./gradlew :services:payment-svc:bootRun
./gradlew :services:shipment-svc:bootRun
```
## Optional- To package and run as docker containers
```commandline 
gradle :services:order-svc:bootJar
gradle :services:payment-svc:bootJar
gradle :services:shipment-svc:bootJar
cd services/payment-svc
docker build -t payment-svc:latest .
docker run -d -p 8083:8083 --name payment-svc payment-svc
cd services/shipment-svc
docker build -t shipment-svc:latest .
docker run -d -p 8084:8084 --name shipment-svc shipment-svc
cd services/order-svc
docker build -t order-svc:latest .
docker run -d -p 8081:8081 --name order-svc order-svc
```





## To Test/ start workflow execution by creating a new Order

```commandline
curl --location --request POST 'localhost:8081/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
  "productId": 333333,
  "price": 28.99,
  "quantity": 2
}'
```

To get an order based on id and know its status
```commandline
curl --location --request GET 'localhost:8081/orders/1' \
--header 'Accept: application/json'
```

**Optional:** To debit payment for an order
```commandline
curl --location --request POST 'localhost:8083/payments/debit' \
--header 'Content-Type: application/json' \
--data-raw '{
  "orderId": 5,
  "productId": 5555,
  "price": 55.99,
  "quantity": 1
}'
```

**Optional:** To view list of all payments

```commandline
curl --location --request GET 'localhost:8083/payments' \
--header 'Accept: application/json'
```


**Optional:** To ship an order 

```commandline
curl --location --request POST 'localhost:8084/shipments/ship' \
--header 'Content-Type: application/json' \
--data-raw '{
  "orderId": 5,
  "productId": 5555,
  "price": 55.99,
  "quantity": 1
}'
```

**Optional:** To list all shipments
```commandline
curl --location --request GET 'localhost:8084/shipments' \
--header 'Accept: application/json'
```