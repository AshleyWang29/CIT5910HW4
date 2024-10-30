
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"H", "D", "C", "S"};
        cards = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        } else {
            return null;
        }
    }
}
