# Endpoints

## Assignment 2

1. `POST /auth/login` *(1)*
2. `GET /api/categories` *(1)*

## Assignment 3

1. `GET /api/users` *(1)*
2. `POST /api/users` *(2)*
3. `DELETE /api/users/{id}` *(2)*
4. `GET /api/users/{id}` *(1)*
5. `PATCH /api/users/{id}` *(2)*
6. `GET /api/cart` *(1)*
7. `POST /api/cart` *(2)*
8. `PATCH /api/cart/{id}` *(3)*
9. `GET /api/orders/{id}` *(2)*
10. `GET /api/products` *(1)*
11. `GET /api/products/{id}` *(1)*
12. `DELETE /api/wishlist`
13. `GET /api/wishlist`
14. `POST /api/wishlist`

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
