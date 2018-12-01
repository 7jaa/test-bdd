package ch.hearc.ig.odi.minishop.restresources;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.business.Product;
import ch.hearc.ig.odi.minishop.exception.ProductException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import org.junit.Test;

public class ProductResourceTest {

  @Test
  public void getProduct() throws ProductException {
    MockPersistence mp = new MockPersistence();

    Product expectedProduct = mp.getProducts().get(0);

    Product actualProduct = new Product();
    assertNotEquals(expectedProduct, actualProduct);

    actualProduct = mp.getProduct((long) 4000);
    assertEquals(expectedProduct, actualProduct);
  }
}