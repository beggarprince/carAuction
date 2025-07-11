#!/bin/bash

curl -X POST http://localhost:8808/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
        "username":"demo@site.com",
        "password":"password123",
        "name":"Demo",
        "lastName":"User"
      }'

