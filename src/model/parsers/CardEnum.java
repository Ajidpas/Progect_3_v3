/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.parsers;

/**
 * Enum class with al tags
 * @author Sasha
 */
public enum CardEnum {
    CARDS("cards"),
    ID("id"),
    CARD("card"),
    
    THEMA("thema"),
    TYPE("type"),
    WAS_SENT("was-sent"),
    COUNTRY("country"),
    YEAR("year"),
    VALUABLE("valuable"),
    IS_ALIVE("is-alive"),
    FIRST_NAME("firs-tname"),
    LAST_NAME("last-name"),
    NATIONALITY("nationality"),
    
    AUTHOR("author");
    
    /** string value of the tag name */
    String value;
    
    /**
     * Constructor
     * @param value 
     */
    private CardEnum(String value) {
        this.value = value;
    }
    
    /**
     * get string variable 
     * @return string variable
     */
    public String getValue() {
        return value;
    }
}
