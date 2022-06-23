FROM openjdk:11
WORKDIR /
RUN echo 'Adding .jar file to the image as app.jar'
ADD target/*.jar app.jar
RUN echo 'Adding deployment-application.properties file to the image '
ADD deployment-application.properties application.properties
RUN echo 'Opening Port: 7000'
EXPOSE 7000
RUN echo 'Starting app.jar...'
CMD java -jar app.jar



