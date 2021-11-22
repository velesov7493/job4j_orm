package ru.job4j.orm1.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.Candidate;
import ru.job4j.orm1.models.JobLibrary;
import ru.job4j.orm1.stores.CandidateStore;
import ru.job4j.orm1.stores.JobLibraryStore;

import java.util.List;
import java.util.Scanner;

public class Hql {

    private static final Logger LOG = LoggerFactory.getLogger(Hql.class);

    private static void cmdRead() {
        List<Candidate> candidates = CandidateStore.findAll();
        LOG.info("Все кандидаты:");
        candidates.forEach((c) -> LOG.info(c.toString()));
        LOG.info("Кандидат с id=3:");
        Candidate candidate = CandidateStore.findById(3);
        LOG.info(candidate == null ? "Не найден" : candidate.toString());
        String searchName = "Власов Александр Сергеевич";
        LOG.info("Кандидаты с именем " + searchName + ":");
        candidates = CandidateStore.findByName(searchName);
        candidates.forEach((c) -> LOG.info(c.toString()));
    }

    private static void cmdWrite() {
        Candidate[] candidates = new Candidate[] {
                new Candidate("Власов Александр Сергеевич", 2, 70000.50),
                new Candidate("Удалов Сергей Фёдорович", 20, 170000.77),
                new Candidate("Обскуров Вениамин Петрович", 70, 770000.25),
        };
        for (Candidate c : candidates) {
            JobLibrary library = new JobLibrary("Хранилище кандидата " + c.getName());
            JobLibraryStore.save(library);
            c.setJobLibrary(library);
            CandidateStore.save(c);
            LOG.info("Записан: " + c.toString());
        }
        candidates[1].setExperience(22);
        candidates[1].setSalary(180000);
        CandidateStore.save(candidates[1]);
        LOG.info("Изменен кандидат: " + candidates[1]);
        LOG.info("Удаление кандидата с id=1: " + CandidateStore.deleteById(1));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String prompt = "Список команд: 1 - записать данные, 2 - прочитать данные, 3 - выход";
        System.out.println(prompt);
        int cmd;
        do {
            System.out.print("?>");
            cmd = sc.nextInt();
            switch (cmd) {
                case 1: cmdWrite(); break;
                case 2: cmdRead(); break;
                case 3: break;
                default: System.out.println(prompt); break;
            }
        } while (cmd != 3);
        DbUtils.releaseSessionFactory();
    }
}
