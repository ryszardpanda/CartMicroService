FROM openjdk:21-jdk

LABEL maintainer="Rychu"

COPY target/CartMicroService-0.0.1-SNAPSHOT.jar CartMicroService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "CartMicroService-0.0.1-SNAPSHOT.jar"]