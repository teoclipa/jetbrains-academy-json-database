package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GetCommand implements Command {
    private final JsonDatabase db;
    private final JsonElement key;

    public GetCommand(JsonDatabase db, JsonElement key) {
        this.db = db;
        this.key = key;
    }

    @Override
    public JsonObject execute() {
        JsonObject response = new JsonObject();
        JsonElement value = db.get(key);
        if (value != null) {
            response.addProperty("response", "OK");
            response.add("value", value);
        } else {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        }
        return response;
    }
}
