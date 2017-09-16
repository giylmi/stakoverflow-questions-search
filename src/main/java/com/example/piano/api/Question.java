package com.example.piano.api;

/**
 * Created by adel on 9/15/17.
 */
public class Question {

    private String id;
    private String title;
    private Long date;
    private String url;
    private QuestionAuthor author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public QuestionAuthor getAuthor() {
        return author;
    }

    public void setAuthor(QuestionAuthor author) {
        this.author = author;
    }
}
