/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.parsers;

import model.generated.Author;
import model.generated.Card;
import model.generated.CardType;
import model.generated.Valuable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import model.generated.Cards;
import model.generated.ObjectFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Sasha
 */
public class CardsSaxHandler extends DefaultHandler {
    
    /** object factory */
    private ObjectFactory objectFactory;
    
    /** set of cards */
    private Cards cards;
    
    /** card what parser are working with */
    private Card currentCard;
    
    /** author what parser are working with */
    private Author currentAuthor;
    
    /** tag what parser are working with */
    private CardEnum currentEnum;
    
    /** range of tags with simple type data */
    private EnumSet<CardEnum> withText;
    
    /**
     * Constructor
     */
    public CardsSaxHandler() {
        objectFactory = new ObjectFactory();
        cards = objectFactory.createCards();
        withText = EnumSet.range(CardEnum.THEMA, CardEnum.NATIONALITY);
    }
    
    /**
     * Get set of cards
     * @return set of cards
     */
    public Cards getCards() {
        return cards;
    }
    
    /**
     * method that is called in the start of each element of xml file
     * @param uri file uri
     * @param localName element local name
     * @param qName element name 
     * @param attrs attributes of current element
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch (localName){
            case "card":
                currentCard = objectFactory.createCard();
                currentCard.setId(attrs.getValue(0));
                break;
            case "author":
                currentAuthor = objectFactory.createAuthor();
                currentAuthor.setIsAlive(Boolean.valueOf(attrs.getValue(0)));
                break;
            default:
                CardEnum temp = CardEnum.valueOf(localName.toUpperCase());
                if (withText.contains(temp)) {
                    currentEnum = temp;
                }
        }
    }
    
    /**
     * method that is called in the end of each element of xml file
     * @param uri file uri
     * @param localName element local name
     * @param qName element name
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (localName) {
            case "card":
                cards.getCard().add(currentCard);
                break;
            case "author":
                currentCard.getAuthor().add(currentAuthor);
                break;
        }
    }
    
    /**
     * Array of characters of the element
     * @param ch array of chars
     * @param start start position of the valu of the element
     * @param length length of the element value
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case THEMA:
                    currentCard.setThema(s);
                    break;
                case TYPE:
                    currentCard.setType(CardType.valueOf(s.toUpperCase()));
                    break;
                case WAS_SENT:
                    currentCard.setWasSent(Boolean.valueOf(s));
                    break;
                case COUNTRY:
                    currentCard.setCountry(s);
                    break;
                case YEAR:
                    currentCard.setYear(Integer.parseInt(s));
                    break;
                case VALUABLE:
                    currentCard.setValuable(Valuable.valueOf(s.toUpperCase()));
                    break;
                case FIRST_NAME:
                    currentAuthor.setFirstName(s);
                    break;
                case LAST_NAME:
                    currentAuthor.setLastName(s);
                    break;
                case NATIONALITY:
                    currentAuthor.setNationality(s);
                    break;
            }
        }
        currentEnum = null;
    }
}
