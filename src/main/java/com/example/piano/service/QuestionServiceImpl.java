package com.example.piano.service;

import com.example.piano.api.QuestionSearchResults;
import com.example.piano.dao.QuestionDao;
import com.example.piano.exception.PianoIllegalArgumentException;

/**
 * Created by adel on 9/15/17.
 */
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public QuestionSearchResults search(String intitle, String sort, Long page) {
        if (intitle == null) {
            intitle = "";
        }
        if (sort == null) {
            sort = "activity";
        }
        else if (!sort.equals("activity") && !sort.equals("creation") && !sort.equals("relevance") && !sort.equals("votes")) {
            throw new PianoIllegalArgumentException("sort",
                    "question sort available arguments values: votes | creation | relevance | activity");
        }
        if (page == null) {
            page = 0L;
        }

        return questionDao.search(intitle, sort, page);
    }

}
