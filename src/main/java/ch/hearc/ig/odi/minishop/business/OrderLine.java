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
public class OrderLine implements Serializable {

  @JsonProperty("orderlineposition")
  private Long orderlineposition;
  @JsonProperty("product")
  private Product product;
  @JsonProperty("quantity")
  private Long quantity;

  public OrderLine() {
  }

  public OrderLine(Long orderlineposition, Product product, Long quantity) {
    this.orderlineposition = orderlineposition;
    this.product = product;
    this.quantity = quantity;
  }

  @XmlElement
  public Long getOrderlineposition() {
    return orderlineposition;
  }

  public void setOrderlineposition(Long orderlineposition) {
    this.orderlineposition = orderlineposition;
  }

  @XmlTransient
  public Product getProduct() {
    return product;
  }

  @XmlTransient
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

    sb.append("          id: " + this.getOrderlineposition() + "\n");
    sb.append("          *** PRODUCT ***" + "\n" + this.getProduct());
    sb.append("          quantity: " + this.getQuantity() + "\n" + "\n");

    return sb.toString();
  }
}
