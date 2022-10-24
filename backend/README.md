
OpenAPI Swagger documentation: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

OpenAPI Yaml: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)


# Инструкция по развертыванию и тестированию


Запуск контейнеризованного backend-сервиса на локальной машине:
```
docker-compose up --build -d
```

Удаление контейнеризованного backend-сервиса на локальной машине:
```
docker-compose down -v
```


Запуск backend-сервиса на локальной машине c базой в докере (для отладки):
```
export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/truestore \
&& export JDBC_DATABASE_USERNAME=postgres \
&& export JDBC_DATABASE_PASSWORD=password \
&& docker run --rm -d -p 5432:5432 -e POSTGRES_DB=truestore -e POSTGRES_PASSWORD=password postgres \
&& mvn spring-boot:run
```

Запуск тестов на локальной машине и базой в докере (для отладки):
```
export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/truestore \
&& export JDBC_DATABASE_USERNAME=postgres \
&& export JDBC_DATABASE_PASSWORD=password \
&& docker run --rm -d -p 5432:5432 -e POSTGRES_DB=truestore -e POSTGRES_PASSWORD=password postgres \
&& mvn test
```

Запуск тестов и сборка на локальной машине с checkstyle перед Pull Request:
```
export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/truestore \
&& export JDBC_DATABASE_USERNAME=postgres \
&& export JDBC_DATABASE_PASSWORD=password \
&& docker run --rm -d -p 5432:5432 -e POSTGRES_DB=truestore -e POSTGRES_PASSWORD=password postgres \
&& mvn clean install
```

Удаление приложения:
1. Ctrl+C в консоли что закрыть приложение;
2. Команда в консоли, чтобы остановить docker контейнер с postgres (автоудаление):
```
docker stop $(docker ps -q --filter ancestor=postgres)
```



