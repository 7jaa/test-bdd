/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Product")
public class Product {

  @JsonProperty("productid")
  private Long productid;
  @JsonProperty("productname")
  private String productname;
  @JsonProperty("description")
  private String description;
  @JsonProperty("category")
  private String category;
  @JsonProperty("price")
  private BigDecimal price;
  @JsonProperty("status")
  private String status = ProductStatus.ACTIVE.toString();

  public enum ProductStatus {
    ACTIVE("active"), INACTIVE("inactive");
    private String productStatusName;

    ProductStatus(String productStatusName) {
      this.productStatusName = productStatusName;
    }

    public String toString() {
      return super.toString().toLowerCase();
    }
  }

  public Product() {
  }

  public Product(Long productid, String productname, String description, String category,
      BigDecimal price) {
    this.productid = productid;
    this.productname = productname;
    this.description = description;
    this.category = category;
    this.price = price;
    this.status = ProductStatus.ACTIVE.toString();
  }

  @XmlElement
  public Long getProductid() {
    return productid;
  }

  public void setProductid(Long productid) {
    this.productid = productid;
  }

  @XmlElement
  public String getProductname() {
    return productname;
  }

  public void setProductname(String productname) {
    this.productname = productname;
  }

  @XmlElement
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @XmlElement
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @XmlElement
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @XmlElement
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("          product id: " + this.getProductid() + "\n");
    sb.append("          product name: " + this.getProductname() + "\n");
    sb.append("          description: " + this.getDescription() + "\n");
    sb.append("          category: " + this.getCategory() + "\n");
    sb.append("          price: " + this.getPrice() + "\n");
    sb.append("          status: " + this.getStatus() + "\n");

    return sb.toString();
  }

}
