/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class WarPlayer extends Player {
    private ArrayList<Card> hand;

    public WarPlayer(String name) {
        super(name);
        this.hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    @Override
    public Card play() {
        if (!hand.isEmpty()) 
        {
            return hand.remove(0); // Return and remove the top card from the hand
        }
        return null;
    }
}
