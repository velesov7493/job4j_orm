package ru.job4j.orm1.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.HibernateUtils;
import ru.job4j.orm1.models.Candidate;

import java.util.List;
import java.util.function.Function;

public class HqlRead {

    private static final Logger LOG = LoggerFactory.getLogger(HqlRead.class);
    private static final SessionFactory SF = HibernateUtils.getSessionFactory();

    private static <T> T select(Function<Session, T> command) {
        T result = null;
        Session session = SF.openSession();
        Transaction tx = session.beginTransaction();
        boolean error = false;
        try {
            result = command.apply(session);
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
        return result;
    }

    private static List<Candidate> findAllCandidates() {
        Function<Session, List> f = (s) -> s.createQuery("FROM Candidate").getResultList();
        return select(f);
    }

    private static Candidate getCandidateById(int candidateId) {
        Function<Session, Candidate> f = (s) -> {
            String hql = "FROM Candidate c WHERE c.id = :cId";
            return
                    s.createQuery(hql, Candidate.class)
                    .setParameter("cId", candidateId)
                    .getSingleResult();
        };
        return select(f);
    }

    private static Candidate findCandidateByName(String name) {
        Function<Session, Candidate> f = (s) -> {
            String hql = "FROM Candidate c WHERE c.name = :fName";
            return
                    s.createQuery(hql, Candidate.class)
                    .setParameter("fName", name)
                    .getSingleResult();
        };
        return select(f);
    }

    public static void main(String[] args) {
        List<Candidate> candidates = findAllCandidates();
        System.out.println("Все кандидаты:");
        candidates.forEach(System.out::println);
        System.out.println("Кандидат с id=3:");
        System.out.println(getCandidateById(3));
        String searchName = "Власов Александр Сергеевич";
        System.out.println("Кандидат с именем " + searchName + ":");
        System.out.println(findCandidateByName(searchName));
        HibernateUtils.releaseSessionFactory();
    }
}
