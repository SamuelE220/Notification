#Etapa 1: Build - Usamos imagen base con JDK 21
FROM eclipse-temurin:21-jdk-alpine AS build

#Establecemos el directorio de trabajo
WORKDIR /app

#Copiamos los archivos de configuración de Gradle primero (mejor cache)
COPY gradle gradle
COPY gradlew gradlew.bat build.gradle settings.gradle ./
RUN chmod +x ./gradlew

#Descargamos dependencias primero para aprovechar el cache de Docker
RUN ./gradlew dependencies --no-daemon

#Copiamos el resto del código fuente
COPY src src

#Compilamos el .jar sin tests
RUN ./gradlew clean build -x test --no-daemon

#Etapa 2: Runtime - Usamos JRE más ligero para ejecutar
FROM eclipse-temurin:21-jre-alpine

#Creamos usuario no-root para mayor seguridad
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001

#Establecemos el directorio de trabajo
WORKDIR /app

#Copiamos el .jar compilado desde la etapa de build
COPY --from=build /app/build/libs/*.jar ./

#Cambiamos al usuario no-root
USER spring:spring

#Puerto que expondrá tu app
EXPOSE 8092

#Comando de arranque con optimizaciones de JVM
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", \
    "notification-0.0.1-SNAPSHOT.jar"]
