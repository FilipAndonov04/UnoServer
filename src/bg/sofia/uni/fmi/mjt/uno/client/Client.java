package bg.sofia.uni.fmi.mjt.uno.client;

import bg.sofia.uni.fmi.mjt.uno.errorlogger.ErrorLogger;
import bg.sofia.uni.fmi.mjt.uno.errorlogger.StackTraceInFilePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 9999;

    private static final String ERROR_FILE_NAME = "clientErrors2.txt";

    private static final String END_MESSAGE = "!END!";
    private static final String SHUTDOWN_MESSAGE = "!SHUTDOWN!";

    private static final String QUIT_COMMAND = "quit";

    public static void main(String[] args) {
        ErrorLogger errorLogger = new StackTraceInFilePrinter(ERROR_FILE_NAME);

        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("You have successfully connected to the server!");
            System.out.println("Type help to see the available commands!");

            while (communicate(writer, reader, scanner)) {
            }

        } catch (Exception e) {
            errorLogger.log(e);
            System.out.println("There is a problem with the server!");
        }

        System.out.println("Have a nice day!");
    }

    private static boolean communicate(PrintWriter writer, BufferedReader reader, Scanner scanner) throws IOException {
        System.out.print("Enter command: ");
        String message = scanner.nextLine();

        if (message.equals(QUIT_COMMAND)) {
            return false;
        }

        writer.println(message);

        String reply = reader.readLine();
        while (!reply.equals(END_MESSAGE)) {
            if (reply.equals(SHUTDOWN_MESSAGE)) {
                return false;
            }
            System.out.println(reply);
            reply = reader.readLine();
        }
        return true;
    }

}
