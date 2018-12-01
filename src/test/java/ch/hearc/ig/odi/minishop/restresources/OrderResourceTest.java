package ch.hearc.ig.odi.minishop.restresources;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.business.Order;
import ch.hearc.ig.odi.minishop.business.OrderLine;
import ch.hearc.ig.odi.minishop.business.Product;
import ch.hearc.ig.odi.minishop.exception.OrderException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import org.junit.Test;

public class OrderResourceTest {

  @Test
  public void getOrder() throws OrderException {
    MockPersistence mp = new MockPersistence();
    Order expectedOrder = mp.getOrders().get(0);

    Order actualOrder = new Order();
    assertNotEquals(expectedOrder, actualOrder);

    actualOrder = mp.getOrder((long) 7000);
    assertEquals(expectedOrder, actualOrder);
  }
}