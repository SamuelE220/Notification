#Etapa 1: compilación con Gradle y JDK 21
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app

#Copiamos todo el proyecto
COPY . .

#Dar permisos de ejecución al wrapper de Gradle
RUN chmod +x ./gradlew

#Compilamos el .jar sin tests
RUN ./gradlew clean build -x test

#Etapa 2: ejecución con JDK 21 ligero
FROM eclipse-temurin:21-jdk
WORKDIR /app

#Copiamos el .jar compilado
COPY --from=build /app/build/libs/*.jar app.jar

#Puerto que expondrá tu app
EXPOSE 8092

#Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
