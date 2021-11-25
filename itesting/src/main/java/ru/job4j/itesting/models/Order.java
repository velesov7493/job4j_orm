package ru.job4j.itesting.models;

import java.util.Date;
import java.util.Objects;

public class Order {

    private int id;
    private String name;
    private String description;
    private Date created;

    public Order(int aId, String aName, String aDescription, Date aCreated) {
        id = aId;
        name = aName;
        description = aDescription;
        created = aCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return
                id == order.id
                && Objects.equals(name, order.name)
                && Objects.equals(description, order.description)
                && Objects.equals(created, order.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created);
    }
}
