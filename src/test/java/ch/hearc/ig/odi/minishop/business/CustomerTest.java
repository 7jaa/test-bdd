package ch.hearc.ig.odi.minishop.business;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

  private MockPersistence mp;
  private Customer customer;

  @Before
  public void setUp() {
    mp = new MockPersistence();
    customer = mp.getCustomers().get(0);

  }

  @After
  public void tearDown() {
    customer = null;
  }

  /**
   * Tests if carts are correctly added to a customer1
   */
  @Test
  public void addCart() {
    Cart cartToAdd = mp.getCustomers().get(1).getCarts().get(0);
    List<Cart> expectedCarts = new ArrayList<>();
    expectedCarts.add(customer.getCarts().get(0));
    expectedCarts.add(customer.getCarts().get(1));
    expectedCarts.add(cartToAdd);

    List<Cart> actualCarts = customer.getCarts();
    assertNotEquals(expectedCarts, actualCarts);

    customer.addCart(cartToAdd);

    assertEquals(expectedCarts, actualCarts);
  }

  /**
   * Tests if orders are correctly added to a customer1
   */
  @Test
  public void addOrder() {
    Order orderToAdd = (Order) mp.getCustomers().get(3).getOrders().get(0);
    List<Order> expectedOrders = new ArrayList<>();
    expectedOrders.add((Order) customer.getOrders().get(0));
    expectedOrders.add(orderToAdd);

    List actualOrders = customer.getOrders();
    assertNotEquals(expectedOrders, actualOrders);

    customer.addOrder(orderToAdd);
    assertEquals(expectedOrders, actualOrders);
  }
}