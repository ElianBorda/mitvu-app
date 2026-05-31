# ============================================================
#  Dockerfile — Backend Spring Boot (miTVU) para Railway
# ============================================================

# ── Etapa 1: Build ──────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiar el Maven Wrapper y el POM primero (aprovecha caché de capas)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias (cacheado si el pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar (sin tests, los tests corren en CI)
RUN ./mvnw clean package -DskipTests -Dspring.profiles.active=prod -B

# ── Etapa 2: Runtime ────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app

# Crear usuario no-root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto (Railway lo sobreescribe con $PORT)
EXPOSE 3001

# Arrancar la app con el perfil de producción
ENTRYPOINT ["java", \
  "-Dspring.profiles.active=prod", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
