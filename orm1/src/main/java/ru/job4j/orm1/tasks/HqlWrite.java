package ru.job4j.orm1.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.HibernateUtils;
import ru.job4j.orm1.models.Candidate;

import java.util.function.Consumer;

public class HqlWrite {

    private static final Logger LOG = LoggerFactory.getLogger(HqlWrite.class);
    private static final SessionFactory SF = HibernateUtils.getSessionFactory();

    private static boolean execute(Consumer<Session> dmlCommand) {
        Session session = SF.openSession();
        Transaction tx = session.beginTransaction();
        boolean error = false;
        try {
            dmlCommand.accept(session);
            session.flush();
        } catch (Exception ex) {
            error = true;
            LOG.error("Ошибка выполнения запроса", ex);
        } finally {
            if (error) {
                tx.rollback();
            } else {
                tx.commit();
            }
            session.close();
        }
        return !error;
    }

    private static boolean saveCandidate(Candidate value) {
        Consumer<Session> task = (s) -> {
            if (value.getId() == 0) {
                s.save(value);
            } else {
                String hql =
                    "UPDATE Candidate c SET c.name = :newName, "
                    + "c.experience = :newExperience, c.salary = :newSalary WHERE c.id = :cId";
                s.createQuery(hql)
                .setParameter("newName", value.getName())
                .setParameter("newExperience", value.getExperience())
                .setParameter("newSalary", value.getSalary())
                .setParameter("cId", value.getId())
                .executeUpdate();
            }
        };
        return execute(task);
    }

    private static boolean deleteCandidateById(int candidateId) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM Candidate c WHERE c.id = :cId";
            s.createQuery(hql)
            .setParameter("cId", candidateId)
            .executeUpdate();
        };
        return execute(task);
    }

    public static void main(String[] args) {
        Candidate[] candidates = new Candidate[] {
            new Candidate("Власов Александр Сергеевич", 2, 70000.50),
            new Candidate("Удалов Сергей Фёдорович", 20, 170000.77),
            new Candidate("Обскуров Вениамин Петрович", 70, 770000.25),
        };
        for (Candidate c : candidates) {
            saveCandidate(c);
            System.out.println("Записан: " + c.toString());
        }
        candidates[1].setExperience(22);
        candidates[1].setSalary(180000);
        saveCandidate(candidates[1]);
        System.out.println("Изменен кандидат: " + candidates[1]);
        System.out.println("Удаление кандидата с id=1: " + deleteCandidateById(1));
        HibernateUtils.releaseSessionFactory();
    }
}
