
import java.util.Scanner;

public class BlackjackSolitaire {
    private final Deck deck;
    private final Card[] grid;
    private int discardsRemaining;

    public BlackjackSolitaire() {
        deck = new Deck();
        deck.shuffle();
        grid = new Card[16]; // 16 scoring spots
        discardsRemaining = 4;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Card drawnCard = deck.drawCard();
            if (drawnCard == null) {
                break;
            }

            System.out.println("Drawn card: " + drawnCard);
            displayGrid();
            System.out.println("Enter a position (1-16) to place the card, or enter 0 to discard (" + discardsRemaining + " discards remaining):");
            int position = scanner.nextInt();

            if (position >= 1 && position <= 16) {
                if (grid[position - 1] == null) {
                    grid[position - 1] = drawnCard;
                } else {
                    System.out.println("Position already filled. Try again.");
                    continue;
                }
            } else if (position == 0) {
                if (discardsRemaining > 0) {
                    discardsRemaining--;
                } else {
                    System.out.println("No discards remaining. Try again.");
                    continue;
                }
            } else {
                System.out.println("Invalid position. Try again.");
                continue;
            }

            if (isGameOver()) {
                break;
            }
        }

        int score = calculateScore();
        System.out.println("Game over! You scored " + score + " points.");
    }

    private void displayGrid() {
        for (int i = 0; i < 16; i++) {
            if (i == 5) {
                System.out.println();
            }
            if (i == 10 || i == 13) {
                System.out.print("   ");
            }
            if (grid[i] == null) {
                System.out.print((i + 1) + "\t");
            } else {
                System.out.print(grid[i] + "\t");
            }
            if (i == 9 || i == 12 || i == 15) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private boolean isGameOver() {
        for (int i = 0; i < 16; i++) {
            if (grid[i] == null) {
                return false;
            }
        }
        return true;
    }

    private int calculateScore() {
        int score = 0;

        // Calculate score for rows
        for (int i = 0; i < 15; i += 5) {
            int rowScore = calculateHandScore(new Card[]{grid[i], grid[i + 1], grid[i + 2], grid[i + 3], (i + 4 < 16 ? grid[i + 4] : null)});
            score += rowScore;
        }

        // Calculate score for columns
        for (int i = 0; i < 5; i++) {
            int colScore = calculateHandScore(new Card[]{grid[i], grid[i + 5], (i + 10 < 16 ? grid[i + 10] : null), (i + 15 < 16 ? grid[i + 15] : null)});
            score += colScore;
        }

        return score;
    }

    private int calculateHandScore(Card[] hand) {
        int sum = 0;
        int aceCount = 0;
        for (Card card : hand) {
            if (card == null) {
                continue;
            }
            String rank = card.getRank();
            if (rank.equals("A")) {
                aceCount++;
                sum += 11;
            } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                sum += 10;
            } else {
                sum += Integer.parseInt(rank);
            }
        }

        // Adjust for aces if sum exceeds 21
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }

        // Scoring rules
        if (sum == 21 && hand.length == 2) {
            return 10; // Blackjack
        } else if (sum == 21) {
            return 7;
        } else if (sum == 20) {
            return 5;
        } else if (sum == 19) {
            return 4;
        } else if (sum == 18) {
            return 3;
        } else if (sum == 17) {
            return 2;
        } else if (sum <= 16) {
            return 1;
        } else {
            return 0; // Bust
        }
    }
}