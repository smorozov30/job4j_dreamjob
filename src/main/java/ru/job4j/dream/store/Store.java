package ru.job4j.dream.store;

import ru.job4j.dream.model.*;

import java.util.Collection;

public interface Store {
    void addUser(User user);
    User checkEmail(User user);

    void save(Post post);
    Post findPostById(int id);
    Collection<Post> findAllPosts();

    void save(Candidate candidate);
    Candidate findCandidateById(int id);
    Collection<Candidate> findAllCandidates();

    void save(Photo photo);
    Photo findPhotoById(int id);
    Collection<Photo> findAllPhotos();

    City findCityById(int id);
    Collection<City> findAllCities();

    void delete(Candidate candidate);

}
