package ch.hearc.ig.odi.minishop.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

  private MockPersistence mp;
  private Customer customer1;
  private Customer customer2;
  private Order order1;
  private Order order2;

  @Before
  public void setUp() {
    mp = new MockPersistence();
    customer1 = mp.getCustomers().get(0);
    customer2 = mp.getCustomers().get(3);
    order1 = (Order) customer1.getOrders().get(0);
    order2 = (Order) customer2.getOrders().get(0);
  }

  @After
  public void tearDown() {
    mp = null;
    customer1 = null;
    customer2 = null;
    order1 = null;
    order2 = null;
  }

  /**
   * Tests if an order1 line is correctly added to an order1
   */
  @Test
  public void addOrderLine() {
    OrderLine orderLineToAdd = order2.getContent().get(0);
    List<OrderLine> expectedOrderLines = new ArrayList<>();
    expectedOrderLines.add(order1.getContent().get(0));
    expectedOrderLines.add(order1.getContent().get(1));
    expectedOrderLines.add(orderLineToAdd);

    List<OrderLine> actualOrderLines = order1.getContent();
    assertNotEquals(expectedOrderLines, actualOrderLines);

    order1.addOrderLine(orderLineToAdd);

    assertEquals(expectedOrderLines, actualOrderLines);
  }
}