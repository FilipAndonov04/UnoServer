package bg.sofia.uni.fmi.mjt.uno.game.card;

public enum Value {

    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    SKIP("⊘"),
    REVERSE("⇅"),
    PLUS_TWO("+2"),
    CHOOSE_COLOR("⊕"),
    PLUS_FOUR("+4");

    private final String text;

    Value(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
