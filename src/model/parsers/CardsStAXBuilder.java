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
import model.generated.Cards;
import model.generated.ObjectFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import view.View;

/**
 * StAX parser
 * @author Sasha
 */
public class CardsStAXBuilder implements CardParser {
    
    /** view object */
    private final View view = new View();
    
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
    public CardsStAXBuilder() {
        objectFactory = new ObjectFactory();
        cards = objectFactory.createCards();
        withText = EnumSet.range(CardEnum.THEMA, CardEnum.NATIONALITY);
    }
    
    /**
     * Get card set
     * @return card set
     */
    @Override
    public Cards getCards() {
        return cards;
    }
    
    /**
     * Build all cards
     * @param fileName name of the xml file that contains information about cards
     */
    @Override
    public void buildCardSet(String fileName) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        try {
            reader = factory.createXMLStreamReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement(reader.getLocalName(), reader.getAttributeValue(0));
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        characters(reader.getText().trim());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        endElement(reader.getLocalName());
                        break;
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(CardsStAXBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
    
    /**
     * This method is called at the begin of the element
     * @param localName local name of tag (variable)
     * @param attributeValue value of the attribute
     */
    private void startElement(String localName, String attributeValue) {
        switch (localName){
            case "card":
                currentCard = objectFactory.createCard();
                currentCard.setId(attributeValue);
                break;
            case "author":
                currentAuthor = objectFactory.createAuthor();
                currentAuthor.setIsAlive(Boolean.valueOf(attributeValue));
                break;
            default:
                CardEnum temp = CardEnum.valueOf(localName.toUpperCase());
                if (withText.contains(temp)) {
                    currentEnum = temp;
                }
        }
    }
    
    /**
     * This method is called in the end of the element
     * @param localName local name of the tag (variable)
     */
    private void endElement(String localName) {
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
     * This method is called in the center of the tag
     * @param tagContent contetn od the current tag
     */
    private void characters(String tagContent) {
        if (currentEnum != null) {
            switch (currentEnum) {
                case THEMA:
                    currentCard.setThema(tagContent);
                    break;
                case TYPE:
                    currentCard.setType(CardType.valueOf(tagContent.toUpperCase()));
                    break;
                case WAS_SENT:
                    currentCard.setWasSent(Boolean.valueOf(tagContent));
                    break;
                case COUNTRY:
                    currentCard.setCountry(tagContent);
                    break;
                case YEAR:
                    currentCard.setYear(Integer.parseInt(tagContent));
                    break;
                case VALUABLE:
                    currentCard.setValuable(Valuable.valueOf(tagContent.toUpperCase()));
                    break;
                case FIRST_NAME:
                    currentAuthor.setFirstName(tagContent);
                    break;
                case LAST_NAME:
                    currentAuthor.setLastName(tagContent);
                    break;
                case NATIONALITY:
                    currentAuthor.setNationality(tagContent);
                    break;
            }
        }
        currentEnum = null;
    }
}
