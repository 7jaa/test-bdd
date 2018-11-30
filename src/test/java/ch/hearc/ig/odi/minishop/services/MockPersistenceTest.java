package ch.hearc.ig.odi.minishop.services;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ch.hearc.ig.odi.minishop.business.Cart;
import ch.hearc.ig.odi.minishop.business.CartItem;
import ch.hearc.ig.odi.minishop.business.Customer;
import ch.hearc.ig.odi.minishop.business.Order;
import ch.hearc.ig.odi.minishop.business.Order.OrderStatus;
import ch.hearc.ig.odi.minishop.business.Product;
import ch.hearc.ig.odi.minishop.exception.CustomerException;
import ch.hearc.ig.odi.minishop.exception.OrderException;
import ch.hearc.ig.odi.minishop.exception.ProductException;
import ch.hearc.ig.odi.minishop.exception.StoreException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MockPersistenceTest {

  private MockPersistence mp;
  private Customer customer;
  private Cart cart;
  private long customerId;
  private long customersCartId;
  private long orderId;
  private Order order;

  @Before
  public void setUp() throws OrderException {
    mp = new MockPersistence();
    customer = mp.getCustomers().get(0);
    cart = customer.getCarts().get(1);
    customerId = customer.getCustomerid();
    customersCartId = cart.getCartid();
    orderId = 7000;
    order = mp.getOrder(orderId);
    order.setOrderstatus(OrderStatus.OPEN.toString());
  }

  @After
  public void tearDown() {
    mp = null;
    customer = null;
    cart = null;
    customerId = 0;
    customersCartId = 0;
    orderId = 0;
    order = null;
  }

  /**
   * Tests if the correct cart amount is returned
   *
   * @throws StoreException if the cart does not exist
   */
  @Test
  public void getCartAmount() throws StoreException {
    BigDecimal expectedCartAmount = new BigDecimal("11037.0");

    BigDecimal actualCartAmount = mp.getCartAmount(customerId, customersCartId);

    assertEquals(expectedCartAmount, actualCartAmount);
  }

  /**
   * Tests if an item is correctly removed from cart
   *
   * @throws StoreException if the cart does not exist
   */
  @Test
  public void removeItemFromCart() throws StoreException {
    long cartItemIdToRemove = cart.getContent().get(1).getCartitemid();
    List<CartItem> expectedCartItems = new ArrayList<>();
    expectedCartItems.add(cart.getContent().get(0));
    expectedCartItems.add(cart.getContent().get(2));

    List<CartItem> actualCartItems = cart.getContent();
    assertNotEquals(expectedCartItems, actualCartItems);

    mp.removeItemFromCart(customerId, customersCartId, cartItemIdToRemove);

    assertEquals(expectedCartItems, actualCartItems);
  }

  /**
   * Tests if an order status is correctly updated
   *
   * @throws OrderException if the order does not exist
   */
  @Test
  public void updateOrder() throws OrderException {
    String expectedOrderStatus = "canceled";

    String actualOrderStatus = order.getOrderstatus();
    assertNotEquals(expectedOrderStatus, actualOrderStatus);

    mp.updateOrder(orderId, "cANCeled");
    actualOrderStatus = order.getOrderstatus();

    assertEquals(expectedOrderStatus, actualOrderStatus);
  }

  /**
   * Tests if an illegal order status is correctly caught
   *
   * @throws OrderException if the order does not exist
   */
  @Test(expected = IllegalArgumentException.class)
  public void updateOrderWithIllegalStatus() throws OrderException {
    mp.updateOrder(orderId, "random");
  }

  /**
   * Tests if an order is correctly created
   *
   * @throws StoreException if the cart does not exist
   */
  @Test
  public void createOrder()
      throws StoreException {
    List<Product> expectedProducts = new ArrayList<>();
    long[] expectedProductsQuantities = new long[3];
    for (int i = 0; i < cart.getContent().size(); i++) {
      expectedProducts.add(cart.getContent().get(i).getProduct());
      expectedProductsQuantities[i] = cart.getContent().get(i).getQuantity();
    }

    Order newOrder = mp.createOrder(customersCartId);
    assertEquals(3, newOrder.getContent().size());

    List<Product> actualProducts = new ArrayList<>();
    long[] actualProductsQuantities = new long[3];
    for (int i = 0; i < newOrder.getContent().size(); i++) {
      actualProducts.add(newOrder.getContent().get(i).getProduct());
      actualProductsQuantities[i] = newOrder.getContent().get(i).getQuantity();
    }

    assertEquals(expectedProducts, actualProducts);
    assertArrayEquals(expectedProductsQuantities, actualProductsQuantities);
    assertEquals("check_out", cart.getCartstatus());
  }

  /**
   * Tests if a customer is correctly updated
   *
   * @throws CustomerException if the customer to update does not exist
   */
  @Test
  public void updateCustomer() throws CustomerException {
    Customer expectedCustomer = mp.getCustomers().get(3);

    Customer actualCustomer = mp.getCustomers().get(0);
    assertNotEquals(expectedCustomer, actualCustomer);

    mp.updateCustomer(customerId, expectedCustomer);
    actualCustomer = mp.getCustomers().get(0);

    assertEquals(expectedCustomer, actualCustomer);
  }

  /**
   * Tests if a product is correctly updated
   *
   * @throws ProductException if the product does not exist
   */
  @Test
  public void updateProduct() throws ProductException {
    Product expectedProduct = mp.getProducts().get(1);

    Product actualProduct = mp.getProducts().get(0);
    assertNotEquals(expectedProduct, actualProduct);

    mp.updateProduct(actualProduct.getProductid(), expectedProduct);
    actualProduct = mp.getProducts().get(0);

    assertEquals(expectedProduct, actualProduct);
  }
}