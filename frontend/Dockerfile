FROM node:18.10-alpine AS build
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY ./ /app
RUN npm run build

FROM nginx:1.23.1-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/build /usr/share/nginx/html
