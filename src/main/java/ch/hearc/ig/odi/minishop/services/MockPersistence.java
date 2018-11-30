/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.services;


import static ch.hearc.ig.odi.minishop.business.Cart.CartStatus.CHECK_OUT;
import static java.math.BigDecimal.valueOf;

import ch.hearc.ig.odi.minishop.business.Cart;
import ch.hearc.ig.odi.minishop.business.CartItem;
import ch.hearc.ig.odi.minishop.business.Customer;
import ch.hearc.ig.odi.minishop.business.Order;
import ch.hearc.ig.odi.minishop.business.Order.OrderStatus;
import ch.hearc.ig.odi.minishop.business.OrderLine;
import ch.hearc.ig.odi.minishop.business.Product;
import ch.hearc.ig.odi.minishop.exception.CustomerException;
import ch.hearc.ig.odi.minishop.exception.OrderException;
import ch.hearc.ig.odi.minishop.exception.ProductException;
import ch.hearc.ig.odi.minishop.exception.StoreException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MockPersistence {

  private final static Logger logger = LogManager.getLogger(MockPersistence.class);

  private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
  private Map<Long, Customer> customers;
  private Map<Long, Product> products;
  private Map<Long, Order> orders;
  private Map<Long, Cart> carts;
  private long idCounterCustomer = 3000;
  private long idCounterProduct = 4000;
  private long idCounterCart = 5000;
  private long idCounterCartItem = 6000;
  private long idCounterOrder = 7000;
  private long idCounterOrderLine = 8000;

  public MockPersistence() {
    populateMockPersistence();
  }


  /**
   * Return all existing customers
   *
   * @return all customers
   */
  public ArrayList<Customer> getCustomers() {
    ArrayList<Customer> customers = new ArrayList<>(this.customers.values());
    return customers;
  }

  /**
   * Return a customer
   *
   * @param customerId specifies which customer to return
   * @return the customer
   * @throws CustomerException if customerId does not match any existing customer
   */
  public Customer getCustomer(Long customerId) throws CustomerException {
    Customer customer = this.customers.get(customerId);
    if (customer != null) {
      return customer;
    } else {
      logger.warn("Failed to get customer n." + customerId
          + " because it does not exist [method: getCustomer(customerId)]");
      throw new CustomerException("the customer does not exist.");
    }
  }

  /**
   * Create a new object Customer and add it to the map of customer
   *
   * @param username the username of the customer
   * @param firstName the first name of the customer
   * @param lastName the last name of the customer
   * @param email the email of the customer
   * @param phone (optional) the phone number of the customer
   * @return the freshly created customer
   * @throws CustomerException if a required param is null
   */
  public Customer createCustomer(String username, String firstName, String lastName, String email,
      String phone) throws CustomerException {
    if (username != null && firstName != null && lastName != null && email != null) {
      Customer newCustomer = new Customer(idCounterCustomer++, username, firstName, lastName, email,
          phone);
      this.customers.put(newCustomer.getCustomerid(), newCustomer);
      logger.info(
          "Customer n." + newCustomer.getCustomerid() + " (" + newCustomer.getFirstname() + " "
              + newCustomer.getLastname()
              + ") has been successfully created [method: createCustomer(username, firstName, lastName, email, phone)]");
      return this.customers.get(newCustomer.getCustomerid());
    } else {
      logger.warn("Failed to create customer n." + (idCounterCustomer + 1)
          + " because of a null attribute [method: createCustomer(username, firstName, lastName, email, phone)]");
      throw new CustomerException("customer couldn't have been created.");
    }
  }

  /**
   * Delete a customer
   *
   * @param customerId specifies which customer to delete
   * @throws CustomerException if customerId does not match any existing customer
   */
  public void deleteCustomer(Long customerId) throws CustomerException {
    Customer customerScrapped = this.customers.remove(customerId);
    if (customerScrapped != null) {
      logger.info(
          "Customer n." + customerId + " (" + customerScrapped.getFirstname() + " "
              + customerScrapped
              .getLastname()
              + ") has been successfully deleted [method: deleteCustomer(customerId)]");
    } else {
      logger.warn("Failed to delete customer n." + customerId
          + " because it does not exist [method: deleteCustomer(customerId)]");
      throw new CustomerException("customer couldn't have been deleted.");
    }
  }

  /**
   * Update a customer
   *
   * @param customerId specifies which customer to update
   * @param customerNewData the new data
   * @return the customer updated
   * @throws CustomerException if the id does not match any existing customer
   */
  public Customer updateCustomer(Long customerId, Customer customerNewData)
      throws CustomerException {
    Customer customerToUpdate = this.customers.get(customerId);
    if (customerToUpdate != null) {
      customers.put(customerId, customerNewData);
      if (customerNewData.getCustomerid().equals(customerId)) {
        deleteCustomer(customerId);
      }
      logger.info(
          "Customer n." + customerToUpdate.getCustomerid() + " (" + customerToUpdate.getFirstname()
              + " " + customerToUpdate.getLastname()
              + ") has been successfully updated [method: updateCustomer(customerId, customerNewData)]");
      return customerNewData;
    } else {
      logger.warn("Failed to update customer n." + customerId
          + " because it does not exist [method: updateCustomer(customerId, customerNewData)]");
      throw new CustomerException("customer couldn't have been updated.");
    }
  }

  /**
   * Return all existing products
   *
   * @return all products
   */
  public List<Product> getProducts() {
    ArrayList<Product> products = new ArrayList<>(this.products.values());
    return products;
  }

  /**
   * Return a product
   *
   * @param productId specifies which product to return
   * @return the product
   * @throws ProductException if the id does not match any existing product
   */
  public Product getProduct(Long productId) throws ProductException {
    Product product = this.products.get(productId);
    if (product != null) {
      return product;
    } else {
      logger.warn("Failed to get product n." + productId
          + " because it does not exist [method: getProduct(productId)]");
      throw new ProductException("The product does not exist.");
    }
  }

  /**
   * Create a new product
   *
   * @param productName the name of the product
   * @param description the description of the product
   * @param category the category of the product
   * @param price the price of the product
   * @return the product created
   * @throws ProductException if a required parameter is null
   */
  public Product createProduct(String productName, String description, String category,
      String price) throws ProductException {
    if (productName != null && description != null && category != null && price != null) {
      Product newProduct = new Product(idCounterProduct++, productName, description, category,
          new BigDecimal(price));
      this.products.put(newProduct.getProductid(), newProduct);
      logger.info("Product n." + newProduct.getProductid() + " (" + newProduct.getProductname()
          + ") has been successfully created [method: createProduct(productName, description, category, price)]");
      return this.products.get(newProduct.getProductid());
    } else {
      logger.warn(
          "Failed to create product n." + (idCounterProduct + 1)
              + " because of a null attribute [method: createProduct(productName, description, category, price)]");
      throw new ProductException("product couldn't have been created.");
    }
  }

  /**
   * Delete a product
   *
   * @param productId the id of the product to delete
   * @throws ProductException if the productId does not match any existing product
   */
  public void deleteProduct(Long productId) throws ProductException {
    Product productScrapped = this.products.remove(productId);
    if (productScrapped != null) {
      logger.info("Product n." + productId + " (" + productScrapped.getProductname()
          + ") has been successfully deleted [method: deleteProduct(productId)]");
    } else {
      logger.warn("Failed to delete product n." + productId
          + " because it does not exist [method: deleteProduct(productId)]");
      throw new ProductException("product not deleted.");
    }
  }

  /**
   * Update a product
   *
   * @param productId specifies which product to update
   * @param productNewData new data
   * @return the updated product
   * @throws ProductException if the id does not match any existing product
   */
  public Product updateProduct(Long productId, Product productNewData) throws ProductException {
    Product productToUpdate = this.products.get(productId);
    if (productToUpdate != null) {
      products.put(productId, productNewData);
      if (productNewData.getProductid().equals(productId)) {
        deleteProduct(productId);
      }
      logger.info("Product n." + productNewData.getProductid()
          + " has been successfully updated [method: updateProduct(productId, productNewData)]");
      return productNewData;
    } else {
      logger.warn("Failed to update product n." + productId
          + " because it does not exist [method: updateProduct(productId, productNewData)]");
      throw new ProductException("product couldn't have been updated.");
    }
  }

  /**
   * Return all orders
   *
   * @return all orders
   */
  public List<Order> getOrders() {
    ArrayList<Order> orders = new ArrayList<>(this.orders.values());
    return orders;
  }

  /**
   * Return an order
   *
   * @param orderId : specifies which order to return
   * @return the order
   * @throws OrderException if the id does not match any existing order
   */
  public Order getOrder(Long orderId) throws OrderException {
    Order order = this.orders.get(orderId);
    if (order != null) {
      return order;
    } else {
      logger.warn("Failed to get order n." + orderId
          + " because it does not exist [method: getOrder(orderId)]");
      throw new OrderException("Not found");
    }
  }

  /**
   * Update the status of an order
   *
   * @param orderId the id of the order
   * @param status the new order status
   * @return the order with its updated status
   * @throws OrderException if the id does not match any existing order
   */
  public Order updateOrder(Long orderId, String status) throws OrderException {
    Order orderToUpdate = this.getOrder(orderId);
    if (orderToUpdate != null) {
      EnumSet<OrderStatus> expectedOrderStatus = EnumSet
          .of(OrderStatus.OPEN, OrderStatus.PAID, OrderStatus.CANCELED, OrderStatus.CONFIRMED,
              OrderStatus.SHIPPED);
      try {
        expectedOrderStatus.contains(OrderStatus.valueOf(status.toUpperCase()));
        orderToUpdate.setOrderstatus(status.toLowerCase());
        orders.put(orderId, orderToUpdate);
        logger.info("Order n." + orderToUpdate.getOrderid() + " (" + orderToUpdate.getOrderstatus()
            + ") has been successfully updated [method: updateOrder(orderId, status)]");
        return orderToUpdate;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Status can only be 'open', 'paid', 'canceled', 'confirmed' or 'shipped'");
      }
    } else {
      logger.warn("Failed to update order n." + orderId
          + " because it does not exist [method: updateOrder(orderId, status)]");
      throw new OrderException("Status not updated");
    }
  }

  /**
   * Create a new order and check out a cart
   *
   * @param cartId specifies which cart to checkout
   * @return the new order
   * @throws StoreException if the id does not match any existing cart or if the cart is empty
   */
  public Order createOrder(Long cartId) throws StoreException {
    Long customerId = getCustomerId(cartId);
    Cart cartToCheckOut = getCart(customerId, cartId);
    if (cartToCheckOut != null) {
      Order newOrder = checkoutCartIntoNewOrder(cartToCheckOut);
      cartToCheckOut.setCartstatus("check_out");

      this.orders.put(newOrder.getOrderid(), newOrder);
      logger.info("Order n." + newOrder.getOrderid()
          + " (" + newOrder.getOrderstatus()
          + ") has been successfully created [method: createOrder(cartId)]");
      return newOrder;
    } else {
      logger.warn("Failed to create a new order because cart n." + cartId
          + " does not exist [method: createOrder(cartId)]");
      throw new StoreException("order was not created.");
    }
  }

  /**
   * Checks out a cart into a new order
   *
   * @param cartToCheckOut the cart to check out
   * @return the order matching the checked out cart
   * @throws StoreException if the cart does not exist
   */
  private Order checkoutCartIntoNewOrder(Cart cartToCheckOut) throws StoreException {
    Order order = new Order();
    Date newOrderDate = new Date();

    order.setOrderid(idCounterOrder++);
    order.setOrderdate(newOrderDate);

    Product productToCheckOut;
    long quantityToCheckOut;
    OrderLine orderLineToAdd;

    try {
      for (int i = 0; i < cartToCheckOut.getContent().size(); i++) {
        productToCheckOut = cartToCheckOut.getContent().get(i).getProduct();
        quantityToCheckOut = cartToCheckOut.getContent().get(i).getQuantity();
        orderLineToAdd = new OrderLine(idCounterOrderLine++, productToCheckOut, quantityToCheckOut);
        order.addOrderLine(orderLineToAdd);
      }
      return order;
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
      logger.warn("Failed to create a new order because cart n." + cartToCheckOut.getCartid()
          + " does not exist [method: checkoutCartIntoNewOrder(cartToCheckOut)]");
      throw new StoreException("order was not created.");
    }
  }

  /**
   * Returns the id of the customer that owns the given cart
   *
   * @param cartId the id of the cart owned by the customer
   * @return the owner (customer) of the cart
   * @throws StoreException if the cart does not exist
   */
  private Long getCustomerId(Long cartId) throws StoreException {
    Long customerId = null;

    try {
      for (int i = 0; i < this.getCustomers().size(); i++) {
        Customer actualCustomer = this.getCustomers().get(i);
        List<Cart> carts = actualCustomer.getCarts();

        for (Cart actualCart : carts) {
          if (actualCart.getCartid().equals(cartId)) {
            customerId = actualCustomer.getCustomerid();
          }
        }
      }
    } catch (NullPointerException e) {
      logger.warn("Failed to create a new order because cart n." + cartId
          + " does not exist [method: getCustomerId(cartId)]");
      throw new StoreException("order was not created.");
    }

    return customerId;
  }

  /**
   * Returns a specific cart
   *
   * @param customerId specifies which customer owns the cart
   * @param cartId specifies which cart to return
   * @return the cart
   * @throws StoreException if the cart does not exist
   */
  public Cart getCart(Long customerId, Long cartId) throws StoreException {
    Customer customer = customers.get(customerId);
    int indexOfCart = customer.getCarts().indexOf(carts.get(cartId));
    Cart cart = customer.getCarts().get(indexOfCart);
    if (cart != null) {
      return cart;
    } else {
      logger.warn("Failed to get cart n." + cartId + " of customer n." + customerId
          + " (" + customer.getFirstname() + " " + customer.getLastname()
          + ") because the cart does not exist [method: getCart(customerId, cartId)]");
      throw new StoreException("Not found.");
    }
  }

  /**
   * Delete an item from a cart
   *
   * @param customerId specifies which customer owns the cart
   * @param cartId specifies which cart contains the item
   * @param cartItemId specifies which item to remove
   * @throws StoreException if the cartItemId does not match any existing cartitem and/or the cartId
   * does not match any cart
   */
  public void removeItemFromCart(Long customerId, Long cartId, Long cartItemId)
      throws StoreException {
    Cart cart = getCart(customerId, cartId);
    if (cart != null && cartItemId != null) {
      cart.removeCartItemById(cartItemId);
      logger.info("Item n." + cartItemId + " has been successfully removed from cart n." + cartId
          + " [method: removeItemFromCart(customerId, cartId, cartItemId)]");
    } else {
      logger.warn("Failed to remove item n." + cartItemId + " from cart n." + cartId
          + " because either the item or the cart (or both) does not exist [method: removeItemFromCart(customerId, cartId, cartItemId)]");
      throw new StoreException("error.");
    }
  }

  /**
   * Add an item to a cart
   *
   * @param customerId specifies to which customer the cart belongs
   * @param cartId speficies to which cart the item will be added
   * @param productId specifies which item to add
   * @return the cart with the new item added
   * @throws StoreException if the cart does not exist
   * @throws ProductException if the product does not exist
   */
  public Cart addItemToAGivenCart(Long customerId, Long cartId, Long productId, Long quantity)
      throws StoreException, ProductException {
    Cart cart = getCart(customerId, cartId);
    if (cart != null && productId != null && quantity != null) {
      Product product = getProduct(productId);
      CartItem cartitem = new CartItem(idCounterCartItem++, product, quantity);
      cart.addCartItem(cartitem);
      logger.info("Item n." + idCounterCart + " (containing product n." + productId
          + ") has been successfully added to cart n." + cartId
          + " [method: addItemToAGivenCart(customerId, cartId, productId, quantity)]");
      return cart;
    } else {
      logger.warn(
          "Failed to add the product to the cart because either cart n." + cartId + " or product n."
              + productId
              + "  does not exist, or the quantity entered is incorrect [method: addItemToAGivenCart(customerId, cartId, productId, quantity)]");
      throw new StoreException("item not added. error.");
    }
  }

  /**
   * Update the quantity of an item in a cart
   *
   * @param customerId specifies which customer owns the cart
   * @param cartId specifies in which cart is the item
   * @param productId specifies which product is to update
   * @param quantity the new quantity
   * @return the cart with the updated data
   * @throws StoreException if the cart does not contain the product
   * @throws ProductException if the productId does not match any existing product
   */
  public Cart updateCart(Long customerId, Long cartId, Long productId, Long quantity)
      throws StoreException, ProductException {
    Cart cart = getCart(customerId, cartId);
    Product product = getProduct(productId);
    int position = -1;
    for (int i = 0; i < cart.getContent().size(); i++) {
      if (cart.getContent().get(i).getProduct().equals(product)) {
        position = i;
      }
    }
    if (position != -1) {
      cart.getContent().get(position).setQuantity(quantity);
      logger.info("Cart n." + cart.getCartid()
          + " has been successfully updated [method: updateCart(customerId, cartId, productId, quantity)]");
      return cart;
    } else {
      logger.warn("Failed to update quantity of product n." + productId + " of cart n." + cartId
          + " [method: updateCart(customerId, cartId, productId, quantity)]");
      throw new StoreException("item not added.");
    }
  }

  /**
   * Return the total price of a cart
   *
   * @param customerId specifies which customer owns the cart
   * @param cartId specifies which cart to work with
   * @return the total amount
   * @throws StoreException if the IDs do not match any existing related objects
   */
  public BigDecimal getCartAmount(Long customerId, Long cartId)
      throws StoreException {
    Cart cart = getCart(customerId, cartId);
    if (cart != null) {
      BigDecimal totalCartAmount = new BigDecimal("0");
      BigDecimal quantityOfCurrentCartItem;
      BigDecimal amountOfCurrentCartItem;
      for (int i = 0; i < cart.getContent().size(); i++) {
        quantityOfCurrentCartItem = valueOf(cart.getContent().get(i).getQuantity());
        amountOfCurrentCartItem = cart.getContent().get(i).getProduct().getPrice()
            .multiply(quantityOfCurrentCartItem);
        totalCartAmount = totalCartAmount.add(amountOfCurrentCartItem);
      }
      return totalCartAmount;
    } else {
      logger.warn("Failed to get amount of cart n." + cartId
          + " because it does not exist [method: getCartAmount(customerId, cartId)]");
      throw new StoreException("cart not found.");
    }
  }

  /**
   * Add an item to a new cart
   *
   * @param customerId specifies which customer will own the new cart
   * @param productId specifies which product to add
   * @return the new cart
   * @throws ProductException if the productId does not match any existing product
   * @throws CustomerException if the customerId does not match any existing customer
   */
  public Cart addProductToNewCart(Long customerId, Long productId, Long quantity)
      throws ProductException, CustomerException {
    Product product = products.get(productId);
    if (product != null && quantity != null) {
      CartItem newCartItem = new CartItem(idCounterCartItem++, product, quantity);
      Customer customer = this.getCustomer(customerId);
      Cart newCart = new Cart(idCounterCart++);
      newCart.addCartItem(newCartItem);
      customer.addCart(newCart);
      carts.put(newCart.getCartid(), newCart);
      logger.info("Product n." + productId + " (" + newCartItem.getProduct().getProductname()
          + ") has been successfully added to cart n." + newCart.getCartid()
          + " [method: addProductToNewCart(customerId, productId, quantity)]");
      return newCart;
    } else {
      logger.warn("Failed to add product n." + productId + " to cart n." + (idCounterCart + 1)
          + " [method: addProductToNewCart(customerId, productId, quantity)]");
      throw new ProductException("item not added. error.");
    }
  }

  /**
   * Populates the class for test purposes
   */
  private void populateMockPersistence() {
    customers = new HashMap<>();
    products = new HashMap<>();
    orders = new HashMap<>();
    carts = new HashMap<>();

    //Création et mappage Customer

    Customer c0 = new Customer(idCounterCustomer++, "agueniat", "Adeline", "Gueniat",
        "adeline.gueniat@he-arc.ch", "032 321 12 23");
    Customer c1 = new Customer(idCounterCustomer++, "jdubois", "Joyce", "Dubois",
        "joyce.dubois@he-arc.ch", "032 456 45 56");
    Customer c2 = new Customer(idCounterCustomer++, "urosselet", "Ulysse", "Rosselet",
        "ulysse.rosselet@he-arc.ch", "032 789 78 89");
    Customer c3 = new Customer(idCounterCustomer++, "mschaffter", "Myriam", "Schaffter",
        "myriam.schaffter@he-arc.ch", "032 852 25 58");
    Customer c4 = new Customer(idCounterCustomer++, "nobody", "john", "doe", "john@doe.com",
        "000 00 000 00"); // Pour delete
    Customer c5 = new Customer(idCounterCustomer++, "nobodette", "jane", "doe", "jane@doe.com",
        "000 00 000 00"); // Pour update

    customers.put(c0.getCustomerid(), c0);
    customers.put(c1.getCustomerid(), c1);
    customers.put(c2.getCustomerid(), c2);
    customers.put(c3.getCustomerid(), c3);
    customers.put(c4.getCustomerid(), c4);
    customers.put(c5.getCustomerid(), c5);

// Création et mappage Product

    Product p0 = new Product(idCounterProduct++, "Lifeprint",
        "Lifeprint est une imprimante photo bluetooth à réalité augmentée qui s’accompagne d’une app gratuite et qui vous permet d’imprimer instantanément vos photos et vidéos préférées aussi bien depuis la bibliothèque de votre smartphone que depuis vos réseaux sociaux (Snapchat, Twitter, Facebook, Instagram, …)",
        "High-Tech", valueOf(149.50));
    Product p1 = new Product(idCounterProduct++, "GARDENA R40 Li",
        "La tondeuse robot GARDENA tond la pelouse toute seule et va se recharger automatiquement à sa station de charge. Vous pouvez profiter d'une pelouse toujours impeccable et également de votre temps libre.",
        "Maison et jardin", valueOf(999.95));
    Product p2 = new Product(idCounterProduct++, "PANASONIC SC-SB10",
        "La barre de son SB10 est dotée de deux haut-parleurs (2x20W) + 1 subwoofer intégré (10W). Combinées, ces unités empliront votre salon d'un son net, riche et arrondi, beaucoup plus dynamique et immersif que ce que vous offrent habituellement les haut-parleurs des téléviseurs.",
        "High-Tech", valueOf(234.90));
    Product p3 = new Product(idCounterProduct++, "POC Tectal 2018",
        "Plus couvrant que les casques VTT classiques, le casque POC Tectal est conçu pour allier protection optimale et ventilation efficace. Dessiné pour les parcours exigeants et les courses d'endurance, il est doté d'un revêtement en EPS renforcé, offrant au pilote une forte protection.",
        "Sports", valueOf(212.00));
    Product p4 = new Product(idCounterProduct++,
        "MOULINEX Robot pâtissier Masterchef Gourmet Premium",
        "Le robot pâtissier Masterchef Gourmet allie l'utile à l'agréable avec des fonctionnalités essentielles et un design élégant. Doté d'un choix de 8 vitesses, ce robot va vite devenir l'outil indispensable de votre cuisine, celui dont on se sert quotidiennement et que l'on ne souhaite jamais ranger.",
        "Électroménager", valueOf(328.95));
    Product p5 = new Product(idCounterProduct++, "VTT SANTA CRUZ HIGHTOWER Carbone CC 27,5+ XX1",
        "Le VTT SANTA CRUZ Hightower a été conçu pour procurer une grande polyvalence qui ravira les amateurs de All Mountain et d'Enduro comme les accros de DH. Dans cette version, il est monté avec des roues au standard 27,5\"+ permettant de monter des pneus de section plus importante - jusqu'à 3 pouces - pour un grip exceptionnel en toutes circonstances.",
        "Sports", valueOf(10697.50));
    Product p6 = new Product(idCounterProduct++, "JOOKI Smart music player",
        "L'enceinte Jooki est fournie avec un lot de 5 figurines colorées, un câble de chargement USB et un manuel d'utilisateur.",
        "High-Tech", valueOf(249.00));
    Product p7 = new Product(idCounterProduct++, "CIGAR CITY Floride 12x35cl",
        "Cigar City est une des brasseries les plus célèbres des Etats Unis et se voit régulièrement dans le groupe de tête lors des rankings.",
        "Alimentation", valueOf(39.00));
    Product p8 = new Product(idCounterProduct++, "TOTTORI Blended Japanese Whisky",
        "Tottori est une gamme de whisky créée par la distillerie Matsui Shuzo située dans la préfecture de Tottori, au nord-est d'Hiroshima, sur la rive de la mer du Japon. Créé pour célébrer la beauté préservée de cette province rurale, cette bouteille est un assemblage de plusieurs whisky ayant nécessité l'eau pure des cours d'eau naturels de Tottori et reflète l'expertise de Matsui depuis plus de 100 ans dans l'art du vieillissement et de l'assemblage des spiritueux.",
        "Alimentation", valueOf(55.00));
    Product p9 = new Product(idCounterProduct++, "CUISINART Multi Griddler",
        "Multi Griddler, c’est le petit malin de la collection Griddler. Ses spécialités ? Grillades bien saisies, brochettes parfumées, gaufres croustillantes, sandwichs triangles. Il puise son inspiration des saveurs du monde entier, pour régaler jeunes et petites familles de ses créations inspirées de la street food.",
        "Eléctroménager", valueOf(114.95));
    Product p10 = new Product(idCounterProduct++, "Maillot ONEAL ELEMENT RACEWEAR ",
        "La performance devient votre seul souci avec ce maillot Element Racewear Femme Manches Longues, développé pour vous par la marque O'NEAL. Celui-ci est idéal pour les sportives qui se penchent sur leur guidon pour plus d'aérodynamisme. La coupe, plus longue à l'arrière qu'à l'avant, fixe le maillot dans le short/pantalon sans friper. Des protections rembourrées ont même été rajoutées aux coudes, pour limiter l'usure ! Avec son col en V très flexible et son jersey, c'est un vêtement aéré et très agréable d'utilisation. Celui-ci vous laissera sur la peau une sensation de fraîcheur, même dans les efforts les plus intenses.",
        "Sports", valueOf(42.50));
    Product p11 = new Product(idCounterProduct++, "Produit à supprimer",
        "Ce produit n'existe que dans le but de pouvoir le supprimer dans Postman lors du run de la collection",
        "Autre", valueOf(10.00));
    Product p12 = new Product(idCounterProduct++, "Produit à updater",
        "Ce produit n'existe que dans le but de pouvoir le mettre à jour dans Postman lors du run de la collection",
        "Autre", valueOf(10.00));

    products.put(p0.getProductid(), p0);
    products.put(p1.getProductid(), p1);
    products.put(p2.getProductid(), p2);
    products.put(p3.getProductid(), p3);
    products.put(p4.getProductid(), p4);
    products.put(p5.getProductid(), p5);
    products.put(p6.getProductid(), p6);
    products.put(p7.getProductid(), p7);
    products.put(p8.getProductid(), p8);
    products.put(p9.getProductid(), p9);
    products.put(p10.getProductid(), p10);
    products.put(p11.getProductid(), p11);
    products.put(p12.getProductid(), p12);

    //Création des Cart et ajout d'item

    Cart ca0 = new Cart(idCounterCart++); // check_out
    Cart ca1 = new Cart(idCounterCart++); // open
    Cart ca2 = new Cart(idCounterCart++); // open
    Cart ca3 = new Cart(idCounterCart++); // check_out
    Cart ca4 = new Cart(idCounterCart++); // open

    ca0.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p0.getProductid())), (long) 1));
    ca0.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p7.getProductid())), (long) 1));
    ca0.setCartstatus(CHECK_OUT.toString());

    ca1.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p1.getProductid())), (long) 2));
    ca1.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p8.getProductid())), (long) 1));

    ca2.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p8.getProductid())), (long) 2));
    ca2.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p4.getProductid())), (long) 1));
    ca2.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p2.getProductid())), (long) 1));
    ca2.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p0.getProductid())), (long) 1));

    ca3.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p6.getProductid())), (long) 1));
    ca3.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p7.getProductid())), (long) 2));
    ca3.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p9.getProductid())), (long) 1));
    ca3.setCartstatus(CHECK_OUT.toString());

    ca4.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p5.getProductid())), (long) 1));
    ca4.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p3.getProductid())), (long) 1));
    ca4.addCartItem(
        new CartItem(idCounterCartItem++, (this.products.get(p10.getProductid())), (long) 3));

    carts.put(ca0.getCartid(), ca0);
    carts.put(ca1.getCartid(), ca1);
    carts.put(ca2.getCartid(), ca2);
    carts.put(ca3.getCartid(), ca3);
    carts.put(ca4.getCartid(), ca4);

    //Attribution des carts aux customers
    c0.addCart(ca0);
    c0.addCart(ca4);
    c1.addCart(ca1);
    c2.addCart(ca2);
    c3.addCart(ca3);

    // Création et mappage Order

    Order o0 = null;
    try {
      o0 = new Order(idCounterOrder++, dateFormat.parse("12.02.2018"));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    if (o0 != null) {
      o0.addOrderLine(
          new OrderLine(idCounterOrderLine++, (this.products.get(p0.getProductid())), (long) 1));

      o0.addOrderLine(
          new OrderLine(idCounterOrderLine++, (this.products.get(p7.getProductid())), (long) 1));
      o0.setOrderstatus(OrderStatus.PAID.toString());
    } else {
      throw new NullPointerException();
    }

    Order o1 = null;
    try {
      o1 = new Order(idCounterOrder++, dateFormat.parse("01.05.2018"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if (o1 != null) {
      o1.addOrderLine(
          new OrderLine(idCounterOrderLine++, (this.products.get(p6.getProductid())), (long) 1));

      o1.addOrderLine(
          new OrderLine(idCounterOrderLine++, (this.products.get(p7.getProductid())), (long) 2));
      o1.addOrderLine(
          new OrderLine(idCounterOrderLine++, (this.products.get(p9.getProductid())), (long) 1));
      o1.setOrderstatus(OrderStatus.OPEN.toString());
    } else {
      throw new NullPointerException();
    }

    orders.put(o0.getOrderid(), o0);
    orders.put(o1.getOrderid(), o1);

    // Attribution des orders aux customers
    c0.addOrder(orders.get(o0.getOrderid()));
    c3.addOrder(orders.get(o1.getOrderid()));
  }

  /**
   * Returns the customer's open carts
   *
   * @param customerId the id of the customer
   * @return the carts
   * @throws CustomerException if customerId does not match any existing customer
   */
  public List<Cart> getOpenCartsForCustomer(Long customerId) throws CustomerException {
    Customer customer = customers.get(customerId);
    if (customer != null) {
      List<Cart> openCarts = customer.getCarts().stream()
          .filter(cart -> cart.getCartstatus().equals("open")).collect(
              Collectors.toList());
      return openCarts;
    } else {
      logger.warn("Failed to get customer n." + customerId
          + " because it does not exist [method: getOpenCartsForCustomer(customerId)]");
      throw new CustomerException("The customer does not exist.");
    }
  }
}





