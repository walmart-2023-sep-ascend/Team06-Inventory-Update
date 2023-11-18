FROM openjdk:17
EXPOSE 6003
ADD target/inventory-update-docker.jar inventory-update-docker.jar
ENTRYPOINT ["java","-jar","inventory-update-docker.jar"]