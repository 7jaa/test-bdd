package ch.hearc.ig.odi.minishop.restresources;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.exception.CustomerException;
import ch.hearc.ig.odi.minishop.exception.StoreException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.math.BigDecimal;
import org.junit.Test;

public class StoreResourceTest {

  @Test
  public void getCartAmount() throws CustomerException, StoreException {
    MockPersistence mp = new MockPersistence();
    BigDecimal expectedAmount = new BigDecimal("188.5");

    BigDecimal actualAmount = new BigDecimal("0");
    assertNotEquals(expectedAmount, actualAmount);

    Long customerId = mp.getCustomers().get(0).getCustomerid();
    Long cartId = mp.getCustomer(customerId).getCarts().get(0).getCartid();
    actualAmount = mp.getCartAmount(customerId, cartId);
    assertEquals(expectedAmount, actualAmount);
  }
}