/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import model.generated.Author;
import model.generated.Card;
import model.generated.Cards;

/**
 * View
 * @author Sasha
 */
public class View {
    
    /**
     * print some message
     * @param s text of message
     */
    public void printMessage(String s) {
        System.out.println(s);
    }
    
    /**
     * print all created cards
     * @param cards all cards
     */
    public void printCards(Cards cards) {
        if (cards == null) {
            return;
        }
        System.out.println("Card list:");
        for (Card card : cards.getCard()) {
            System.out.println("id: " + card.getId() + ", thema: " + card.getThema() + 
                    ", type: " + card.getType() + ", was sent: " + card.isWasSent() + 
                    ", country: " + card.getCountry() + ", year: " + card.getYear() + 
                    ", valuable: " + card.getValuable());
            System.out.println("Authors: ");
            List<Author> authors = card.getAuthor();
            if (authors.size() < 1) {
                System.out.println("No authors available.");
            } else {
                for (Author author : authors) {
                    System.out.println("    Name: " + author.getFirstName() + 
                            ", last name: " + author.getLastName() + 
                            ", nationality: " + author.getNationality());
                }    
            }
            System.out.println("--------------------------");
        }
    }
}
