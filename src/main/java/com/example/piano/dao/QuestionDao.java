package com.example.piano.dao;

import com.example.piano.api.QuestionSearchResults;

/**
 * Created by adel on 9/15/17.
 */
public interface QuestionDao {

    QuestionSearchResults search(String intitle, String sort, Long page);

}
