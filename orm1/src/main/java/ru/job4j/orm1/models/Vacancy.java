package ru.job4j.orm1.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @SequenceGenerator(
        name = "vacanciesIdSeq",
        sequenceName = "vacancies_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacanciesIdSeq")
    private int id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_job_library")
    private JobLibrary jobLibrary;

    public Vacancy() { }

    public Vacancy(String aName) {
        name = aName;
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

    public JobLibrary getJobLibrary() {
        return jobLibrary;
    }

    public void setJobLibrary(JobLibrary value) {
        jobLibrary = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return
                id == vacancy.id
                && Objects.equals(name, vacancy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return
                "Vacancy{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
