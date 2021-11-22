package ru.job4j.orm1.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.Candidate;
import ru.job4j.orm1.models.JobLibrary;
import ru.job4j.orm1.models.Vacancy;
import ru.job4j.orm1.stores.CandidateStore;
import ru.job4j.orm1.stores.JobLibraryStore;
import ru.job4j.orm1.stores.VacancyStore;

import java.util.List;
import java.util.Scanner;

public class SelectFetch {

    private static final Logger LOG = LoggerFactory.getLogger(SelectFetch.class);

    private static void cmdRead() {
        List<JobLibrary> libraries = JobLibraryStore.findAll();
        for (JobLibrary lib : libraries) {
            LOG.info("Вакансии хранилища `" + lib.getName() + "`:");
            for (Vacancy v : lib.getVacancies()) {
                LOG.info(v.toString());
            }
            LOG.info("Кадидат хранилища `" + lib.getName() + "`:" + lib.getCandidate().toString());
        }
    }

    private static void cmdWrite() {
        Vacancy[] vacancies = new Vacancy[] {
                new Vacancy("Java intern"),
                new Vacancy("Java junior"),
                new Vacancy("Java middle"),
                new Vacancy("Java senior")
        };
        JobLibrary library = new JobLibrary("Вакансии hh.ru");
        for (Vacancy v : vacancies) {
            VacancyStore.save(v);
            library.addVacancy(v);
            LOG.info("Записана вакансия: " + v.toString());
        }
        JobLibraryStore.save(library);
        LOG.info("Создано хранилище вакансий: " + library);
        Candidate candidate1 = new Candidate("Орешина Елена Михайловна", 4, 180000);
        candidate1.setJobLibrary(library);
        CandidateStore.save(candidate1);
        LOG.info("Создан кандидат: " + candidate1);
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