# Introduction

This is an order fulfillment workflow example project, containing 3 microservices in spring-boot. The way how these microservices
interact is depicted in the following diagram. ![Alt text](orderfullfillment.png?raw=true "order fulfillment workflow")

1) User initiate a checkout via UI
2) Order-svc (order microservice) create an order, with initially order status as 'PENDING'
3) Order microservice initiate the workflow/business process flow - by debiting payment for the order via calling payment-svc (Payment microservice) and sending order (OrderDTO) details
4) Order microservice does the second step in workflow which is shipping the order by calling shipment-svc (Shipment microservice) and sending order (OrderDTO) details
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

### payment-svc (Payment Microservice)

Contains implementation `DebitPaymentForOrderServiceImpl` which debit payment for the order based on OrderDTO

### shipment-svc (Shipment Microservice)

Contains implementation `ShipOrderServiceImpl`  which ship the order based on OrderDTO

# Build and Test

Make sure you have JDK 17 and gradle installed.

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


## To Test/ start workflow execution

```commandline
curl --location --request POST 'localhost:8081/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
  "productId": 30979484,
  "price": 28.99,
  "quantity": 2
}'
```

To get an order based on id and know its status
```commandline
curl --location --request GET 'localhost:8081/orders/1' \
--header 'Accept: application/json'
```

To debit payment for an order
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

To view list of all payments

```commandline
curl --location --request GET 'localhost:8083/payments' \
--header 'Accept: application/json'
```


To ship an order 

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

To list all shipments
```commandline
curl --location --request GET 'localhost:8084/shipments' \
--header 'Accept: application/json'
```