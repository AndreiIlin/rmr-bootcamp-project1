
OpenAPI Swagger documentation: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

OpenAPI Yaml: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)


# Инструкция по развертыванию и тестированию

Запуск backend-сервиса на локальной машине без тестов:
```
docker run --rm -d -p 5432:5432 -e POSTGRES_DB=truestore -e POSTGRES_PASSWORD=password postgres && mvn spring-boot:run
```

Запуск тестов на локальной машине:
```
docker run --rm -d -p 5432:5432 -e POSTGRES_DB=truestore -e POSTGRES_PASSWORD=password postgres && mvn test
```

Удаление приложения:
1. Ctrl+C в консоли что закрыть приложение;
2. Команда в консоли, чтобы остановить docker контейнер с postgres (автоудаление):
```
docker stop $(docker ps -q --filter ancestor=postgres)
```

Запуск контейнеризованного backend-сервиса на локальной машине без тестов:
```
docker-compose up --build
```

Удаление контейнеризованного backend-сервиса на локальной машине:
```
docker-compose down -v
```
