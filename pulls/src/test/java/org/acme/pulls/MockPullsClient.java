package org.acme.pulls;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import org.acme.pulls.PullsResource.PullsClient;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.test.Mock;

@RestClient
@Mock
public class MockPullsClient implements PullsClient {

    @Inject
    Config config;
    
    @Override
    public JsonArray getPulls(String state) {
        JsonArrayBuilder dummy = Json.createArrayBuilder();
        dummy.add(Json.createObjectBuilder().add("html_url", "https://github.com/quarkusio/quarkus/pull/2715").add("title",
                "Super cool PR").add("useless", "Some even more useless info"));
        return dummy.build();
    }

}
