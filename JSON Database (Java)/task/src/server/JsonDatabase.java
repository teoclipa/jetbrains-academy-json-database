package server;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonDatabase {
    private final JsonObject database;
    private final ReadWriteLock lock;
    private static final String DB_PATH = "server/data/db.json";

    public JsonDatabase() {
        this.database = loadDb();
        this.lock = new ReentrantReadWriteLock();
    }

    private JsonObject loadDb() {
        try {
            if (Files.exists(Paths.get(DB_PATH))) {
                try (FileReader reader = new FileReader(DB_PATH)) {
                    JsonElement element = JsonParser.parseReader(reader);
                    if (element.isJsonObject()) {
                        return element.getAsJsonObject();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    private void writeDb() {
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            writer.write(new Gson().toJson(database));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonElement get(JsonElement key) {
        lock.readLock().lock();
        try {
            JsonElement currentLevel = database;
            if (key.isJsonArray()) {
                JsonArray keyArray = key.getAsJsonArray();
                for (JsonElement element : keyArray) {
                    if (currentLevel.isJsonObject()) {
                        currentLevel = currentLevel.getAsJsonObject().get(element.getAsString());
                    } else {
                        return null;
                    }
                }
            } else {
                currentLevel = database.get(key.getAsString());
            }
            return currentLevel;
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean set(JsonElement key, JsonElement value) {
        lock.writeLock().lock();
        try {
            JsonElement currentLevel = database;
            if (key.isJsonArray()) {
                JsonArray keyArray = key.getAsJsonArray();
                int size = keyArray.size();
                for (int i = 0; i < size - 1; i++) {
                    String keyElement = keyArray.get(i).getAsString();
                    if (currentLevel.isJsonObject()) {
                        JsonObject obj = currentLevel.getAsJsonObject();
                        if (!obj.has(keyElement) || !obj.get(keyElement).isJsonObject()) {
                            JsonObject newLevel = new JsonObject();
                            obj.add(keyElement, newLevel);
                        }
                        currentLevel = obj.get(keyElement);
                    } else {
                        return false;
                    }
                }
                String lastKey = keyArray.get(size - 1).getAsString();
                currentLevel.getAsJsonObject().add(lastKey, value);
            } else {
                database.add(key.getAsString(), value);
            }
            writeDb();
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean delete(JsonElement key) {
        lock.writeLock().lock();
        try {
            JsonElement currentLevel = database;
            if (key.isJsonArray()) {
                JsonArray keyArray = key.getAsJsonArray();
                int size = keyArray.size();
                for (int i = 0; i < size - 1; i++) {
                    String keyElement = keyArray.get(i).getAsString();
                    if (currentLevel.isJsonObject()) {
                        currentLevel = currentLevel.getAsJsonObject().get(keyElement);
                    } else {
                        return false;
                    }
                }
                String lastKey = keyArray.get(size - 1).getAsString();
                if (currentLevel.isJsonObject() && currentLevel.getAsJsonObject().has(lastKey)) {
                    currentLevel.getAsJsonObject().remove(lastKey);
                    writeDb();
                    return true;
                }
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
        if (database.has(key.getAsString())) {
            database.remove(key.getAsString());
            writeDb();
            return true;
        }
        return false;
    }

}
