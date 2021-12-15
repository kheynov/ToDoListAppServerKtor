FROM openjdk:8-jdk
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/install/TodoListBackend/ /app/
WORKDIR /app/bin
CMD ["./TodoListBackend"]