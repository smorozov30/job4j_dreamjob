package ru.job4j.dream;

public class Answer {
    private String text;

    public Answer(String name) {
        this.text = name;
    }

    public String getName() {
        return text;
    }

    public void updateName(String text) {
        this.text = text;
    }
}
