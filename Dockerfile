FROM openjdk:8
ADD target/hotelbookingservice-0.0.1-SNAPSHOT.jar hotelbookingservice-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "hotelbookingservice-0.0.1-SNAPSHOT.jar"]