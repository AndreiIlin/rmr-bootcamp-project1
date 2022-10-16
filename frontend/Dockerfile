FROM node:18.10-alpine AS build
ARG BACKEND_URL
ENV BACKEND_HOST $BACKEND_URL
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY ./ /app
RUN npx eslint --max-warnings 0 .
RUN npm run build

FROM nginx:1.23.1-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/build /usr/share/nginx/html
