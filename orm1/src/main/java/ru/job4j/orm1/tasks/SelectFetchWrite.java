package ru.job4j.orm1.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.models.Candidate;
import ru.job4j.orm1.models.JobLibrary;
import ru.job4j.orm1.models.Vacancy;
import ru.job4j.orm1.stores.CandidateStore;
import ru.job4j.orm1.stores.JobLibraryStore;
import ru.job4j.orm1.stores.VacancyStore;

public class SelectFetchWrite {

    private static final Logger LOG = LoggerFactory.getLogger(SelectFetchWrite.class);

    public static void main(String[] args) {
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
}
