# Endpoints

## Assignment 2

1. `POST /auth/login` *(1)*
2. `GET /api/categories` *(1)*

## Assignment 3

### Ass. 3 - Endpoints

1. `GET /api/products` *(1)*
    Retrieve all resources in a collection (e.g. All users, all items)
2. `GET /api/categories` *(1)*
    Retrieve all resources in a collection (e.g. All users, all items)
3. `GET /api/users` *(1)*
    Retrieve all resources in a collection (e.g. All users, all items)
4. `GET /api/products/{id}` *(1)*
    Retrieve a single resource by ID (e.g. Item, user, order based on unique identifier)
5. `POST /api/users` *(2)*
    Create new resource (e.g. New item, new user account)
6. `GET /api/users/me` *(2)*
    Retrieve user-specific data (e.g. User profile, user settings)
7. `PATCH /api/users/me` *(2)*
    Partially update resource details (e.g. update user profile, change multiple item details)
8. `PATCH /api/users/me/password` *(2)*
    Partially update resource details (e.g. update user profile, change multiple item details)
9. `POST /api/cart` *(2)*
    Create an association between resources (e.g. Add item to users favorites)
10. `POST /api/wishlist` *(2)*
    Create an association between resources (e.g. Add item to users favorites)
11. `GET /api/cart` *(2)*
    Retrieve associated resources (e.g. User favorites, user orders)
12. `GET /api/wishlist` *(2)*
    Retrieve associated resources (e.g. User favorites, user orders)
13. `PATCH /api/cart/{id}` *(3)*
    Update nested resource (e.g. update specific attributes of items in an order)

## Assignment 4

New view `admin` added. Accessible in the dropdown menu in the top right for logged in users.

### Ass. 4 - Functional Endpoints

1. `GET /api/users` *(1)*
    The new admin dashboard provides a table of all users in the database.
2. `GET /api/products` *(1)*
    The admin dashboard also provides a table of all products in the database.
3. `PATCH /api/products/{id}/name` *(1)*
    Allows updating the name of a product.
4. `PATCH /api/products/{id}/description` *(1)*
    Allows updating the description of a product.
5. `POST /api/products` *(2)*
    A button has been added to the wishlist page to add a product to a cart.
6. `DELETE /api/wishlist/{wishlistItemId}` *(2)*
    A button has been added to the wishlist page to remove a product from a wishlist.
7. `DELETE /api/cart/{cartItemId}` *(2)*
    A button has been added to the cart page to remove a product from a cart.
8. `PATCH /api/cart/{cartItemId}` *(3)*
    Buttons have been added to the cart page to increment/decrement the quantity of an item in a cart
9. `POST /api/wishlist/addAllToCart` *(3)* - (Submit batch data)
    Button has been added to the wishlist page that adds all items in the users wishlist to the users cart
10. `DELETE /api/wishlist/clear` *(3)* - (Bulk delete resources)
    Button has been added to the wishlist page that remove all items from the users wishlist
11. `POST /api/cart/buy` *(3)* - (Submit batch data)
    Button has been added to the cart page that 'buys' all items in the users cart
