/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.parsers;

import model.generated.Card;
import java.util.Set;
import model.generated.Cards;

/**
 *
 * @author Sasha
 */
public interface CardParser {
    
    /**
     * build card set from the xml file
     * @param fileName xml file with all required information about entities
     */
    void buildCardSet(String fileName);
    
    /**
     * Get all cards
     * @return setof cards
     */
    Cards getCards();
}
