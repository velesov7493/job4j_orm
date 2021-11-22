package ru.job4j.orm1.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @SequenceGenerator(
            name = "candidatesIdSeq",
            sequenceName = "candidates_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidatesIdSeq")
    private int id;
    private String name;
    private int experience;
    private double salary;
    @OneToOne(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true, optional = false
    )
    @JoinColumn(name = "id_job_library")
    private JobLibrary jobLibrary;

    public Candidate() { }

    public Candidate(String aName, int aExperience, double aSalary) {
        name = aName;
        experience = aExperience;
        salary = aSalary;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public JobLibrary getJobLibrary() {
        return jobLibrary;
    }

    public void setJobLibrary(JobLibrary jobLibrary) {
        this.jobLibrary = jobLibrary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return
                id == candidate.id
                && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return
                "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", experience=" + experience
                + ", salary=" + salary
                + ", jobLibrary='" + jobLibrary.getName() + '\''
                + '}';
    }
}