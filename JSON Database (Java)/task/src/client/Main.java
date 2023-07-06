package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        ClientArgs arguments = new ClientArgs();
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        boolean fromFile = false;

        if (arguments.getType() != null) {
            jsonObject.addProperty("type", arguments.getType());
        }
        if (arguments.getKey() != null) {
            jsonObject.addProperty("key", arguments.getKey().toString());
        }
        if (arguments.getValue() != null) {
            jsonObject.addProperty("value", arguments.getValue());
        }

        if (arguments.getInFile() != null) {
            fromFile = true;
            try {
                String filePath = System.getProperty("user.dir") + "/src/client/data/" + arguments.getInFile();
                String content = Files.readString(Paths.get(filePath));
                jsonObject = gson.fromJson(content, JsonObject.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Client started!");

        try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            String requestJson = gson.toJson(jsonObject);
            output.writeUTF(requestJson);
            System.out.println("Sent: " + requestJson);

            if ("exit".equals(jsonObject.get("type").getAsString()) && !fromFile) {
                String response = input.readUTF();
                System.out.println("Received: " + response);
                return;
            }

            String response = input.readUTF();
            System.out.println("Received: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}