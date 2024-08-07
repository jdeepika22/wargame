/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *
 * @author user
 */

public class WarGame extends Game {
    private GroupOfCards deck;
    private static final int MAX_ROUNDS = 100;
    
    public WarGame(String givenName) {
        super(givenName);
        this.deck = new GroupOfCards(52);
    }

    @Override
    public void play() {
        deck.shuffle();

        ArrayList<Card> cards = deck.getCards();
        ArrayList<Player> players = getPlayers();

        for (int i = 0; i < cards.size(); i++) 
        {
            WarPlayer player = (WarPlayer) players.get(i % players.size());
            player.getHand().add(cards.get(i));
        }

        // Play the game until one player has all the cards or reached max rounds
        int round = 0;
        while (round < MAX_ROUNDS && players.size() > 2) 
        {
            System.out.println("Round " + (round + 1));

            ArrayList<Card> roundCards = new ArrayList<>();
            for (Player player : players) 
            {
                roundCards.add(((WarPlayer) player).play());
            }

            for (int i = 0; i < players.size(); i++) 
            {
                System.out.println(players.get(i).getPlayerID() + " plays " + roundCards.get(i));
            }

            int highestCardIndex = 0;
            boolean isWar = false;
            
            for (int i = 1; i < roundCards.size(); i++) 
            {
                int result = compareCards(roundCards.get(highestCardIndex), roundCards.get(i));
                if (result < 0) 
                {
                    highestCardIndex = i;
                    isWar = false;
                } 
                else if (result == 0) 
                {
                    isWar = true;
                }
            }

            if (isWar) 
            {
                System.out.println("War!");
                for (Card card : roundCards) 
                {
                    if (card != null) 
                    {
                        ((WarPlayer) players.get(highestCardIndex)).getHand().add(card);
                    }
                }
                System.out.println(players.get(highestCardIndex).getPlayerID() + " wins this war round.");
            }
            else 
            {
                for (Card card : roundCards) 
                {
                    if (card != null) 
                    {
                        ((WarPlayer) players.get(highestCardIndex)).getHand().add(card);
                    }
                }
                System.out.println(players.get(highestCardIndex).getPlayerID() + " wins this round.");
            }

            // Lost Players
            ArrayList<Player> eliminatedPlayers = new ArrayList<>();
            for (Player player : players) 
            {
                if (((WarPlayer) player).getHand().isEmpty()) 
                {
                    eliminatedPlayers.add(player);
                }
            }

            for (Player player : eliminatedPlayers) 
            {
                System.out.println(player.getPlayerID() + " has been eliminated.");
                players.remove(player);
            }

            round++;
            System.out.println();
        }

        declareWinner();
    }

    @Override
    public void declareWinner() {
        ArrayList<Player> players = getPlayers();
        System.out.println("Final Scorecard:");
        for (Player player : players) 
        {
            WarPlayer warPlayer = (WarPlayer) player;
            System.out.println(player.getPlayerID() + " has " + warPlayer.getHand().size() + " cards left.");
        }

        int maxCards = players.stream()
            .mapToInt(player -> ((WarPlayer) player).getHand().size())
            .max()
            .orElse(0);

        ArrayList<Player> winners = players.stream()
            .filter(player -> ((WarPlayer) player).getHand().size() == maxCards)
            .collect(Collectors.toCollection(ArrayList::new));

        if (winners.size() > 1) 
        {
            System.out.println("The game is a tie between:");
            for (Player player : winners) 
            {
                System.out.println(player.getPlayerID());
            }
        } 
        else 
        {
            System.out.println("The winner is " + winners.get(0).getPlayerID() + "!");
        }
    }

    public int compareCards(Card card1, Card card2) {
        if (card1 == null || card2 == null) 
            return 0;

        Map<String, Integer> rankMap = new HashMap<>();
        rankMap.put("2", 2);
        rankMap.put("3", 3);
        rankMap.put("4", 4);
        rankMap.put("5", 5);
        rankMap.put("6", 6);
        rankMap.put("7", 7);
        rankMap.put("8", 8);
        rankMap.put("9", 9);
        rankMap.put("10", 10);
        rankMap.put("Jack", 11);
        rankMap.put("Queen", 12);
        rankMap.put("King", 13);
        rankMap.put("Ace", 14);

        return Integer.compare(rankMap.get(card1.getRank()), rankMap.get(card2.getRank()));
    }

    public static void main(String[] args) {
        WarGame warGame = new WarGame("War");
        warGame.getPlayers().add(new WarPlayer("Deepika Jangra"));
        warGame.getPlayers().add(new WarPlayer("Sukhpreet Kaur"));        
        warGame.getPlayers().add(new WarPlayer("Gurnaj Kaur"));
        warGame.getPlayers().add(new WarPlayer("Prabhjot Kaur"));

        warGame.play();
    }
}
