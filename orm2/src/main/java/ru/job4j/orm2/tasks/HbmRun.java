package ru.job4j.orm2.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm2.HibernateUtils;
import ru.job4j.orm2.models.Car;
import ru.job4j.orm2.models.Driver;
import ru.job4j.orm2.models.Engine;
import ru.job4j.orm2.models.HistoryRecord;

public class HbmRun {

    private static final Logger LOG = LoggerFactory.getLogger(HbmRun.class);

    public static void main(String[] args) {
        try {
            SessionFactory sf = HibernateUtils.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();
            Engine engine1 = new Engine();
            engine1.setName("ВАЗ 2130");
            engine1.setCapacity(1790);
            engine1.setPower(82);
            s.save(engine1);
            Driver driver1 = new Driver();
            driver1.setName("Петров Валерий Николаевич");
            s.save(driver1);
            Driver driver2 = new Driver();
            driver2.setName("Баширов Валерий Петрович");
            s.save(driver2);
            Car car1 = new Car();
            car1.setName("ВАЗ-21214");
            car1.setEngine(s.load(Engine.class, 1));
            s.save(car1);
            HistoryRecord record1 = new HistoryRecord();
            record1.setDriver(s.load(Driver.class, 1));
            record1.setCar(s.load(Car.class, 1));
            s.save(record1);
            HistoryRecord record2 = new HistoryRecord();
            record2.setDriver(s.load(Driver.class, 2));
            record2.setCar(s.load(Car.class, 1));
            s.save(record2);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            LOG.error("ORM: ошибка сохранения данных", ex);
        } finally {
            HibernateUtils.releaseSessionFactory();
        }
    }
}
