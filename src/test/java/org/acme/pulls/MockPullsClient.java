package org.acme.pulls;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import org.acme.pulls.PullsResource.PullsClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@RestClient
@Alternative
@Priority(1)
@Singleton
public class MockPullsClient implements PullsClient {

    @Override
    public JsonArray getPulls(String state) {
        JsonArrayBuilder dummy = Json.createArrayBuilder();
        dummy.add(Json.createObjectBuilder().add("html_url", "https://github.com/quarkusio/quarkus/pull/2715").add("title",
                "Super cool PR").add("useless", "Some even more useless info"));
        return dummy.build();
    }

}
