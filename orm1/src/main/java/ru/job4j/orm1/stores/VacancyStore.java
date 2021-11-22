package ru.job4j.orm1.stores;

import org.hibernate.Session;
import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.Vacancy;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class VacancyStore {

    public static List<Vacancy> findAll() {
        Function<Session, List> f = (s) -> {
            String hql =
                    "FROM Vacancy v "
                    + "JOIN FETCH v.jobLibrary";
            return s.createQuery(hql).getResultList();
        };
        return DbUtils.select(f);
    }

    public static Vacancy findById(int vacancyId) {
        Function<Session, Vacancy> f = (s) -> {
            String hql =
                    "FROM Vacancy v "
                    + "JOIN FETCH v.jobLibrary "
                    + "WHERE v.id = :fId";
            return
                    s.createQuery(hql, Vacancy.class)
                    .setParameter("fId", vacancyId)
                    .getSingleResult();
        };
        return DbUtils.select(f);
    }

    public static boolean save(Vacancy value) {
        Consumer<Session> task = (s) -> {
            if (value.getId() == 0) {
                s.save(value);
            } else {
                s.update(value);
            }
        };
        return DbUtils.execute(task);
    }

    public static boolean deleteById(int vacancyId) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM Vacancy v WHERE v.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", vacancyId)
            .executeUpdate();
        };
        return DbUtils.execute(task);
    }
}