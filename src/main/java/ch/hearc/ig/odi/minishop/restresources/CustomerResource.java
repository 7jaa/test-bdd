/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.restresources;

import ch.hearc.ig.odi.minishop.business.Customer;
import ch.hearc.ig.odi.minishop.exception.CustomerException;
import ch.hearc.ig.odi.minishop.exception.NotFoundException;
import ch.hearc.ig.odi.minishop.exception.NullFormException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class CustomerResource {

  @Inject
  private MockPersistence persistenceService;

  /**
   * Returns all customers
   *
   * @return all customers
   */
  @GET
  public List<Customer> getCustomers() {
    return persistenceService.getCustomers();
  }

  /**
   * Adds a new customer to the customers list
   *
   * @param username the username of the customer
   * @param firstName the first name of the customer
   * @param lastName the last name of the customer
   * @param email the email of the customer
   * @param phone the phone number of the customer
   * @return the created customer
   * @throws NullFormException if a required form parameter is null
   */
  @POST
  public Customer createCustomer(@FormParam("username") String username,
      @FormParam("firstname") String firstName, @FormParam("lastname") String lastName,
      @FormParam("email") String email, @FormParam("phone") String phone) throws NullFormException {
    try {
      return persistenceService.createCustomer(username, firstName, lastName, email, phone);
    } catch (CustomerException e) {
      e.printStackTrace();
      throw new NullFormException(e.getMessage());
    }
  }

  /**
   * Returns a single customer
   *
   * @param customerId the id of the customer
   * @return a customer
   * @throws NotFoundException if the customer does not exist
   */
  @GET
  @Path("/{id}")
  public Customer getCustomer(@Context HttpServletRequest servletRequest,
      @PathParam("id") Long customerId) throws NotFoundException {
    try {
      return persistenceService.getCustomer(customerId);
    } catch (CustomerException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Updates a customer
   *
   * @param id the id of the customer
   * @param customer the new data
   * @return the updated customer
   * @throws NotFoundException if the customer does not exist
   */
  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Customer updateCustomer(@PathParam("id") Long id, Customer customer)
      throws NotFoundException {
    try {
      return persistenceService.updateCustomer(id, customer);
    } catch (CustomerException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Deletes a customer
   *
   * @param id the id of the customer
   * @throws NotFoundException if the customer does not exist
   */
  @DELETE
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void deleteCustomer(@PathParam("id") Long id) throws NotFoundException {
    try {
      persistenceService.deleteCustomer(id);
    } catch (CustomerException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

}

