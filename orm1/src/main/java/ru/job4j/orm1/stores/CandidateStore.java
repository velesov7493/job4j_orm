package ru.job4j.orm1.stores;

import org.hibernate.Session;
import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.Candidate;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CandidateStore {

    public static List<Candidate> findAll() {
        Function<Session, List> f = (s) -> {
            String hql = "FROM Candidate c JOIN FETCH c.jobLibrary";
            return s.createQuery(hql).getResultList();
        };
        return DbUtils.select(f);
    }

    public static Candidate findById(int candidateId) {
        Function<Session, Candidate> f = (s) -> {
            String hql = "FROM Candidate c JOIN FETCH c.jobLibrary WHERE c.id = :cId";
            return
                    s.createQuery(hql, Candidate.class)
                    .setParameter("cId", candidateId)
                    .getSingleResult();
        };
        return DbUtils.select(f);
    }

    public static List<Candidate> findByName(String name) {
        Function<Session, List> f = (s) -> {
            String hql = "FROM Candidate c JOIN FETCH c.jobLibrary WHERE c.name = :fName";
            return
                    s.createQuery(hql, Candidate.class)
                    .setParameter("fName", name)
                    .getResultList();
        };
        return DbUtils.select(f);
    }

    public static boolean save(Candidate value) {
        Consumer<Session> task = (s) -> {
            if (value.getId() == 0) {
                s.save(value);
            } else {
                String hql =
                        "UPDATE Candidate c SET c.name = :newName, c.jobLibrary = :newLibrary, "
                        + "c.experience = :newExperience, c.salary = :newSalary WHERE c.id = :cId";
                s.createQuery(hql)
                .setParameter("newName", value.getName())
                .setParameter("newExperience", value.getExperience())
                .setParameter("newSalary", value.getSalary())
                .setParameter("newLibrary", value.getJobLibrary())
                .setParameter("cId", value.getId())
                .executeUpdate();
            }
        };
        return DbUtils.execute(task);
    }

    public static boolean deleteById(int candidateId) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM Candidate c WHERE c.id = :cId";
            s.createQuery(hql)
            .setParameter("cId", candidateId)
            .executeUpdate();
        };
        return DbUtils.execute(task);
    }
}
