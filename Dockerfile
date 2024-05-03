# Builds the plugin inside a docker container and runs a PaperMC server with it

# Base Image
FROM alpine:latest

# Environment variables
ARG SERVER_JAR_URL
RUN test -n "$SERVER_JAR_URL"
ENV RAM=2G

# Add packages
RUN apk add --no-cache curl git openjdk21 maven gradle

# Checkout out project at /build
RUN mkdir /build
WORKDIR /build

# Run BuildTools
COPY util util
RUN util/runBuildTools.sh

# Copy rest of the project
COPY . .

# Build the project
RUN gradle clean build

# Set up the server
RUN mkdir /app
WORKDIR /app
RUN cp /build/ops.json .
RUN echo "eula=true" > eula.txt
RUN curl -o "server.jar" "$SERVER_JAR_URL"

# Install the plugin
RUN mkdir plugins
RUN cp /build/build/libs/*.jar plugins/

# Remove the build directory
RUN rm -rf /build

# Run the server
CMD java -Xmx$RAM -Xms$RAM -server -jar "server.jar" nogui