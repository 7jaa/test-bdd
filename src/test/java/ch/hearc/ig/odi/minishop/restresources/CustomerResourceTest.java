package ch.hearc.ig.odi.minishop.restresources;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.business.Customer;
import ch.hearc.ig.odi.minishop.exception.CustomerException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import org.junit.Test;

public class CustomerResourceTest {

  @Test
  public void getCustomer() throws CustomerException {

    MockPersistence mp = new MockPersistence();

    Customer expectedCustomer = mp.getCustomers().get(0);

    Customer actualCustomer = new Customer();
    assertNotEquals(expectedCustomer,actualCustomer);

    actualCustomer = mp.getCustomer((long) 3000);
    assertEquals(expectedCustomer,actualCustomer);
  }
}