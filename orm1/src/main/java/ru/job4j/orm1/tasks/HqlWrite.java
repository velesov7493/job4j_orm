package ru.job4j.orm1.tasks;

import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.Candidate;
import ru.job4j.orm1.stores.CandidateStore;

public class HqlWrite {

    public static void main(String[] args) {
        Candidate[] candidates = new Candidate[] {
            new Candidate("Власов Александр Сергеевич", 2, 70000.50),
            new Candidate("Удалов Сергей Фёдорович", 20, 170000.77),
            new Candidate("Обскуров Вениамин Петрович", 70, 770000.25),
        };
        for (Candidate c : candidates) {
            CandidateStore.save(c);
            System.out.println("Записан: " + c.toString());
        }
        candidates[1].setExperience(22);
        candidates[1].setSalary(180000);
        CandidateStore.save(candidates[1]);
        System.out.println("Изменен кандидат: " + candidates[1]);
        System.out.println("Удаление кандидата с id=1: " + CandidateStore.deleteById(1));
        DbUtils.releaseSessionFactory();
    }
}
