FROM node:13 AS node
WORKDIR /node
COPY captain-adhoc-frontend/ ./
RUN npm install
RUN npm run build

FROM maven:3-jdk-8 AS maven
WORKDIR /maven
COPY captain-adhoc-backend/src ./src
COPY captain-adhoc-backend/pom.xml ./pom.xml
COPY --from=node /node ./app/src/main/resources/static
RUN mvn clean package 


FROM openjdk:8-jre
WORKDIR /app
COPY --from=maven /maven/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","./app.jar"]
