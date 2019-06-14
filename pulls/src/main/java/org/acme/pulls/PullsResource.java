package org.acme.pulls;

import java.net.URISyntaxException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.rest.client.RestClientDefinitionException;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/pulls")
public class PullsResource {

    @RestClient
    PullsClient client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getPulls() throws IllegalStateException, RestClientDefinitionException, URISyntaxException {
        JsonArray pulls = client.getPulls("open");
        JsonArrayBuilder filtered = Json.createArrayBuilder();
        for (JsonValue pull : pulls) {
            JsonObject pullJson = pull.asJsonObject();
            filtered.add(Json.createObjectBuilder().add("title", pullJson.get("title")).add("url", pullJson.get("html_url")));
        }
        return filtered.build();
    }

    @RegisterRestClient
    public interface PullsClient {

        @Fallback(PullsFallback.class)
        @GET
        @Path("/pulls")
        JsonArray getPulls(@QueryParam("state") String state);

    }

    public static class PullsFallback implements FallbackHandler<JsonArray> {

        @Override
        public JsonArray handle(ExecutionContext context) {
            System.out.println("GITHUB NOT AVAILABLE!");
            return Json.createArrayBuilder().build();
        }

    }

}