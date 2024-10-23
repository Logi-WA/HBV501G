# Endpoints

## Assignment 2

1. `POST /auth/login` *(1)*
2. `GET /api/categories` *(1)*

## Assignment 3

### Implemented in UI

1. `GET /api/products` *(1)*  
    Retrieve all resources in a collection (e.g. All users, all items)
2. `GET /api/categories` *(1)*  
    Retrieve all resources in a collection (e.g. All users, all items)
3. `GET /api/products/{id}` *(1)*  
    Retrieve a single resource by ID (e.g. Item, user, order based on unique identifier)
4. `POST /api/users` *(2)*  
    Create new resource (e.g. New item, new user account)
5. `GET /api/users/me` *(2)*  
    Retrieve user-specific data (e.g. User profile, user settings)
6. `PATCH /api/users/me` *(2)*  
    Partially update resource details (e.g. update user profile, change multiple item details)
7. `PATCH /api/users/me/password` *(2)*  
    Partially update resource details (e.g. update user profile, change multiple item details
8. `POST /api/cart` *(2)*  
    Create an association between resources (e.g. Add item to users favourites)
9. `POST /api/wishlist` *(2)*  
    Create an association between resources (e.g. Add item to users favourites)
10. `GET /api/cart/cart` / `GET /api/cart` *(2)*  
    Retrieve associated resouces (e.g. User favourites, user orders)
11. `GET /api/cart/wishlist` / `GET /api/wishlist` *(2)*  
    Retrieve associated resouces (e.g. User favourites, user orders)
12. `PATCH /api/cart/{id}` *(3)*  
    Update nested resource (e.g. update specific attributes of items in an order)

### Not (All) Implemented in UI

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
12. `DELETE /api/wishlist` *(2)*
13. `GET /api/wishlist` *(2)*
14. `POST /api/wishlist` *(2)*
15. `PATCH /api/users/{id}/password` *(2)*

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
