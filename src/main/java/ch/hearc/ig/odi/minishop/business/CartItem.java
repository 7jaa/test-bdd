/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "cartItem")
public class CartItem implements Serializable {

  @JsonProperty("cartitemid")
  private Long cartitemid;
  @JsonProperty("product")
  private Product product;
  @JsonProperty("quantity")
  private Long quantity;

  public CartItem() {

  }

  public CartItem(Long cartitemid, Product product, Long quantity) {
    this.cartitemid = cartitemid;
    this.product = product;
    this.quantity = quantity;
  }

  @XmlElement
  public Long getCartitemid() {
    return cartitemid;
  }

  public void setCartItem(Long cartItemId) {
    this.cartitemid = cartItemId;
  }

  @XmlTransient
  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @XmlElement
  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("          id: " + this.getCartitemid() + "\n");
    sb.append("          *** PRODUCT ***" + "\n" + this.getProduct());
    sb.append("          quantity: " + this.getQuantity() + "\n" + "\n");

    return sb.toString();
  }
}
