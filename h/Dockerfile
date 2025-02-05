# base image
FROM openjdk:17-jdk-alpine

WORKDIR /app

# label
LABEL authors="lhh"

# jar 파일 복사
COPY h/build/libs/*.jar app.jar

# port expose
EXPOSE 8083

# application run
CMD ["java", "-jar", "app.jar"]

# ./gradlew build
# docker build -t app .
# docker images
# docker run -p 8083:8083 app
