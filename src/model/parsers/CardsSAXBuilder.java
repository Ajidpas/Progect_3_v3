/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.parsers;

import java.io.IOException;
import model.generated.Cards;
import model.generated.ObjectFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import view.View;

/**
 *
 * @author Sasha
 */
public class CardsSAXBuilder implements CardParser {
    
    /** view object */
    private final View view = new View();
    
    /** object factory */
    private ObjectFactory objectFactory;
    
    /** set of cards */
    private Cards cards;
    
    /** card handler */
    private CardsSaxHandler ch;
    
    /** xml reader */
    private XMLReader xmlReader;
    
    /**
     * Constructor
     */
    public CardsSAXBuilder() {
        objectFactory = new ObjectFactory();
        cards = objectFactory.createCards();
        // sax analisator creation
        ch = new CardsSaxHandler();
        try {
            xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setContentHandler(ch);
        } catch (SAXException e) {
            view.printMessage("sax parser error: " + e.getMessage());
        }
    }
    
    /**
     * get set of cards
     * @return set of cards
     */
    @Override
    public Cards getCards() {
        return cards;
    }
    
    /**
     * build set of cards
     * @param fileName entire name of xml file which contains information about cards
     */
    @Override
    public void buildCardSet(String fileName) {
        try {
            xmlReader.parse(fileName);
        } catch (SAXException e) {
            view.printMessage("sax parser error: " + e.getMessage());
        } catch (IOException e) {
            view.printMessage("IO error: " + e.getMessage());
        }
        cards = ch.getCards();
    }
}
