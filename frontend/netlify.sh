#!/usr/bin/env bash

zip -r website.zip ./build

curl -H "Content-Type: application/zip" \
      -H "Authorization: Bearer $NETLIFY_ACCESS_TOKEN" \
      --data-binary "@website.zip" \
      https://api.netlify.com/api/v1/sites/$NETLIFY_SUBDOMAIN.netlify.com/deploys