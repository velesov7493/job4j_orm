package ru.job4j.orm1.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.HibernateUtils;
import ru.job4j.orm1.models.Author;
import ru.job4j.orm1.models.Book;

public class ManyToMany {

    private static final Logger LOG = LoggerFactory.getLogger(ManyToMany.class);

    public static void main(String[] args) {
        try {
            SessionFactory sf = HibernateUtils.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();
            s.save(Author.of("Рудазов Александр"));
            s.save(Author.of("Рудазова Ксения"));
            Book[] books = new Book[13];
            books[0] = Book.of("Архимаг");
            books[1] = Book.of("Рыцари Пречистой Девы");
            books[2] = Book.of("Самое лучшее оружие");
            books[3] = Book.of("Серая чума");
            books[4] = Book.of("Война колдунов");
            books[5] = Book.of("Дети Судного Часа");
            books[6] = Book.of("Совет двенадцати");
            books[7] = Book.of("Битва Полчищ");
            books[8] = Book.of("Заря над бездной");
            books[9] = Book.of("Паргоронские байки: том 1");
            books[10] = Book.of("Паргоронские байки: том 2");
            books[11] = Book.of("Паргоронские байки: том 3");
            books[12] = Book.of("Паргоронские байки: том 4");
            Author author1 = s.load(Author.class, 1);
            Author author2 = s.load(Author.class, 2);
            for (int i = 9; i < books.length; i++) {
                books[i].addAuthor(author2);
            }
            for (Book book : books) {
                book.addAuthor(author1);
                s.save(book);
            }
            books[12].deleteAuthor(author2);
            s.save(books[12]);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            LOG.error("ORM: ошибка сохранения данных", ex);
        } finally {
            HibernateUtils.releaseSessionFactory();
        }
    }
}
