package com.example.piano.resources;

import com.example.piano.api.QuestionSearchResults;
import com.example.piano.service.QuestionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by adel on 9/15/17.
 */
@Path("/questions")
@Produces(APPLICATION_JSON)
public class QuestionResource {

    private QuestionService questionService;

    public QuestionResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GET
    @Path("search")
    public QuestionSearchResults search(@QueryParam("intitle") String intitle,
                                        @QueryParam("sort") String sort,
                                        @QueryParam("page") Long page) {
        return questionService.search(intitle, sort, page);
    }

}
