package com.github.mkouba.pulls;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
    GithubClient client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getPulls() throws IllegalStateException, RestClientDefinitionException, URISyntaxException {
        long start = System.currentTimeMillis();
        JsonArray pulls = client.getPulls("open");
        System.out.println(pulls.size() + " pull requests received in " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start) + " seconds");
        
        JsonArrayBuilder filtered = Json.createArrayBuilder();
        for (JsonValue pull : pulls) {
            JsonObjectBuilder obj = Json.createObjectBuilder();
            obj.add("title", pull.asJsonObject().get("title"));
            obj.add("html_url", pull.asJsonObject().get("html_url"));
            obj.add("user_login", pull.asJsonObject().get("user").asJsonObject().get("login"));
            filtered.add(obj);
        }
        return filtered.build();
    }

    @RegisterRestClient
    @Produces(MediaType.APPLICATION_JSON)
    public interface GithubClient {

        @Fallback(GithubFallback.class)
        @GET
        @Path("/pulls")
        JsonArray getPulls(@QueryParam("state") String state);

    }
    
    public static class GithubFallback implements FallbackHandler<JsonArray> {

        @Override
        public JsonArray handle(ExecutionContext context) {
            System.out.println("GitHub not available!");
            return Json.createArrayBuilder().build();
        }
        
    }
    
}