package ru.job4j.orm2.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tz_drivers")
public class Driver {

    @Id
    @SequenceGenerator(
            name = "driversIdSeq",
            sequenceName = "tz_drivers_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driversIdSeq")
    private int id;
    private String name;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<HistoryRecord> historyRecords;

    public Driver() {
        historyRecords = new ArrayList<>();
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

    public void addHistoryRecord(HistoryRecord value) {
        value.setDriver(this);
        historyRecords.add(value);
    }

    public void removeHistoryRecord(HistoryRecord value) {
        historyRecords.remove(value);
    }

    public List<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }
}