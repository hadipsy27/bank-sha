# Gunakan base image Java
FROM eclipse-temurin:17-jdk

# Buat working directory
WORKDIR /app

# Copy file JAR ke dalam container
COPY target/*.jar app.jar

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
