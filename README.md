# Endpoints

## Assignment 2

1. `POST /auth/login` *(1)*
2. `GET /api/categories` *(1)*

## Assignment 3

1. `GET /api/users` (1)
2. `POST /api/users` (2)
3. `DELETE /api/users/{id}` (2)
4. `GET /api/users/{id}` (1)
5. `PATCH /api/users/{id}` (2)  

## Examples

### Assignment 2 Examples

1. `curl -X POST localhost:8080/auth/login -H "Content-Type: application/json" -d "{\"username\": \"user\", \"password\": \"password\"}"`
2. `curl localhost:8080/api/categories`

### Assignment 3 Examples

1. `curl -X GET http://localhost:8080/api/users`
2. `curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d "{\"email\": \"new.email@example.com\"}"`
3. `curl -X DELETE http://localhost:8080/api/users/{id}`
4. `curl -X GET http://localhost:8080/api/users/{id}`
5. `curl -X PATCH http://localhost:8080/api/users/1 -H "Content-Type: application/json" -d "{\"email\": \"new.email@example.com\"}"`