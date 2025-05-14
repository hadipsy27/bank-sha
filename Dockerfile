# Gunakan base image JDK
FROM openjdk:17-jdk-slim

# Tambahkan metadata
#LABEL maintainer="yourname@example.com"

# Buat folder kerja
WORKDIR /app

# Salin file JAR hasil build
COPY build/libs/*.jar app.jar

# Expose port Spring Boot (default 8080)
EXPOSE 8080

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
