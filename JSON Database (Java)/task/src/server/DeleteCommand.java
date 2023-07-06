package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DeleteCommand implements Command {
    private final JsonDatabase db;
    private final JsonElement key;

    public DeleteCommand(JsonDatabase db, JsonElement key) {
        this.db = db;
        this.key = key;
    }

    @Override
    public JsonObject execute() {
        JsonObject response = new JsonObject();
        if (db.delete(key)) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        }
        return response;
    }
}
