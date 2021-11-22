package ru.job4j.orm1.stores;

import org.hibernate.Session;
import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.JobLibrary;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class JobLibraryStore {

    public static List<JobLibrary> findAll() {
        Function<Session, List> f = (s) -> {
            String hql =
                    "SELECT DISTINCT jl FROM JobLibrary jl "
                    + "JOIN FETCH jl.candidate "
                    + "LEFT JOIN FETCH jl.vacancies";
            return s.createQuery(hql).getResultList();
        };
        return DbUtils.select(f);
    }

    public static JobLibrary findById(int libraryId) {
        Function<Session, JobLibrary> f = (s) -> {
            String hql =
                    "FROM JobLibrary jl "
                    + "JOIN FETCH jl.candidate "
                    + "LEFT JOIN FETCH jl.vacancies "
                    + "WHERE jl.id = :fId";
            return
                    s.createQuery(hql, JobLibrary.class)
                    .setParameter("fId", libraryId)
                    .getSingleResult();
        };
        return DbUtils.select(f);
    }

    public static boolean save(JobLibrary value) {
        Consumer<Session> task = (s) -> {
            if (value.getId() == 0) {
                s.save(value);
            } else {
                s.update(value);
            }
        };
        return DbUtils.execute(task);
    }

    public static boolean deleteById(int libraryId) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM JobLibrary jl WHERE jl.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", libraryId)
            .executeUpdate();
        };
        return DbUtils.execute(task);
    }
}
