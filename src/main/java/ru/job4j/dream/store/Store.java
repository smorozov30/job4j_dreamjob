package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Photo;
import ru.job4j.dream.model.Post;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();
    Collection<Candidate> findAllCandidates();
    Collection<Photo> findAllPhotos();
    void save(Post post);
    Post findPostById(int id);
    void save(Candidate candidate);
    Candidate findCandidateById(int id);
    void save(Photo photo);
    Photo findPhotoById(int id);
    void delete(Candidate candidate);
}
