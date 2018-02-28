import SearchManager.FoodSearch;
import UserManager.Users;
import Workers.FoodData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private static ObjectMapper om = new ObjectMapper();
    static void main(String... args) {

    }

    // Our Event.type should follow a EVENT:TYPE convention
    public static Response dispatchEvent(Event event) throws Exception {
        System.out.println("\n\nEventType \n" + event.getType());
        String domain = event.getType().split(":")[0];

        switch(domain) {
            case "SEARCH":
                return SearchEvent(event);
            case "USER":
                return UserEvent(event);
            case "TEST":
                // Write the return value to a string for transport in the response object.
                return TestEvent(event);
            case "PRIVATE":
                break;
            default:
                System.out.println("Event Not Recognized " + event.getType());
        }
        Response response = new Response(false, "No Event Found", "No Body", event.getType());
        return response;
    }

    /**
     * Each event handler should be static
     * Shows: Setup how to handle an event.
     * @param evt
     * @return Response
     * @throws Exception
     */
    private static Response TestEvent(Event evt) throws Exception {
        // Instantiate your manager to consume the Events payload.
        FakeManager fm = new FakeManager();

        String managerDone = fm.doSomething(evt.getPayload());

        // Managers that return a response should use the Response.class
        // if manager is not suppose to respond then manually send a Response.ok == true
        Response response = new Response(
                true,
                "Manager is done.",
                managerDone,
                evt.getType()
        );

        return response;
        // TODO Wire up Managers
    }

    private static Response SearchEvent(Event evt) throws Exception {
        String type = evt.getType().split(":")[1];
        switch (type) {
            case"REPORT":
                String result = om.writeValueAsString(FoodSearch.getReport(evt.getPayload()));
                return  new Response(
                        true,
                        "Report Found",
                        result,
                        evt.getType()
                );
            case"DESCRIPTION":
                String description = om.writeValueAsString(FoodSearch.getDescription(evt.getPayload()));
                return  new Response(
                        true,
                        "Description Found",
                        description,
                        evt.getType()
                );
            case"NUTRIENT":
                FoodData.retrieveNutrient(evt.getPayload());
                return  new Response(
                        true,
                        "Description Found",
                        "Test Nutrient Search",
                        evt.getType()
                );
        }
        return new Response(
                false,
                "Search Type Not Found: " + evt.getType(),
                "No Body",
                evt.getType()
        );
    }

    private static Response UserEvent(Event evt) throws IOException {
        String type = evt.getType().split(":")[1];
        switch (type) {
            case "NEW_USER":
                HashMap payload = om.readValue(evt.getPayload(), HashMap.class);
                String result = Users.newUser(payload);
                return new Response(
                        true,
                        "IEW_User Created",
                        result,
                        evt.getType()
                );
        }
        return new Response(
                false,
                "IEW_User Type Not Found: " + evt.getType(),
                "No Body",
                evt.getType()
        );
    }
}
