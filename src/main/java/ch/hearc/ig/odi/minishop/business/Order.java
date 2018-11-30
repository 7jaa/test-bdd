/*
 * Copyright (c) 2018. Cours Outils de développement intégré, HEG Arc.
 */

package ch.hearc.ig.odi.minishop.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Order")
public class Order implements Serializable {

  @JsonProperty("orderid")
  private Long orderid;
  @JsonProperty("orderdate")
  private Date orderdate;
  @JsonProperty("content")
  private List<OrderLine> content;
  @JsonProperty("orderstatus")
  private String orderstatus = OrderStatus.OPEN.toString();

  public enum OrderStatus {
    OPEN("open"), CONFIRMED("confirmed"), PAID("paid"), SHIPPED("shipped"), CANCELED("canceled");
    private String orderStatusName;

    OrderStatus(String statusName) {
      this.orderStatusName = statusName;
    }

    public String toString() {
      return super.toString().toLowerCase();
    }
  }

  public Order() {
    this.content = new ArrayList<>();
    this.orderstatus = OrderStatus.OPEN.toString();
  }

  public Order(Long orderid, Date orderdate) {
    this.orderid = orderid;
    this.orderdate = orderdate;
    this.content = new ArrayList<>();
    this.orderstatus = OrderStatus.OPEN.toString();
  }

  @XmlElement
  public Long getOrderid() {
    return orderid;
  }

  public void setOrderid(Long orderid) {
    this.orderid = orderid;
  }

  @XmlElement
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  public Date getOrderdate() {
    return orderdate;
  }

  public void setOrderdate(Date orderdate) {
    this.orderdate = orderdate;
  }

  @XmlElement
  public List<OrderLine> getContent() {
    return content;
  }

  public void setContent(List<OrderLine> content) {
    this.content = content;
  }

  @XmlElement
  public String getOrderstatus() {
    return orderstatus;
  }

  public void setOrderstatus(String orderstatus) {
    this.orderstatus = orderstatus;
  }

  /**
   * Add a new order line
   *
   * @param orderLine : new orderLine to add to content
   */
  public void addOrderLine(OrderLine orderLine) {
    this.content.add(orderLine);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("     id: " + this.getOrderid() + "\n");
    sb.append("     order date: " + this.getOrderdate() + "\n");
    sb.append("     order status: " + this.getOrderstatus() + "\n");
    sb.append("     *** ORDER LINES ***" + "\n");
    for (int i = 0; i < this.content.size(); i++) {
      sb.append("          >Order line n°" + i + "\n");
      sb.append(this.content.get(i));
    }
    if (this.content.size() == 0) {
      sb.append("     >This customer does not have any order lines" + "\n" + "\n");
    }

    return sb.toString();
  }

}
