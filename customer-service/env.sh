#!/bin/bash

# chmod 755 myscript.sh
export SPRING_PROFILES_ACTIVE=development
export POSTGRES_DB=jdbc:postgresql://localhost:5432/cust_mgmt
export POSTGRES_USER=postgres
export POSTGRES_PASS=postgres
export CUSTOMER_APP_URL=http://localhost:8180/api
export TRANSACTION_APP_URL=http://localhost:8280/api
export FRONTEND_APP_URL=http://localhost:4200
