package com.example.piano.api;

import java.util.List;

/**
 * Created by adel on 9/15/17.
 */
public class QuestionSearchResults {

    private boolean hasMore;
    private String sort;
    private List<Question> questions;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
