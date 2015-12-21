/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.parsers;

/**
 * Parser factory
 * @author Sasha
 */
public class ParserFactory {
    
    /**
     * Factory methor to create required parser
     * @param parser type of required parser
     * @return required parser object
     */
    public CardParser getParser(ParserEnum parser) {
        if (parser == null) {
            return null;
        }
        switch (parser) {
            case DOM_PARSER:
                return new CardsDOMBuilder();
            case SAX_PARSER:
                return new CardsSAXBuilder();
            case STAX_PARSER:
                return new CardsStAXBuilder();
            default:
                return null;
        }
    }
            
    /**
     *
     * @author Sasha
     */
    public enum ParserEnum {
        DOM_PARSER,
        SAX_PARSER,
        STAX_PARSER
    }
}
