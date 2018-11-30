/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.restresources;

import ch.hearc.ig.odi.minishop.business.Cart;
import ch.hearc.ig.odi.minishop.exception.CustomerException;
import ch.hearc.ig.odi.minishop.exception.NotFoundException;
import ch.hearc.ig.odi.minishop.exception.NullFormException;
import ch.hearc.ig.odi.minishop.exception.ProductException;
import ch.hearc.ig.odi.minishop.exception.StoreException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customer/{id}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class StoreResource {

  @Inject
  private MockPersistence persistenceService;

  /**
   * Returns a list of the customer's shopping carts
   *
   * @param id the id of the customer
   * @return the customer's shopping carts
   * @throws NotFoundException if the customer does not exist
   */
  @GET
  public List<Cart> getOpenCartsForCustomer(@PathParam("id") Long id) throws NotFoundException {
    try {
      return persistenceService.getOpenCartsForCustomer(id);
    } catch (CustomerException e) {
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Adds an item to a new shopping cart
   *
   * @param id the id of the customer
   * @param productId the id of the product
   * @param quantity the quantity of the product
   * @return the new cart
   * @throws NotFoundException if the product or the customer does not exist
   */
  @POST
  public Cart addProductToNewCart(@PathParam("id") Long id,
      @FormParam("productid") Long productId, @FormParam("quantity") Long quantity)
      throws NotFoundException {
    try {
      return persistenceService.addProductToNewCart(id, productId, quantity);
    } catch (ProductException | CustomerException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Returns content of given shopping cart id
   *
   * @param id the id of the customer
   * @param cartId the id of the cart
   * @return a cart
   * @throws NotFoundException if the cart does not exist
   */
  @GET
  @Path("/{cartId}")
  public Cart getCustomerCart(@PathParam("id") Long id,
      @PathParam("cartId") Long cartId) throws NotFoundException {
    try {
      return persistenceService.getCart(id, cartId);
    } catch (StoreException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Adds an item to a given shopping cart
   *
   * @param id the id of the customer
   * @param cartId the id of the cart
   * @param productId the id of the product
   * @param quantity the quantity of the product
   * @return the updated cart
   * @throws NotFoundException if the cart or the product does not exist
   */
  @POST
  @Path("/{cartId}")
  public Cart addProductToSpecificCart(@PathParam("id") Long id,
      @PathParam("cartId") Long cartId, @FormParam("productid") Long productId,
      @FormParam("quantity") Long quantity) throws NotFoundException {
    try {
      return persistenceService.addItemToAGivenCart(id, cartId, productId, quantity);
    } catch (StoreException | ProductException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Updates a shopping cart
   *
   * @param id the id of the customer
   * @param cartId the id of the cart
   * @param productId the id of the product
   * @param quantity the quantity of the product
   * @return the updated cart
   * @throws NotFoundException if the cart or the product does not exist
   */
  @PUT
  @Path("/{cartId}")
  public Cart updateCart(@PathParam("id") Long id,
      @PathParam("cartId") Long cartId, @FormParam("productid") Long productId,
      @FormParam("quantity") Long quantity) throws NotFoundException {
    try {
      return persistenceService.updateCart(id, cartId, productId, quantity);
    } catch (StoreException | ProductException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Returns total price of given shopping cart
   *
   * @param id the id of the customer
   * @param cartId the id of the cart
   * @return the total price of the shopping cart
   * @throws NotFoundException if the cart does not exist
   */
  @GET
  @Path("/{cartId}/total")
  public BigDecimal getCartAmount(@PathParam("id") Long id,
      @PathParam("cartId") Long cartId) throws NotFoundException {
    try {
      return persistenceService.getCartAmount(id, cartId);
    } catch (StoreException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Deletes an item in a given cart
   *
   * @param id the id of the customer
   * @param cartId the id of the cart
   * @param itemId the id of the item to remove
   * @throws NotFoundException if the cart or the item does not exist
   */
  @DELETE
  @Path("{cartId}/item/{itemId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void removeItemFromCart(@PathParam("id") Long id, @PathParam("cartId") Long cartId,
      @PathParam("itemId") Long itemId) throws NotFoundException {
    try {
      persistenceService.removeItemFromCart(id, cartId, itemId);
    } catch (StoreException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

}
