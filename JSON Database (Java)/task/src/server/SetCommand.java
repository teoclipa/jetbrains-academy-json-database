package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SetCommand implements Command {
    private final JsonDatabase db;
    private final JsonElement key;
    private final JsonElement value;

    public SetCommand(JsonDatabase db, JsonElement key, JsonElement value) {
        this.db = db;
        this.key = key;
        this.value = value;
    }

    @Override
    public JsonObject execute() {
        JsonObject response = new JsonObject();
        if (db.set(key, value)) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
        }
        return response;
    }
}
