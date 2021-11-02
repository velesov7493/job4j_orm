package ru.job4j.orm.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_models")
public class CarModel {

    @Id
    @SequenceGenerator(
            name = "carModelsIdSeq",
            sequenceName = "car_models_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carModelsIdSeq")
    private int id;
    private String name;

    public static CarModel of(String modelName) {
        CarModel m = new CarModel();
        m.setName(modelName);
        return m;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return
                id == carModel.id
                && Objects.equals(name, carModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return
                "CarModel{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}