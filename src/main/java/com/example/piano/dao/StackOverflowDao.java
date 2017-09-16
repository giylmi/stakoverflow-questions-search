package com.example.piano.dao;

import com.example.piano.api.Question;
import com.example.piano.api.QuestionAuthor;
import com.example.piano.api.QuestionSearchResults;
import com.example.piano.client.StackOverflowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

/**
 * Created by adel on 9/15/17.
 */
public class StackOverflowDao implements QuestionDao {

    private static Logger logger = LoggerFactory.getLogger(StackOverflowDao.class);

    private final StackOverflowClient client;

    public StackOverflowDao(StackOverflowClient client) {
        this.client = client;
    }

    /**
     *
     * @param intitle
     * @param sort votes | creation | relevevance | activity
     * @param page number of page starting from 0. We add + 1 as stackoverflow starts from 1
     * @return
     */
    public QuestionSearchResults search(String intitle, String sort, Long page) {
        StackOverflowClient.StackOverflowSearchResults stackOverflowSearchResults = client.searchResults(intitle, sort, page + 1);
        QuestionSearchResults results = map(stackOverflowSearchResults);
        results.setSort(sort);
        return results;
    }

    private static QuestionSearchResults map(StackOverflowClient.StackOverflowSearchResults search) {
        QuestionSearchResults results = new QuestionSearchResults();
        results.setHasMore(search.isHasMore());
        if (search.getQuestions() != null) {
            results.setQuestions(search.getQuestions().stream().map(StackOverflowDao::map).collect(Collectors.toList()));
        }
        return results;
    }

    private static Question map(StackOverflowClient.StackOverflowQuestionItem item) {
        Question question = new Question();
        question.setDate(item.getCreationDate());
        question.setId(String.valueOf(item.getId()));
        question.setTitle(item.getTitle());
        question.setUrl(item.getUrl());
        question.setAuthor(map(item.getAuthor()));
        return question;
    }

    private static QuestionAuthor map(StackOverflowClient.StackOverflowAuthor sAuthor) {
        QuestionAuthor author = new QuestionAuthor();
        author.setId(String.valueOf(sAuthor.getId()));
        author.setName(sAuthor.getName());
        author.setUrl(sAuthor.getLink());
        return author;
    }


}
