package com.example.piano.client;

import com.example.piano.exception.PianoTestRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by adel on 9/16/17.
 */
public class StackOverflowClient {

    private static final Logger logger = LoggerFactory.getLogger(StackOverflowClient.class);

    private OkHttpClient client;

    public StackOverflowClient() {
        client = new OkHttpClient.Builder().build();
    }

    public StackOverflowClient(Long connectTimeout, Long readTimeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (connectTimeout != null)
            builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        if (readTimeout != null)
            builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        client = builder.build();
    }

    public StackOverflowSearchResults searchResults(String intitle, String sort, Long page) {
        Request request = new Request.Builder().get().url(new HttpUrl.Builder()
                .scheme("http").host("api.stackexchange.com").addPathSegment("2.2").addPathSegment("search")
                .addQueryParameter("intitle", intitle)
                .addQueryParameter("page", String.valueOf(page))
                .addQueryParameter("sort", sort)
                .addQueryParameter("site", "stackoverflow")
                .build()).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                StackOverflowClient.StackOverflowSearchResults stackOverflowSearchResults = new ObjectMapper().readValue(body.byteStream(), StackOverflowSearchResults.class);
                body.byteStream().close();
                return stackOverflowSearchResults;
            }
            else {
                logger.debug(response.body().string());
                throw new PianoTestRuntimeException("StackOverflow responsed with status: " + response.code() + " " + response.message());
            }
        }
        catch (SocketTimeoutException e) {
            logger.warn("StackOverflow connect timeout");
            throw new PianoTestRuntimeException("StackOverflow is not accessible");
        }
        catch (IOException e) {
            logger.warn("StackOverflow request caused IOException with: " + e.getMessage());
            throw new PianoTestRuntimeException("StackOverflow is not accessible");
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StackOverflowSearchResults {

        @JsonProperty("has_more")
        private boolean hasMore;
        @JsonProperty("items")
        private List<StackOverflowQuestionItem> questions;

        public StackOverflowSearchResults() {
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<StackOverflowQuestionItem> getQuestions() {
            return questions;
        }

        public void setQuestions(List<StackOverflowQuestionItem> questions) {
            this.questions = questions;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StackOverflowQuestionItem {
        @JsonProperty("question_id")
        private long id;
        @JsonProperty("creation_date")
        private long creationDate;
        private String title;
        @JsonProperty("link")
        private String url;
        @JsonProperty("owner")
        private StackOverflowAuthor author;

        public StackOverflowQuestionItem() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(long creationDate) {
            this.creationDate = creationDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public StackOverflowAuthor getAuthor() {
            return author;
        }

        public void setAuthor(StackOverflowAuthor author) {
            this.author = author;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StackOverflowAuthor {
        @JsonProperty("user_id")
        private long id;
        private String link;
        @JsonProperty("display_name")
        private String name;

        public StackOverflowAuthor() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
