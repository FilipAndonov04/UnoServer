package bg.sofia.uni.fmi.mjt.uno.game.card;

public record Card(Color color, Value value) {

    private static final String RESET_COLOR = "\u001B[0m";

    public boolean canBePlayedAfter(Card other) {
        return isWild() || color.equals(other.color) || value.equals(other.value);
    }

    public boolean isWild() {
        return color.equals(Color.WILD);
    }

    @Override
    public String toString() {
        return color.getColorCode() + value.getText() + RESET_COLOR;
    }

}
