# Исходный код frontend-команды

Адрес веб-клиента: [http://localhost](http://localhost)

Сборка прокета и запуск на локальной машине в Docker контейнере:
```
docker build -t webclient . && docker run --rm -d -p 80:80 webclient
```

Остановка и удаление Docker контейнера на локальной машине:
```
docker stop $(docker ps -q --filter ancestor=webclient)  
```
