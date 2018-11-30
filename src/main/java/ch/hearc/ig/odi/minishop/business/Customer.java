/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Customer")
public class Customer implements Serializable {

  @JsonProperty("customerid")
  private Long customerid;
  @JsonProperty("username")
  private String username;
  @JsonProperty("firstname")
  private String firstname;
  @JsonProperty("lastname")
  private String lastname;
  @JsonProperty("email")
  private String email;
  @JsonProperty("phone")
  private String phone;
  @JsonIgnore
  private List<Cart> carts;
  @JsonIgnore
  private List<Order> orders;


  public Customer() {
    this.orders = new ArrayList<>();
    this.carts = new ArrayList<>();
  }

  public Customer(Long customerid, String username, String firstname, String lastname, String email,
      String phone) {
    this.customerid = customerid;
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phone = phone;
    this.carts = new ArrayList<>();
    this.orders = new ArrayList<>();
  }

  @XmlElement
  public Long getCustomerid() {
    return customerid;
  }

  public void setCustomerid(Long customerId) {
    this.customerid = customerId;
  }

  @XmlElement
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @XmlElement
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstName) {
    this.firstname = firstName;
  }

  @XmlElement
  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastName) {
    this.lastname = lastName;
  }

  @XmlElement
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @XmlElement
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @JsonIgnore
  public List<Cart> getCarts() {
    return carts;
  }

  public void setCarts(List<Cart> carts) {
    this.carts = carts;
  }

  @JsonIgnore
  public List getOrders() {
    return orders;
  }


  public void setOrders(List orders) {
    this.orders = orders;
  }

  /**
   * Add a cart to list of carts
   *
   * @param cart : new cart to add to carts
   */
  public void addCart(Cart cart) {
    this.carts.add(cart);
    cart.setCustomer(this);
  }

  /**
   * Add an order to list of orders
   *
   * @param order : new order to add to orders
   */
  public void addOrder(Order order) {
    this.orders.add(order);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("id: " + this.getCustomerid() + "\n");
    sb.append("username: " + this.getUsername() + "\n");
    sb.append("first name: " + this.getFirstname() + "\n");
    sb.append("last name: " + this.getLastname() + "\n");
    sb.append("email: " + this.getEmail() + "\n");
    sb.append("phone: " + this.getPhone() + "\n");

    sb.append("*** CARTS ***" + "\n");
    for (int i = 0; i < this.carts.size(); i++) {
      sb.append("     >Cart n°" + i + "\n");
      sb.append(this.carts.get(i));
    }
    if (this.carts.size() == 0) {
      sb.append("     >This customer does not have any cart" + "\n" + "\n");
    }

    sb.append("*** ORDERS ***" + "\n");
    for (int i = 0; i < this.orders.size(); i++) {
      sb.append("     >Order n°" + i + "\n");
      sb.append(this.orders.get(i));
    }
    if (this.orders.size() == 0) {
      sb.append("     >This customer does not have any order" + "\n" + "\n");
    }

    return sb.toString();
  }


}

