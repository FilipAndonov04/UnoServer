package bg.sofia.uni.fmi.mjt.uno.game.gamelogs.spectator;

import java.io.PrintWriter;

public class ConsolePrintingSpectator implements Spectator {

    private final PrintWriter printWriter;

    public ConsolePrintingSpectator(PrintWriter printWriter) {
        if (printWriter == null) {
            throw new IllegalArgumentException("Print writer cannot be null!");
        }

        this.printWriter = printWriter;
    }

    @Override
    public void onEvent(String event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null!");
        }

        printWriter.println(event);
    }

}
