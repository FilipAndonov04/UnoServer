package bg.sofia.uni.fmi.mjt.uno.server;

import bg.sofia.uni.fmi.mjt.uno.database.account.Database;
import bg.sofia.uni.fmi.mjt.uno.database.account.InMemoryDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.account.cipher.StringCipher;
import bg.sofia.uni.fmi.mjt.uno.database.account.loader.DatabaseLoader;
import bg.sofia.uni.fmi.mjt.uno.database.account.loader.FromStreamLoader;
import bg.sofia.uni.fmi.mjt.uno.database.account.saver.FileDatabaseSaver;
import bg.sofia.uni.fmi.mjt.uno.database.game.GameDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.game.UnoGameDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.AccountDataDatabase;
import bg.sofia.uni.fmi.mjt.uno.database.game.account.AccountDataInMemoryDatabase;
import bg.sofia.uni.fmi.mjt.uno.errorlogger.ErrorLogger;
import bg.sofia.uni.fmi.mjt.uno.errorlogger.StackTraceInFilePrinter;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {

    private static final int SERVER_PORT = 9999;

    private static final String DATABASE_FILE_NAME = "database.txt";
    private static final String SECRET_KEY_FILE_NAME = "secret.key";
    private static final String ERROR_FILE_NAME = "serverErrors.txt";

    private static Server instance;

    private final Database database;
    private final GameDatabase gameDatabase = new UnoGameDatabase();
    private final ErrorLogger errorLogger = new StackTraceInFilePrinter(ERROR_FILE_NAME);
    private final AccountDataDatabase accountDataDatabase = new AccountDataInMemoryDatabase();

    private boolean isRunning;

    private Server() {
        try {
            StringCipher cipher = StringCipher.getSimpleCipherFromStream(new FileInputStream(SECRET_KEY_FILE_NAME));
            DatabaseLoader loader = new FromStreamLoader(new FileReader(DATABASE_FILE_NAME), cipher);
            database = new InMemoryDatabase(loader.loadAccounts(), new FileDatabaseSaver(DATABASE_FILE_NAME, cipher));
        } catch (Exception e) {
            errorLogger.log(e);
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static synchronized Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public Database getDatabase() {
        return database;
    }

    public GameDatabase getGameDatabase() {
        return gameDatabase;
    }

    public ErrorLogger getErrorLogger() {
        return errorLogger;
    }

    public AccountDataDatabase getAccountDataDatabase() {
        return accountDataDatabase;
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }

    public synchronized void shutDown() {
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             ExecutorService executor = Executors.newCachedThreadPool()) {

            Socket clientSocket;
            while (isRunning) {
                clientSocket = serverSocket.accept();

                ClientRequestHandler clientHandler = new ClientRequestHandler(clientSocket);
                executor.execute(clientHandler);
            }
        } catch (IOException e) {
            errorLogger.log(e);
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }

    public static void main(String[] args) {
        Server server = Server.getInstance();
        server.start();

        try (Scanner scanner = new Scanner(System.in)) {
            while (server.isRunning()) {
                System.out.print("Enter command: ");
                String message = scanner.nextLine();

                if (message.equals("shutdown")) {
                    server.shutDown();
                    System.out.println("<SERVER HAS SHUTDOWN>");
                }
            }
        }
    }

}
