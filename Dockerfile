# Stage 1: Build the application
FROM openjdk:24-ea-jdk-slim AS build

WORKDIR /app

# Install maven manually to avoid wrapper issues with CRLF on Windows
RUN apt-get update && apt-get install -y maven

COPY pom.xml ./
# Resolve dependencies
RUN mvn dependency:go-offline

COPY src ./src

# Build the WAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the application on Tomcat
FROM openjdk:24-ea-jdk-slim

ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

# Install curl and tar to download Tomcat
RUN apt-get update && apt-get install -y curl tar && rm -rf /var/lib/apt/lists/*

# Download and install Tomcat 11
ENV TOMCAT_VERSION 11.0.0-M16
RUN mkdir -p $CATALINA_HOME && \
  curl -fSL https://archive.apache.org/dist/tomcat/tomcat-11/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz -o tomcat.tar.gz && \
  tar -xvf tomcat.tar.gz --strip-components=1 -C $CATALINA_HOME && \
  rm tomcat.tar.gz

# Remove default apps
RUN rm -rf $CATALINA_HOME/webapps/*

# Copy the WAR from the build stage
COPY --from=build /app/target/umurimoHub-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/umurimoHub.war

# Expose HTTP port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
