package com.example.piano.providers;

import com.example.piano.api.ApiError;
import com.example.piano.exception.PianoIllegalArgumentException;
import com.google.common.collect.Maps;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by adel on 9/17/17.
 */
@Provider
public class PianoIllegalArgumentExceptionMapper implements ExceptionMapper<PianoIllegalArgumentException> {
    @Override
    public Response toResponse(PianoIllegalArgumentException e) {
        HashMap<String, Object> entity = Maps.newHashMap();
        entity.put("argument", e.getArgument());
        entity.put("reason", e.getReason());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ApiError().id(UUID.randomUUID().toString())
                        .message("bad request")
                        .entity(entity))
                .build();
    }
}
