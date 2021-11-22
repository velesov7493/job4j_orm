package ru.job4j.orm1.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "job_libraries")
public class JobLibrary {

    @Id
    @SequenceGenerator(
            name = "jobLibrariesIdSeq",
            sequenceName = "job_libraries_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobLibrariesIdSeq")
    private int id;
    private String name;
    @OneToOne(mappedBy = "jobLibrary", fetch = FetchType.LAZY, orphanRemoval = true)
    private Candidate candidate;
    @OneToMany(
            mappedBy = "jobLibrary", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true
    )
    private Set<Vacancy> vacancies;

    public JobLibrary() {
        vacancies = new HashSet<>();
    }

    public JobLibrary(String aName) {
        this();
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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void addVacancy(Vacancy value) {
        value.setJobLibrary(this);
        vacancies.add(value);
    }

    public void deleteVacancy(Vacancy value) {
        vacancies.remove(value);
    }

    public Set<Vacancy> getVacancies() {
        return new HashSet<>(vacancies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobLibrary that = (JobLibrary) o;
        return
                id == that.id
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return
                "JobLibrary{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}