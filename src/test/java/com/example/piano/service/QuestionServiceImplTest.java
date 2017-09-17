package com.example.piano.service;

import com.example.piano.dao.QuestionDao;
import com.example.piano.exception.PianoIllegalArgumentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Created by adel on 9/17/17.
 */
public class QuestionServiceImplTest {

    private QuestionServiceImpl questionService;

    @Before
    public void init() {
        QuestionDao questionDao = mock(QuestionDao.class);
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    public void testExceptionThrown(){
        try {
            questionService.search("", "activity", 0L);
            fail();
        }
        catch (PianoIllegalArgumentException e) {
            assertEquals("intitle", e.getArgument());
        }
    }
}
