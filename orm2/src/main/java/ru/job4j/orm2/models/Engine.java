package ru.job4j.orm2.models;

import javax.persistence.*;

@Entity
@Table(name = "tz_engines")
public class Engine {

    @Id
    @SequenceGenerator(
            name = "enginesIdSeq",
            sequenceName = "tz_engines_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enginesIdSeq")
    private int id;
    private String name;
    private int power;
    private int capacity;

    public Engine() { }

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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
