# Etapa 1: Compilación con Gradle y JDK 21
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Dar permisos de ejecución al wrapper de Gradle
RUN chmod +x ./gradlew

# Compilamos el .jar sin ejecutar tests
RUN ./gradlew clean build -x test

# Etapa 2: Ejecución con JDK 21 ligero
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos solo el .jar generado (el más reciente)
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Exponemos el puerto de la app
EXPOSE 8092

# Comando de arranque
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
