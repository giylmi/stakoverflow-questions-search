package com.example.piano.service;

import com.example.piano.api.QuestionSearchResults;

/**
 * Created by adel on 9/15/17.
 */
public interface QuestionService {

    QuestionSearchResults search(String intitle, String sort, Long page);
}
