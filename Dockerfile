FROM openjdk:11
WORKDIR /
RUN echo 'Adding .jar file to the image as app.jar'
ADD **/target/*.jar app.jar
RUN echo 'Opening Port: 7000'
EXPOSE 7000
RUN echo 'Starting app.jar...'
CMD java -jar app.jar



