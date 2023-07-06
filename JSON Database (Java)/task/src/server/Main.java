package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final JsonDatabase db = new JsonDatabase();
    private static final CommandInvoker commandInvoker = new CommandInvoker();
    private static boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(23456)) {
            ExecutorService executor = Executors.newFixedThreadPool(4); // create an executor
            while (isRunning) {
                Socket socket = server.accept();
                executor.submit(() -> { // submit a task to the executor
                    try (DataInputStream input = new DataInputStream(socket.getInputStream());
                         DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                        String msgFromClient = input.readUTF();
                        System.out.println("Received: " + msgFromClient);

                        Gson gson = new Gson();
                        ClientArgs clientArgs = gson.fromJson(msgFromClient, ClientArgs.class);

                        String commandName = clientArgs.getType();
                        JsonElement key = clientArgs.getKey();
                        JsonElement value = clientArgs.getValue();

                        switch (commandName) {
                            case "get" -> commandInvoker.register(commandName, new GetCommand(db, key));
                            case "set" -> commandInvoker.register(commandName, new SetCommand(db, key, value));
                            case "delete" -> commandInvoker.register(commandName, new DeleteCommand(db, key));
                            case "exit" -> commandInvoker.register(commandName, new ExitCommand());
                        }

                        JsonObject response = commandInvoker.execute(commandName);
                        String responseJson = gson.toJson(response);
                        output.writeUTF(responseJson);
                        System.out.println("Sent: " + responseJson);

                        if ("exit".equals(commandName)) {
                            socket.close();
                            server.close();
                            isRunning = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            executor.shutdown(); // shutdown executor
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }
}
