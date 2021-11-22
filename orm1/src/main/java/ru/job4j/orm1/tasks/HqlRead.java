package ru.job4j.orm1.tasks;

import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.Candidate;
import ru.job4j.orm1.stores.CandidateStore;

import java.util.List;

public class HqlRead {

    public static void main(String[] args) {
        List<Candidate> candidates = CandidateStore.findAll();
        System.out.println("Все кандидаты:");
        candidates.forEach(System.out::println);
        System.out.println("Кандидат с id=3:");
        System.out.println(CandidateStore.findById(3));
        String searchName = "Власов Александр Сергеевич";
        System.out.println("Кандидат с именем " + searchName + ":");
        System.out.println(CandidateStore.findByName(searchName));
        DbUtils.releaseSessionFactory();
    }
}
