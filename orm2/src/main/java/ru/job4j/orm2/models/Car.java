package ru.job4j.orm2.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tz_cars")
public class Car {

    @Id
    @SequenceGenerator(
            name = "carsIdSeq",
            sequenceName = "tz_cars_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carsIdSeq")
    private int id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "id_engine")
    private Engine engine;
    private String name;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<HistoryRecord> historyRecords;

    public Car() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHistoryRecord(HistoryRecord value) {
        value.setCar(this);
        historyRecords.add(value);
    }

    public void removeHistoryRecord(HistoryRecord value) {
        historyRecords.remove(value);
    }

    public List<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }
}
