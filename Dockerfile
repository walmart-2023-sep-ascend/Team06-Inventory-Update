FROM openjdk:17
EXPOSE 6003
ADD target/inventory-update-service.jar inventory-update-service.jar
ENTRYPOINT ["java","-jar","inventory-update-service.jar"]