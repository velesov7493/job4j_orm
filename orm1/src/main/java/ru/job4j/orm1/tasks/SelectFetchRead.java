package ru.job4j.orm1.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.models.JobLibrary;
import ru.job4j.orm1.models.Vacancy;
import ru.job4j.orm1.stores.JobLibraryStore;

import java.util.List;

public class SelectFetchRead {

    private static final Logger LOG = LoggerFactory.getLogger(SelectFetchWrite.class);

    public static void main(String[] args) {
        List<JobLibrary> libraries = JobLibraryStore.findAll();
        for (JobLibrary lib : libraries) {
            LOG.info("Вакансии хранилища `" + lib.getName() + "`:");
            for (Vacancy v : lib.getVacancies()) {
                LOG.info(v.toString());
            }
            LOG.info("Кадидат хранилища `" + lib.getName() + "`:" + lib.getCandidate().toString());
        }
    }
}
