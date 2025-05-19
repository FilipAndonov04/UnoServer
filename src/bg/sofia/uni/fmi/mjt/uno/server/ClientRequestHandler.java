package bg.sofia.uni.fmi.mjt.uno.server;

import bg.sofia.uni.fmi.mjt.uno.game.exception.GameException;
import bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator.ConsolePrintingSpectator;
import bg.sofia.uni.fmi.mjt.uno.user.UnoUser;
import bg.sofia.uni.fmi.mjt.uno.user.User;
import bg.sofia.uni.fmi.mjt.uno.user.command.Command;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserInput;
import bg.sofia.uni.fmi.mjt.uno.user.exception.InvalidUserOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {

    private static final String END_MESSAGE = "!END!";
    private static final String SHUTDOWN_MESSAGE = "!SHUTDOWN!";

    private static final String SERVER_HAS_SHUTDOWN_MESSAGE = "Server has shut down!";

    private final Socket socket;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Client Request Handler for " + socket.getRemoteSocketAddress());

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            User user = new UnoUser(Server.getInstance().getDatabase(), Server.getInstance().getGameDatabase(),
                new ConsolePrintingSpectator(out), Server.getInstance().getAccountDataDatabase());
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!Server.getInstance().isRunning()) {
                    out.println(SERVER_HAS_SHUTDOWN_MESSAGE);
                    out.println(SHUTDOWN_MESSAGE);
                    return;
                }

                handleInput(inputLine, out, user);
            }
        } catch (Exception e) {
            Server.getInstance().getErrorLogger().log(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Server.getInstance().getErrorLogger().log(e);
            }
        }
    }

    private void handleInput(String inputLine, PrintWriter printWriter, User user) {
        Command command;
        try {
            command = Command.create(inputLine, user, printWriter);
        } catch (InvalidUserInput e) {
            printWriter.println(e.getMessage());
            printWriter.println(END_MESSAGE);
            return;
        }
        try {
            command.execute();
        } catch (InvalidUserOperation | GameException e) {
            printWriter.println(e.getMessage());
            printWriter.println(END_MESSAGE);
            return;
        }

        printWriter.println("Command executed successfully!");
        printWriter.println(END_MESSAGE);
    }

}
