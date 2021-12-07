import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Version Format Class Handles parsing the XML files for international versions of Monopoly
 *
 * @version 1.0
 * @author Daniah Mohammed - 101145902
 */
public class VersionFormat extends DefaultHandler implements Serializable {

    private ArrayList<Tile> tiles;
    private ArrayList<Jail> jails;
    private Stack<String> elementStacks;
    private String currencySign;

    private String propertyName;
    private int propertyPrice;
    private String group;

    private String utilityName;
    private int utilityPrice;

    private String railRoadName;
    private int railRoadPrice;

    private Monopoly monopoly;

    /**
     * VersionFormat Constructor
     */
    public VersionFormat(Monopoly monopoly){
        this.elementStacks = new Stack<>();
        this.tiles = new ArrayList<>();
        this.jails = new ArrayList<>();
        this.monopoly = monopoly;
    }

    /**
     * Importing the XML files and assigning it to the tiles & currency sign
     * @param filename, the name of the XML file
     */
    public void importFormat(String filename){

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;
        } catch (SAXException e) {
            e.printStackTrace();
            return;
        }


        URL resource;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        resource = classLoader.getResource(filename);
        InputStream fileInputStream;

        try {
            fileInputStream = resource.openStream();
            saxParser.parse(fileInputStream, this);
        } catch (SAXException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method for tiles
     * @return list of parsed tiles from the XML
     */

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    /**
     * Getter method for currency sign
     * @return String of parsed currency sign from the XML
     */

    public String getCurrencySign() {
        return currencySign;
    }

    /**
     * Getter method for jails
     * @return list of parsed jails from the XML
     */

    public ArrayList<Jail> getJails() {
        return jails;
    }

    /**
     * Adding qName to the elementStacks
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, org.xml.sax.Attributes attributes) throws SAXException {
        elementStacks.add(qName);
    }


    /**
     * Creating the list of tiles depending on element tags
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Property")) {
            tiles.add(new Property(propertyName,propertyPrice,group));
        }else if(qName.equals("Jail")){
            Jail jail = new Jail("Jail", 1, monopoly);
            tiles.add(jail);
            jails.add(jail);
        }else if(qName.equals("RailroadTile")){
            tiles.add(new RailroadTile(railRoadName, railRoadPrice));
        }else if(qName.equals("GoToJail")){
            tiles.add(new GoToJail());
        }else if(qName.equals("UtilityTile")){
            tiles.add(new UtilityTile(utilityName, utilityPrice));
        }else if(qName.equals("GoTile")){
            tiles.add(new GoTile());
        }
        elementStacks.pop();

    }

    /**
     * Assign the characters withing the XML file to their corresponding attribute
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String input = new String(ch, start, length).trim();
        switch (elementStacks.peek()) {
            case "propertyName":
                propertyName = input;
                break;
            case "propertyPrice":
                this.propertyPrice = Integer.parseInt(input);
                break;
            case "group":
                group = new String(ch, start, length);
                break;
            case "railRoadName":
                railRoadName = input;
                break;
            case "railRoadPrice":
                railRoadPrice = Integer.parseInt(input);
                break;
            case "utilityName":
                utilityName = input;
                break;
            case "utilityPrice":
                utilityPrice = Integer.parseInt(input);
                break;
            case "currencySign":
                currencySign = input;
                break;
        }
    }
}
