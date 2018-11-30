# Mini shop

Minishop est une api REST simplifiée pour un magasin en ligne comportant des clients, des produits, des paniers d’achats et des commandes. 


## Jeu de données 

Nous avons créé un jeu de données dans MockPersistence. Afin de faciliter les tests sur le projet, voici les différents objets créés et leur identitifiants.
**Customer**
6 objets Customer
- customer 3000, possède les carts 5000 (check_out) et 5004 (open), ainsi que l'order 7000 (paid)
- customer 3001, possède le cart 5001 (open)
- customer 3002, possède le cart 5002 (open)
- customer 3003, possède le cart 5003 (check_out) et l'order 7001 (open)
- customer 3004 
- customer 3005

**Product**

12 objets Product, plage d'ID 4000 - 4012

**Cartitem**

Les objets Cartitem sont incrémentés à partir de 6000.

**Orderline**

Les objets Orderline sont incrémentés à partir de 8000.

## Requêtes

Une fois le serveur tomcat lancé en localhost, voici
la liste des requêtes exécutables pour les URI suivantes
(base de l'URI: http://localhost:8080/minishop/api):

### /customer:
    get:
      tags:
      - "customer"
      summary: Gets all customers
      description:  return a list of all customers
      produces:
      - "application/json"
      responses:
        200:
          description: A list of customer
          schema:
            type: array
            items:
              $ref: '#/definitions/Customer'
    post:
      tags:
      - "customer"
      summary: Creates a customer
      description: Adds a new customer to the customers list.
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - in: "formData"
        name: "username"
        type: string
        required: true
      - in: "formData"
        name: "firstname"
        type: string
        required: true
      - in: "formData"
        name: "lastname"
        type: string
        required: true
      - in: "formData"
        name: "email"
        type: string
        required: true
      - in: "formData"
        name: "phone"
        type: string
        required: false
      responses:
        200:
          description: customer succesfully created.
          schema:
            $ref: '#/definitions/Customer'
        400:
          description: customer couldn't have been created.
  ### /customer/{id}:
    get:
      tags:
      - "customer"
      summary: Gets a single customer
      description: returns a single customer
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      responses:
        200:
          description: A customer
          schema:
            $ref: '#/definitions/Customer'
        404:
          description: The customer does not exist.
    put:
      tags:
      - "customer"
      summary: update a customer
      description: Updates a  customer in the customers list.
      consumes:
      - "application/json"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - in: "body"
        name: "body"
        description: "Customer object that needs to be updated"
        required: true
        schema:
          $ref: '#/definitions/Customer'
      responses:
        200:
          description: customer succesfully updated.
          schema:
            $ref: '#/definitions/Customer'
        400:
          description: customer couldn't have been updated.
    delete:
      tags:
      - "customer"
      summary: delete a customer
      description: deletes a  customer from the customers list.
      consumes:
      - "application/json"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      responses:
        204:
          description: customer succesfully deleted.
        400:
          description: customer couldn't have been deleted.
  ### /customer/{id}/cart:
    get:
      tags:
      - "store"
      summary: Gets all shopping carts for a customer
      description:  return a list of shopping carts
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - name: status
        in: query
        required: false
        description: filter for the carts status
        type: string
      responses:
        200:
          description: A list of shopping carts
          schema:
            type: array
            items:
              $ref: '#/definitions/Cart'
    post:
      tags:
      - "store"
      summary: add an item to a new shopping cart
      description: "Use this when inserting a new item to a cart for the first time. This will return a cart with an id that shall be used in all calls when adding or updating or deleting products."
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - in: "formData"
        name: "productid"
        type: integer
        format: int64
        required: true
      - in: "formData"
        name: "quantity"
        type: integer
        format: int64
        required: true
      responses:
        200:
          description: item added. returns the ceated cart object
          schema:
            $ref: '#/definitions/Cart'
        400:
          description: item not added. error.
  ### /customer/{id}/cart/{cartId}:
    get:
      tags:
      - "store"
      summary: view shopping cart content
      description: "view  content of given shopping cart id."
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - name: cartId
        in: path
        required: true
        description: the shopping cart's id
        type: integer
      responses:
        200:
          description: cart
          schema:
            $ref: '#/definitions/Cart'
        404:
          description: Not found.
    post:
      tags:
      - "store"
      summary: add an item to a given shopping cart
      description: "Use this when inserting a new product to given shopping cart id. This will return a CartItem with the new updated cart."
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - name: cartId
        in: path
        required: true
        description: the shopping cart's id
        type: integer
      - in: "formData"
        name: "productid"
        type: integer
        format: int64
        required: true
      - in: "formData"
        name: "quantity"
        type: integer
        format: int64
        required: true
      responses:
        200:
          description: item added. returns the ceated cart object
          schema:
            $ref: '#/definitions/Cart'
        400:
          description: item not added. error.
    put:
      tags:
      - "store"
      summary: update  a shopping cart
      description: "Use this when updating a given shopping cart id. This will return a CartItem with the new updated cart."
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - name: cartId
        in: path
        required: true
        description: the shopping cart's id
        type: integer
      - in: "formData"
        name: "productid"
        type: integer
        format: int64
        required: true
      - in: "formData"
        name: "quantity"
        type: integer
        format: int64
        required: true
      responses:
        200:
          description: cart updated.
          schema:
            $ref: '#/definitions/Cart'
        400:
          description: item not added.
  ### /customer/{id}/cart/{cartId}/total:
    get:
      tags:
      - "store"
      summary: view total price of given shopping cart
      description: "Compute and display the total price for a given shopping cart."
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - name: cartId
        in: path
        required: true
        description: the shopping cart's id
        type: integer
      responses:
        200:
          description: total price of given cart
          schema:
            type: number
        404:
          description: cart not found.
  ### /customer/{id}/cart/{cartId}/item/{itemId}:
    delete:
      tags:
      - "store"
      summary: delete an item in a given cart
      consumes:
      - "application/json"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the customer's id
        type: integer
      - name: cartId
        in: path
        required: true
        description: the cart's id
        type: integer
      - name: itemId
        in: path
        required: true
        description: the item's id
        type: integer
      responses:
        204:
          description: operation succesful.
        400:
          description: error.
  ### /product:
    get:
      tags:
      - "product"
      summary: Gets all products
      description:  return a list of all products
      produces:
      - "application/json"
      responses:
        200:
          description: A list of product
          schema:
            type: array
            items:
              $ref: '#/definitions/Product'
    post:
      tags:
      - "product"
      summary: Creates a product
      description: Adds a new product to the products list.
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - in: "formData"
        name: "productname"
        type: string
        required: true
      - in: "formData"
        name: "description"
        type: string
        required: true
      - in: "formData"
        name: "category"
        type: string
        required: true
      - in: "formData"
        name: "price"
        type: number
        required: true
      responses:
        200:
          description: product succesfully created.
          schema:
            $ref: '#/definitions/Product'
        400:
          description: product couldn't have been created.
  ### /product/{id}:
    get:
      tags:
      - "product"
      summary: Gets a single product
      description: returns a single product
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the product's id
        type: integer
      responses:
        200:
          description: A product
          schema:
            $ref: '#/definitions/Product'
        404:
          description: The product does not exist.
    put:
      tags:
      - "product"
      summary: update a product
      description: Updates a  product in the products list.
      consumes:
      - "application/json"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the product's id
        type: integer
      - in: "body"
        name: "body"
        description: "Product object that needs to be updated"
        required: true
        schema:
          $ref: '#/definitions/Product'
      responses:
        200:
          description: product succesfully updated.
          schema:
            $ref: '#/definitions/Product'
        400:
          description: product couldn't have been updated.
    delete:
      tags:
      - "product"
      summary: delete a product
      description: deletes a  product from the products list.
      consumes:
      - "application/json"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: the product's id
        type: integer
      responses:
        204:
          description: product succesfully deleted.
        400:
          description: product not deleted.
  ### /order:
    get:
      tags:
      - "order"
      summary: List all orders
      description:  return a list of all orders
      produces:
      - "application/json"
      responses:
        200:
          description: A list of orders
          schema:
            type: array
            items:
              $ref: '#/definitions/Order'
    post:
      tags:
      - "order"
      summary: Submit an order (checkout)
      description: creates a new order.
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - name: "cartid"
        in: "formData"
        description: "id of shopping cart that will be ordered"
        required: true
        type: 'integer'
        format: 'int64'
      responses:
        200:
          description: order succesfully created.
          schema:
            $ref: '#/definitions/Order'
        400:
          description: order was not created.
  ### /order/{id}:
    get:
      tags:
      - "order"
      summary: Get a given order
      description:  The requested order
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: "the order's id"
        type: integer
      responses:
        200:
          description: The requested order
          schema:
            $ref: '#/definitions/Order'
        404:
          description: Not found
    put:
      tags:
      - "order"
      summary: Update the status of a given order
      description: Update the status of a given order. The new status is passed.
      consumes:
      - "application/x-www-form-urlencoded"
      produces:
      - "application/json"
      parameters:
      - name: id
        in: path
        required: true
        description: "the order's id"
        type: integer
      - name: "newstatus"
        in: "formData"
        description: "new status for the order"
        required: true
        type: 'string'
      responses:
        200:
          description: order status succesfully updated.
          schema:
            $ref: '#/definitions/Order'
        400:
          description: Status not updated