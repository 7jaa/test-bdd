package ch.hearc.ig.odi.minishop.restresources;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.business.Customer;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import org.junit.Test;

public class CustomerResourceTest {

  @Test
  public void updateCustomer() {
    MockPersistence mp = new MockPersistence();
    CustomerResource cr = new CustomerResource();

    Customer expectedCustomer = mp.getCustomers().get(1);

    Customer actualCustomer = mp.getCustomers().get(0);
    assertNotEquals(expectedCustomer,actualCustomer);

    Long actualCustomerId = actualCustomer.getCustomerid();
    cr.updateCustomer(actualCustomerId,expectedCustomer);
    assertEquals(expectedCustomer,actualCustomer);
  }
}