/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.validation;

import java.io.File;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.IOException;
import view.View;

/**
 * Provide validation of the xml fale according to corresponding schema
 * @author Sasha
 */
public class XMLValidator {
    
    /** view object */
    private final View view;
    
    /**
     * Constructor
     */
    public XMLValidator() {
        view = new View();
    }
    
    /**
     * Make validation of the xml fale according to corresponding schema
     * @param xmlURI xml uri
     * @param xsdURI xsd uri
     * @return true if file is valid anf false otherwise
     */
    public boolean validate(String xmlURI, String xsdURI) {
        Source source = new StreamSource(xmlURI);
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaLocation = new File(xsdURI);
        try {
            Schema schema = schemaFactory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            validator.validate(source);
            return true;
        } catch (SAXException e) {
            view.printMessage("Sax exception in the XMLValidator: " + e.getMessage());
        } catch (IOException e) {
            view.printMessage("IO exception in the XMLValidator: " + e.getMessage());
        }
        return true;
    }
}
