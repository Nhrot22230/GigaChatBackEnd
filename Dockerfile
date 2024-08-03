# Utiliza una imagen base de OpenJDK para ejecutar el archivo WAR
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo WAR generado al directorio de trabajo del contenedor
COPY build/libs/giga-chat-api-0.0.1-SNAPSHOT.war /app/giga-chat-api.war

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/giga-chat-api.war"]