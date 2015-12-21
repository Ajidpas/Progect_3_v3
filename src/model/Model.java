/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import model.generated.Cards;
import model.parsers.CardParser;
import model.parsers.ParserFactory;
import model.parsers.ParserFactory.ParserEnum;
import model.validation.XMLValidator;

/**
 * Model
 * @author Sasha
 */
public class Model {
    
    /**
     * Make validation of the xml fale according to corresponding schema
     * @param xmlURI xml uri
     * @param xsdURI xsd uri
     * @return true if file is valid anf false otherwise
     */
    public boolean validate(String xmlURI, String xsdURI) {
        XMLValidator validator = new XMLValidator();
        return validator.validate(xmlURI, xsdURI);
    }
    
    /**
     * Create set of all cards basics on the file with path fileName by concrete parser
     * @param fileName entire xml file path 
     * @param concreteParser parser that used for cards building
     * @return cards set
     */
    public Cards buildCardSet(String fileName, ParserEnum concreteParser) {
        ParserFactory parserFactory = new ParserFactory();
        CardParser parser = parserFactory.getParser(concreteParser);
        parser.buildCardSet(fileName);
        return parser.getCards();
    }
    
}
