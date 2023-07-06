package server;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private final Map<String, Command> commandMap;

    public CommandInvoker() {
        this.commandMap = new HashMap<>();
    }

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public JsonObject execute(String commandName) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("response", "ERROR");
            return errorResponse;
        }
        return command.execute();
    }
}
