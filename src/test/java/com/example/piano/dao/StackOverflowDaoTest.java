package com.example.piano.dao;

import com.example.piano.api.Question;
import com.example.piano.api.QuestionSearchResults;
import com.example.piano.client.StackOverflowClient;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by adel on 9/16/17.
 */
@RunWith(JUnit4.class)
public class StackOverflowDaoTest {

    private StackOverflowDao dao;
    private StackOverflowClient mockClient;
    private Generator generator;

    @Before
    public void init() throws IOException {
        generator = new Generator();
        mockClient = mock(StackOverflowClient.class);
        dao = new StackOverflowDao(mockClient);
    }

    @Test
    public void testSort() {
        List<String> sortTypes = Lists.newArrayList("date", "title", "author");
        sortTypes.forEach(s -> doReturn(generator.getResponse(s)).when(mockClient).searchResults(any(), eq(s), any()));

        sortTypes.forEach(s -> {
            QuestionSearchResults search = dao.search("", s, 0L);
            boolean sorted = true;
            Comparator<Question> comparator = questionComparator(s);
            for (int i = 0; i < search.getQuestions().size() - 1; ++i) {
                sorted &= comparator.compare(search.getQuestions().get(i), search.getQuestions().get(i + 1)) <= 0;
            }
            assertTrue(sorted);
        });
    }

    private static Comparator<StackOverflowClient.StackOverflowQuestionItem> questionItemComparator(String sort) {
        return (o1, o2) -> {
            if (sort.equals("date")) {
                return Long.compare(o1.getCreationDate(), o2.getCreationDate());
            }
            if (sort.equals("title")) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (sort.equals("author")) {
                return o1.getAuthor().getName().compareTo(o2.getAuthor().getName());
            }
            fail("unknown sort type in test");
            return 0;
        };
    }

    private static Comparator<Question> questionComparator(String sort) {
        return (o1, o2) -> {
            if (sort.equals("date")) {
                return Long.compare(o1.getDate(), o2.getDate());
            }
            if (sort.equals("title")) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (sort.equals("author")) {
                return o1.getAuthor().getName().compareTo(o2.getAuthor().getName());
            }
            fail("unknown sort type in test");
            return 0;
        };
    }

    private static class Generator {

        private long qId = 1;
        private long aId = 1001;

        private StackOverflowClient.StackOverflowSearchResults getResponse(String sort) {
            StackOverflowClient.StackOverflowSearchResults stackOverflowSearchResults = new StackOverflowClient.StackOverflowSearchResults();
            stackOverflowSearchResults.setHasMore(false);
            List<StackOverflowClient.StackOverflowQuestionItem> questionItems = Lists.newArrayList(
                    getStackOverflowQuestionItem(),
                    getStackOverflowQuestionItem(),
                    getStackOverflowQuestionItem()
            );
            stackOverflowSearchResults.setQuestions(questionItems);
            stackOverflowSearchResults.getQuestions().sort(questionItemComparator(sort));
            return stackOverflowSearchResults;
        }

        private StackOverflowClient.StackOverflowQuestionItem getStackOverflowQuestionItem() {
            StackOverflowClient.StackOverflowQuestionItem stackOverflowQuestionItem = new StackOverflowClient.StackOverflowQuestionItem();
            stackOverflowQuestionItem.setId(qId);
            stackOverflowQuestionItem.setTitle("Question title #" + qId);
            stackOverflowQuestionItem.setCreationDate(System.currentTimeMillis() - (qId * 1000 * 60 * 60 * 24));
            stackOverflowQuestionItem.setUrl("http://stackoverflow.com/question/" + qId);
            stackOverflowQuestionItem.setAuthor(getAuthor());
            ++qId;
            return stackOverflowQuestionItem;
        }

        private StackOverflowClient.StackOverflowAuthor getAuthor() {
            StackOverflowClient.StackOverflowAuthor author = new StackOverflowClient.StackOverflowAuthor();
            author.setId(aId);
            author.setLink("http://stackoverflow.com/author/" + qId);
            author.setName("Author name: " + qId);
            ++aId;
            return author;
        }
    }

}
