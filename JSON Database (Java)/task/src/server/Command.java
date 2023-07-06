package server;

import com.google.gson.JsonObject;

public interface Command {
    JsonObject execute();
}
