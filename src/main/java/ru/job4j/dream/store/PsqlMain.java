package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.sql.Timestamp;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();

        System.out.println("********Job creation and candidates********");
        for (int i = 0; i < 3; i++) {
            store.save(new Post(0, "Java Job", "Description", new Timestamp(System.currentTimeMillis())));
        }
        for (int i = 0; i < 3; i++) {
            store.save(new Candidate(0, "Candidate "));
        }

        System.out.println("********Find All********");
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }

        System.out.println("********Update********");
        store.save(new Post(2, "UPDATE JAVA JOB", "New description...", new Timestamp(System.currentTimeMillis())));
        store.save(new Candidate(2, "UPDATE CANDIDATE"));

        System.out.println("********After update********");
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }

        System.out.println("********Find By Id********");
        System.out.println(store.findPostById(2));
        System.out.println(store.findCandidateById(2));
    }
}
