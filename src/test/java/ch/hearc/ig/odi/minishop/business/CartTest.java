package ch.hearc.ig.odi.minishop.business;

import static org.junit.Assert.*;

import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CartTest {

  private MockPersistence mp;
  private Customer customer;
  private Cart cart;

  @Before
  public void setUp() {
    mp = new MockPersistence();
    customer = mp.getCustomers().get(0);
    cart = customer.getCarts().get(1);
  }

  @After
  public void tearDown() {
    mp = null;
    customer = null;
    cart = null;
  }

  /**
   * Tests if cart items are correctly removed from a cart using their id
   */
  @Test
  public void removeCartItemById() {
    List<CartItem> expectedCartItems = new ArrayList<>();
    expectedCartItems.add(cart.getContent().get(0));
    expectedCartItems.add(cart.getContent().get(2));

    List<CartItem> actualCartItems = cart.getContent();
    assertNotEquals(expectedCartItems, actualCartItems);

    cart.removeCartItemById((long) 6012);

    assertEquals(expectedCartItems, actualCartItems);
  }

  /**
   * Tests if cart items are correctly added to a cart
   */
  @Test
  public void addCartItem() {
    CartItem cartItemToAdd = mp.getCustomers().get(1).getCarts().get(0).getContent().get(0);
    List<CartItem> expectedCartItems = new ArrayList<>();
    expectedCartItems.add(cart.getContent().get(0));
    expectedCartItems.add(cart.getContent().get(1));
    expectedCartItems.add(cart.getContent().get(2));
    expectedCartItems.add(cartItemToAdd);

    List<CartItem> actualCartItems = cart.getContent();
    assertNotEquals(expectedCartItems, actualCartItems);

    cart.addCartItem(cartItemToAdd);

    assertEquals(expectedCartItems, actualCartItems);
  }

  /**
   * Tests if a cart item is correctly removed from a cart
   */
  @Test
  public void removeCartItem() {
    CartItem cartItemToRemove = cart.getContent().get(1);
    List<CartItem> expectedCartItems = new ArrayList<>();
    expectedCartItems.add(cart.getContent().get(0));
    expectedCartItems.add(cart.getContent().get(2));

    List<CartItem> actualCartItems = cart.getContent();
    assertNotEquals(expectedCartItems, actualCartItems);

    cart.removeCartItem(cartItemToRemove);

    assertEquals(expectedCartItems, actualCartItems);
  }
}