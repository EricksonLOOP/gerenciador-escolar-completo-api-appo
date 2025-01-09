FROM openjdk:21-jdk

WORKDIR /app

# Copiar o Maven Wrapper e os arquivos do código
COPY ./mvnw /app/mvnw
COPY ./.mvn /app/.mvn
COPY src /app/src
COPY pom.xml /app
COPY ./wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Torna o script mvnw executável
RUN chmod +x /app/mvnw

# Instala dependências e empacota a aplicação
RUN ./mvnw clean package -DskipTests

# Comando para rodar o Spring Boot
CMD ["java", "-jar", "/app/target/Opportunity-API-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
