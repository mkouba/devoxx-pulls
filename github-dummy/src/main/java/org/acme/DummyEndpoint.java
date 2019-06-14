package org.acme;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pulls")
public class DummyEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getPulls() {
        JsonArrayBuilder dummy = Json.createArrayBuilder();
        dummy.add(Json.createObjectBuilder().add("html_url", "https://github.com/quarkusio/quarkus/pull/2715").add("title",
                "My super cool PR").add("state", "open").add("number", 2715));
        dummy.add(Json.createObjectBuilder().add("html_url", "https://github.com/quarkusio/quarkus/pull/2716").add("title",
                "Another super cool PR").add("state", "open").add("number", 2715));
        return dummy.build();
    }
}