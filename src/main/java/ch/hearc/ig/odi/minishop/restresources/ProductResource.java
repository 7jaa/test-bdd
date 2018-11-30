/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.restresources;

import ch.hearc.ig.odi.minishop.business.Product;
import ch.hearc.ig.odi.minishop.exception.NotFoundException;
import ch.hearc.ig.odi.minishop.exception.NullFormException;
import ch.hearc.ig.odi.minishop.exception.ProductException;
import ch.hearc.ig.odi.minishop.services.MockPersistence;
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

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ProductResource {

  @Inject
  private MockPersistence persistenceService;

  /**
   * Returns all products
   *
   * @return all products
   */
  @GET
  public List<Product> getProducts() {
    return persistenceService.getProducts();
  }

  /**
   * Creates a new product
   *
   * @param productName the name of the product
   * @param description the description of the product
   * @param category the category of the product
   * @param price the price of the product
   * @return the created product
   * @throws NullFormException if a required form parameter is null
   */
  @POST
  public Product createProduct(@FormParam("productname") String productName,
      @FormParam("description") String description,
      @FormParam("category") String category, @FormParam("price") String price)
      throws NullFormException {
    try {
      return persistenceService.createProduct(productName, description, category, price);
    } catch (ProductException e) {
      e.printStackTrace();
      throw new NullFormException(e.getMessage());
    }
  }

  /**
   * Returns a single product
   *
   * @param productId the id of the product
   * @return a product
   * @throws NotFoundException if the product does not exist
   */
  @GET
  @Path("/{id}")
  public Product getProduct(@PathParam("id") Long productId) throws NotFoundException {
    try {
      return persistenceService.getProduct(productId);
    } catch (ProductException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Updates a product
   *
   * @param id the id of the product
   * @param product the new data
   * @return the updated product
   * @throws NotFoundException if the product does not exist
   */
  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Product updateProduct(@PathParam("id") Long id, Product product) throws NotFoundException {
    try {
      return persistenceService.updateProduct(id, product);
    } catch (ProductException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  /**
   * Deletes a product
   *
   * @param id the id of the product
   * @throws NotFoundException if the product does not exist
   */
  @DELETE
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void deleteProduct(@PathParam("id") Long id) throws NotFoundException {
    try {
      persistenceService.deleteProduct(id);
    } catch (ProductException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

}
