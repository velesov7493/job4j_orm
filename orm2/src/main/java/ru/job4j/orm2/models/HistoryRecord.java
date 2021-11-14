package ru.job4j.orm2.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tj_car_history_owners")
public class HistoryRecord {

    @Id
    @SequenceGenerator(
            name = "historyIdSeq",
            sequenceName = "tj_car_history_owners_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historyIdSeq")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_car")
    private Car car;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_driver")
    private Driver driver;
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDateTime;

    public HistoryRecord() {
        recDateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Date getRecDateTime() {
        return recDateTime;
    }

    public void setRecDateTime(Date recDateTime) {
        this.recDateTime = recDateTime;
    }
}
