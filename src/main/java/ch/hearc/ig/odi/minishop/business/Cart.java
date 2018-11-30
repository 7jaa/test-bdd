/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cart")
public class Cart {

  @JsonProperty("cartid")
  private Long cartid;
  @JsonProperty("cartstatus")
  private String cartstatus = CartStatus.OPEN.toString();
  @JsonProperty("content")
  private ArrayList<CartItem> content;
  @JsonIgnore
  private Customer customer;

  public enum CartStatus {
    OPEN("open"), CHECK_OUT("check_out");
    private String cartStatusName;

    CartStatus(String statusName) {
      this.cartStatusName = statusName;
    }

    public String toString() {
      return super.toString().toLowerCase();
    }
  }

  public Cart() {
    this.content = new ArrayList<>();
  }

  public Cart(Long cartid) {
    this.cartid = cartid;
    this.content = new ArrayList<>();
  }

  @XmlElement
  public Long getCartid() {
    return cartid;
  }

  public void setCartid(Long cartid) {
    this.cartid = cartid;
  }

  @XmlElement
  public String getCartstatus() {
    return cartstatus;
  }

  public void setCartstatus(String cartstatus) {
    this.cartstatus = cartstatus;
  }

  @XmlElement
  public ArrayList<CartItem> getContent() {
    return content;
  }

  public void setContent(ArrayList<CartItem> content) {
    this.content = content;
  }

  /**
   * Removes a cart item
   *
   * @param cartItemId the id of the cart item to remove
   */
  public void removeCartItemById(Long cartItemId) {
    int indexOfCartItemToRemove;
    Long actualCartItemID;

    for (int i = 0; i < this.content.size(); i++) {
      actualCartItemID = this.content.get(i).getCartitemid();
      if (actualCartItemID.equals(cartItemId)) {
        indexOfCartItemToRemove = i;
        this.content.remove(indexOfCartItemToRemove);
      }
    }
  }

  /**
   * Add a cartItem to the list of content
   *
   * @param cartItem : new cartItem to add to content
   */
  public void addCartItem(CartItem cartItem) {
    this.content.add(cartItem);
  }

  /**
   * Remove a cartItem from the list of content
   *
   * @param cartItem : cartItem to remove from content
   */
  public void removeCartItem(CartItem cartItem) {
    this.content.remove(cartItem);
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @JsonIgnore
  public Customer getCustomer() {
    return customer;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("      id: " + this.getCartid() + "\n");
    sb.append("      status: " + this.getCartstatus() + "\n");
    sb.append("      *** CART ITEMS ***" + "\n");
    for (int i = 0; i < this.content.size(); i++) {
      sb.append("          >Cart Item n°" + i + "\n");
      sb.append(this.content.get(i));
    }
    if (this.content.size() == 0) {
      sb.append("          >This customer does not have any cart item" + "\n" + "\n");
    }

    return sb.toString();
  }

}
