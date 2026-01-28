#pull the base image 
FROM eclipse-temurin:25-jdk-noble
#create work dir
WORKDIR /app
#copy jar file in directory
COPY ./build/libs/springSecurity-*.jar /app/springSecurity.jar
#run the jar file 
ENTRYPOINT ["java", "-jar", "/app/springSecurity.jar"]