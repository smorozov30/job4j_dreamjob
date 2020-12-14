package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Photo;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(1);
    private static final AtomicInteger PHOTO_ID = new AtomicInteger(3);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, Photo> photos = new ConcurrentHashMap<>();

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Collection<Photo> findAllPhotos() {
        return photos.values();
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public void save(Photo photo) {
        if (photo.getId() == 0) {
            photo.setId(PHOTO_ID.incrementAndGet());
        }
        photos.put(photo.getId(), photo);
    }

    @Override
    public Photo findPhotoById(int id) {
        return photos.get(id);
    }

    @Override
    public void delete(Candidate candidate) {
        candidates.remove(candidate.getId());
        photos.remove(candidate.getPhotoId());
    }
}
