/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author user
 */
public class WarGameTest {
    private WarGame warGame;

    @BeforeEach
    public void setUp() {
        warGame = new WarGame("War");
        warGame.getPlayers().add(new WarPlayer("Player 1"));
        warGame.getPlayers().add(new WarPlayer("Player 2"));
        warGame.getPlayers().add(new WarPlayer("Player 3"));
        warGame.getPlayers().add(new WarPlayer("Player 4"));
    }

    @Test
    public void testShuffleDeck() {
        GroupOfCards deck = new GroupOfCards(52);
        ArrayList<Card> cardsBeforeShuffle = new ArrayList<>(deck.getCards());
        deck.shuffle();
        ArrayList<Card> cardsAfterShuffle = deck.getCards();
        assertNotEquals(cardsBeforeShuffle, cardsAfterShuffle, "Deck should be shuffled");
    }

    @Test
    public void testDistributeCards() {
        warGame.play();
        int totalCards = warGame.getPlayers().stream().mapToInt(player -> ((WarPlayer) player).getHand().size()).sum();
        assertEquals(52, totalCards, "Total cards should be 52 after distribution");
    }

    @Test
    public void testPlayerElimination() {
        WarPlayer player1 = new WarPlayer("Player 1");
        WarPlayer player2 = new WarPlayer("Player 2");
        WarPlayer player3 = new WarPlayer("Player 3");
        WarPlayer player4 = new WarPlayer("Player 4");

        player1.getHand().clear();
        player2.getHand().clear();
        player3.getHand().clear();
        player4.getHand().add(new Card("Ace", "Spades"));

        warGame.getPlayers().clear();
        warGame.getPlayers().add(player1);
        warGame.getPlayers().add(player2);
        warGame.getPlayers().add(player3);
        warGame.getPlayers().add(player4);

        warGame.play();

        assertTrue(player1.getHand().isEmpty(), "Player 1 should be eliminated");
        assertTrue(player2.getHand().isEmpty(), "Player 2 should be eliminated");
        assertTrue(player3.getHand().isEmpty(), "Player 3 should be eliminated");
        assertFalse(player4.getHand().isEmpty(), "Player 4 should not be eliminated");
    }

    @Test
    public void testDeclareWinner() {
        WarPlayer player1 = new WarPlayer("Player 1");
        WarPlayer player2 = new WarPlayer("Player 2");
        WarPlayer player3 = new WarPlayer("Player 3");
        WarPlayer player4 = new WarPlayer("Player 4");

        player1.getHand().add(new Card("Ace", "Spades"));
        player2.getHand().add(new Card("King", "Hearts"));
        player3.getHand().add(new Card("Queen", "Diamonds"));
        player4.getHand().add(new Card("Jack", "Clubs"));

        warGame.getPlayers().clear();
        warGame.getPlayers().add(player1);
        warGame.getPlayers().add(player2);
        warGame.getPlayers().add(player3);
        warGame.getPlayers().add(player4);

        warGame.play();
        ArrayList<Player> winners = new ArrayList<>();
        winners.add(player1);
        winners.add(player2);

        assertTrue(winners.stream().map(Player::getPlayerID).collect(Collectors.toList()).containsAll(
                warGame.getPlayers().stream().map(Player::getPlayerID).collect(Collectors.toList())), "There should be a tie between Player 1 and Player 2");
    }

    @Test
    public void testCompareCards() {
        Card card1 = new Card("Ace", "Spades");
        Card card2 = new Card("King", "Hearts");
        Card card3 = new Card("Ace", "Hearts");

        int result1 = warGame.compareCards(card1, card2);
        int result2 = warGame.compareCards(card1, card3);
        int result3 = warGame.compareCards(card2, card3);

        assertTrue(result1 > 0, "Ace should be greater than King");
        assertEquals(0, result2, "Ace should be equal to Ace");
        assertTrue(result3 < 0, "King should be less than Ace");
    }

    @Test
    public void testUniquePlayerIDs() {
        Set<String> playerIDs = new HashSet<>();
        for (Player player : warGame.getPlayers()) {
            assertTrue(playerIDs.add(player.getPlayerID()), "Player IDs should be unique");
        }
    }

    @Test
    public void testBoundaryCase() {
        WarPlayer player1 = new WarPlayer("Player 1");
        WarPlayer player2 = new WarPlayer("Player 2");

        player1.getHand().add(new Card("Ace", "Spades"));
        player2.getHand().add(new Card("2", "Hearts"));

        warGame.getPlayers().clear();
        warGame.getPlayers().add(player1);
        warGame.getPlayers().add(player2);

        warGame.play();

        assertEquals(1, warGame.getPlayers().size(), "Only one player should remain");
        assertEquals("Player 1", warGame.getPlayers().get(0).getPlayerID(), "Player 1 should be the winner");
    }
}
