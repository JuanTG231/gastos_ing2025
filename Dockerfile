# ============================
# Etapa 1: Construcción
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar pom.xml y descargar dependencias (para aprovechar la cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# ============================
# Etapa 2: Ejecución
# ============================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el jar construido desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Render asigna dinámicamente el puerto en la variable de entorno $PORT
EXPOSE 8080
ENV PORT=8080

# Iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]