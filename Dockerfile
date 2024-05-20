# Builds the plugin inside a docker container and runs a PaperMC server with it

# Base Image
FROM alpine:latest

# Environment variables
ARG SERVER_JAR_URL
RUN test -n "$SERVER_JAR_URL"
ENV RAM=2G

# Add packages
RUN apk add --no-cache curl git openjdk21 maven gradle

# Set up the server
RUN mkdir /app
WORKDIR /app
RUN curl -o "server.jar" "$SERVER_JAR_URL"
RUN java -Xmx$RAM -Xms$RAM -server -jar "server.jar" nogui
# The server will stop, as the EULA is not yet accepted, this way this step can be cached

# Checkout out project at /build
RUN mkdir /build
WORKDIR /build

# Run BuildTools
COPY util util
RUN util/runBuildTools.sh
# This way the Spigot build process can be cached, even when changing the plugin's source code

# Copy rest of the project
COPY . .

# Build the project
RUN gradle clean build

# Configuring the server
WORKDIR /app
RUN echo "eula=true" > eula.txt
RUN cp /build/ops.json .

# Install the plugin
RUN cp /build/build/libs/*.jar plugins/

# Remove the build directory
RUN rm -rf /build

# Run the server
CMD java -Xmx$RAM -Xms$RAM -server -jar "server.jar" nogui