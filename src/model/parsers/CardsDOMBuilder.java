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
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.View;

/**
 *
 * @author Sasha
 */
public class CardsDOMBuilder implements CardParser {
    
    /** view object */
    private View view = new View();
    
    /** object factory */
    private ObjectFactory objectFactory;
    
    /** cards set */
    private Cards cards;
    
    /** document builder */
    private DocumentBuilder docBuilder;
    
    /**
     * Constructor
     */
    public CardsDOMBuilder() {
        objectFactory = new ObjectFactory();
        cards = objectFactory.createCards();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            view.printMessage("Parser configuration error: " + e.getMessage());
        }
    }
    
    /**
     * Get all cards
     * @return setof cards
     */
    @Override
    public Cards getCards() {
        return cards;
    }
    
    /**
     * build card set from the xml file
     * @param fileName xml file with all required information about entities
     */
    @Override
    public void buildCardSet(String fileName) {
        Document document;
        try {
            document = docBuilder.parse(fileName);
            // get root element
            Element root = document.getDocumentElement();   
            
            //get child elements list
            NodeList cardList = root.getElementsByTagName("card");
            for (int i = 0; i < cardList.getLength(); i++) {
                Element cardElement = (Element) cardList.item(i);
                Card newCard = buildCard(cardElement);
                cards.getCard().add(newCard);
            }
        } catch (IOException e) {
            view.printMessage("IO exception: " + e.getMessage());
        } catch (SAXException e) {
            view.printMessage("SAX exception in CardDOMBuilder class: " + e.getMessage());
        }
    }
    
    /**
     * Build new card from the element
     * @param cardElement element from which new card will be built
     * @return new card was built from the element
     */
    private Card buildCard(Element cardElement) {
        Card newCard = objectFactory.createCard();
        newCard.setId(cardElement.getAttribute("id"));
        newCard.setThema(getElementTextContent(cardElement, "thema"));
        String type = getElementTextContent(cardElement, "type");
        newCard.setType(CardType.valueOf(type.toUpperCase()));
        boolean wasSent = Boolean.valueOf(getElementTextContent(cardElement, "was_sent"));
        newCard.setWasSent(wasSent);
        newCard.setCountry(getElementTextContent(cardElement, "country"));
        int year = Integer.parseInt(getElementTextContent(cardElement, "year"));
        newCard.setYear(year);
        String valuable = getElementTextContent(cardElement, "valuable");
        newCard.setValuable(Valuable.valueOf(valuable.toUpperCase()));
        
        // get athors if they are available
        NodeList authorList = cardElement.getElementsByTagName("author");
        for (int i = 0; i < authorList.getLength(); i++) {
            Element authorElement = (Element) authorList.item(i);
            Author newAuthor = buildAuthor(authorElement);
            newCard.getAuthor().add(newAuthor);
        }
        return newCard;
    }
    
    /**
     * build author from the element
     * @param authorElement element from which new author will be built
     * @return new author
     */
    private Author buildAuthor(Element authorElement) {
        Author newAuthor = objectFactory.createAuthor();
        String isAlive = authorElement.getAttribute("is-alive");
        newAuthor.setIsAlive(Boolean.valueOf(isAlive));
        newAuthor.setFirstName(getElementTextContent(authorElement, "first_name"));
        newAuthor.setLastName(getElementTextContent(authorElement, "last_name"));
        newAuthor.setNationality(getElementTextContent(authorElement, "nationality"));
        return newAuthor;
    }
    
    /**
     * Get element text contetn by the element name
     * @param cardElement element of card 
     * @param elementName name of required element from which text will be returned
     * @return text content from required element
     */
    private String getElementTextContent(Element cardElement, String elementName) {
        NodeList elementsList = cardElement.getElementsByTagName(elementName);
        Node node = elementsList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
