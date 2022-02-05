# Compile API jar
# Can be commented out if building on system, uncomment below
FROM adoptopenjdk/openjdk11 as build
WORKDIR /application
COPY . .
RUN ./mvnw clean package -DskipTests

# Prepare application, could be skipped in future release
FROM adoptopenjdk/openjdk11:alpine-jre as builder
WORKDIR /application
COPY --from=build /application/target/*.jar application.jar
# Uncomment below if building on system
#COPY target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# Run application
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-XX:MinRAMPercentage=50.0", "-XX:MaxRAMPercentage=90.0", "org.springframework.boot.loader.JarLauncher"]