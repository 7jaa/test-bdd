/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.restresources;

import ch.hearc.ig.odi.minishop.business.Order;
import ch.hearc.ig.odi.minishop.exception.NotFoundException;
import ch.hearc.ig.odi.minishop.exception.NullFormException;
import ch.hearc.ig.odi.minishop.exception.OrderException;
import ch.hearc.ig.odi.minishop.exception.StoreException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class OrderResource {

  @Inject
  private MockPersistence persistenceService;

  /**
   * Returns all orders
   *
   * @return all orders
   */
  @GET
  public List<Order> getOrders() {
    return persistenceService.getOrders();
  }

  /**
   * Creates a new order
   *
   * @param cartId the id of shopping cart that will be ordered
   * @return the created order
   * @throws NullFormException if a form parameter is null
   */
  @POST
  public Order createOrder(@FormParam("cartid") Long cartId) throws NullFormException {
    try {
      return persistenceService.createOrder(cartId);
    } catch (StoreException e) {
      e.printStackTrace();
      throw new NullFormException(e.getMessage());
    }
  }

  /**
   * Returns a given order
   *
   * @param orderId the id of the order
   * @return the order
   * @throws NotFoundException if the order does not exist
   */
  @GET
  @Path("/{id}")
  public Order getOrder(@PathParam("id") Long orderId) throws NotFoundException {
    try {
      return persistenceService.getOrder(orderId);
    } catch (OrderException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Updates the status of a given order
   *
   * @param id the id of the order
   * @param newStatus the new status
   * @return the updated order
   * @throws NotFoundException if the order does not exist
   */
  @PUT
  @Path("/{id}")
  public Order updateOrder(@PathParam("id") Long id, @FormParam("newstatus") String newStatus)
      throws NotFoundException {
    try {
      return persistenceService.updateOrder(id, newStatus);
    } catch (OrderException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

}
