/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import model.Model;
import model.generated.Cards;
import model.generated.Card;
import model.parsers.ParserFactory.ParserEnum;
import view.View;

/**
 *
 * @author Sasha
 */
public class Controller {
    
    /** view pbject */
    private final View view;
    
    /** model object */
    private final Model model;
    
    /**
     * Controller
     */
    public Controller() {
        view = new View();
        model = new Model();
    }
    
    /**
     * Programm starts with this point
     */
    public void start() {
        String xmlURI = "src/controller/xml/OldCards.xml";
        String xsdURI = "src/controller/xml/OldCardsSchema.xsd";
        performValidation(xmlURI, xsdURI);
        performParsing(xmlURI);
    }
    
    /**
     * Make validation of xml acording to xsd cshema
     * @param xmlURI xml uri
     * @param xsdURI xsd uri
     */
    public void performValidation(String xmlURI, String xsdURI) {
        boolean isValid = model.validate(xmlURI, xsdURI);
        if (isValid) {
            view.printMessage("Document \"" + xmlURI + "\" is valid!");
        } else {
            view.printMessage("Document \"" + xmlURI + "\" is not valid!");
        }
    }
    
    /**
     * Card set building with help of three different parsers
     * @param xmlURI xml uri
     */
    public void performParsing(String xmlURI) {
        Cards cards;
        EnumSet parsers = EnumSet.range(ParserEnum.DOM_PARSER, ParserEnum.STAX_PARSER);
        for (Iterator it = parsers.iterator(); it.hasNext();) {
            ParserEnum parser = (ParserEnum) it.next();
            view.printMessage("\nParsing with " + parser.toString() + ":");
            cards = model.buildCardSet(xmlURI, parser);
            Collections.sort(cards.getCard(), new Comparator<Card>(){
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            view.printCards(cards);
        }
    }
}
