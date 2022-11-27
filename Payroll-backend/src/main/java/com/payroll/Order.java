package com.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String description;
    private Status status;

    public Order() {
    }

    public Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Order order))
            return false;

        return Objects.equals(this.getId(), order.id)
                && Objects.equals(this.getDescription(), order.description)
                && Objects.equals(this.getStatus(), order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getDescription(), this.getStatus());
    }

    @Override
    public String toString() {
        return "Order {" + "id = " + this.getId() + ", description = " + this.getDescription() + '\'' + ", status = "
                + this.getStatus() + '}';
    }
}
