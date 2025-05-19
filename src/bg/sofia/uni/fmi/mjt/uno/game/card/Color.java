package bg.sofia.uni.fmi.mjt.uno.game.card;

public enum Color {

    WILD("\u001B[0m", "Wild"),
    RED("\u001B[31m", "Red"),
    GREEN("\u001B[32m", "Green"),
    YELLOW("\u001B[33m", "Yellow"),
    BLUE("\u001B[34m", "Blue");

    private final String colorCode;
    private final String name;

    Color(String colorCode, String name) {
        this.colorCode = colorCode;
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getName() {
        return name;
    }

}
