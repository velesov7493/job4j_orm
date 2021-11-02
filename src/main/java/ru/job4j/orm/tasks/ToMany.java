package ru.job4j.orm.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm.HibernateUtils;
import ru.job4j.orm.models.CarBrand;
import ru.job4j.orm.models.CarModel;

public class ToMany {

    private static final Logger LOG = LoggerFactory.getLogger(ToMany.class);

    public static void main(String[] args) {
        try {
            SessionFactory sf = HibernateUtils.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();
            CarBrand brand1 = CarBrand.of("ВАЗ");
            CarBrand brand2 = CarBrand.of("УАЗ");
            s.save(CarModel.of("2101"));
            s.save(CarModel.of("2103"));
            s.save(CarModel.of("2105"));
            s.save(CarModel.of("2106"));
            s.save(CarModel.of("2107"));
            s.save(CarModel.of("3160"));
            s.save(CarModel.of("3162"));
            s.save(CarModel.of("2360"));
            s.save(CarModel.of("469"));
            s.save(CarModel.of("452"));
            brand1.addModel(s.load(CarModel.class, 1));
            brand1.addModel(s.load(CarModel.class, 2));
            brand1.addModel(s.load(CarModel.class, 3));
            brand1.addModel(s.load(CarModel.class, 4));
            brand1.addModel(s.load(CarModel.class, 5));
            brand2.addModel(s.load(CarModel.class, 6));
            brand2.addModel(s.load(CarModel.class, 7));
            brand2.addModel(s.load(CarModel.class, 8));
            brand2.addModel(s.load(CarModel.class, 9));
            brand2.addModel(s.load(CarModel.class, 10));
            s.save(brand1);
            s.save(brand2);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            LOG.error("ORM: ошибка сохранения данных", ex);
        } finally {
            HibernateUtils.releaseSessionFactory();
        }
    }
}
