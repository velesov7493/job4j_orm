package ru.job4j.orm.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "car_brands")
public class CarBrand {

    @Id
    @SequenceGenerator(
            name = "carBrandsIdSeq",
            sequenceName = "car_brands_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carBrandsIdSeq")
    private int id;
    private String name;
    @OneToMany(
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<CarModel> models;

    public CarBrand() {
        models = new HashSet<>();
    }

    public static CarBrand of(String brandName) {
        CarBrand b = new CarBrand();
        b.setName(brandName);
        return b;
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

    public void addModel(CarModel m) {
        models.add(m);
        m.setBrand(this);
    }

    public void deleteModel(CarModel m) {
        models.remove(m);
        m.setBrand(null);
    }

    public Set<CarModel> getModels() {
        return models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand carBrand = (CarBrand) o;
        return id == carBrand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return
                "CarBrand{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}