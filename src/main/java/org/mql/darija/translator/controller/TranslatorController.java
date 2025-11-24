package org.mql.darija.translator.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mql.darija.translator.model.TranslateRequest;
import org.mql.darija.translator.model.TranslateResponse;
import org.mql.darija.translator.service.TranslatorService;

@Path("/translator")
public class TranslatorController {

    private TranslatorService service = new TranslatorService();

    @POST
    @Path("/translate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response translate(TranslateRequest request) {
        String result = service.translate(request.text);
        return Response.ok(new TranslateResponse(result)).build();
    }
}
