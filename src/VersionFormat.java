import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Version Format Class Handles parsing the XML files for international versions of Monopoly
 *
 * @version 1.0
 * @author Daniah Mohammed - 101145902
 */
public class VersionFormat extends DefaultHandler {

    private ArrayList<Tile> tiles;
    private Stack<String> elementStacks;
    private String currencySign;

    private String propertyName;
    private int propertyPrice;
    private String group;

    private String utilityName;
    private int utilityPrice;

    private String railRoadName;
    private int railRoadPrice;

    /**
     * VersionFormat Constructor
     */
    public VersionFormat(){
        this.elementStacks = new Stack<>();
        this.tiles = new ArrayList<>();
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

        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(filename);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            saxParser.parse(fileInputStream, this);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            tiles.add(new Jail("Jail", 1, new Monopoly()));
        }else if(qName.equals("RailroadTile")){
            tiles.add(new RailroadTile(railRoadName, railRoadPrice));
        }else if(qName.equals("GoToJail")){
            tiles.add(new GoToJail());
        }else if(qName.equals("UtilityTile")){
            tiles.add(new UtilityTile(utilityName, utilityPrice));
        }else if(qName.equals("GoTile")){
            tiles.add(new GoTile());
        }

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
        int price = Integer.parseInt(new String(ch, start, length));
        switch (elementStacks.peek()) {
            case "propertyName":
                propertyName = new String(ch, start, length);
                break;
            case "propertyPrice":
                this.propertyPrice = price;
                break;
            case "group":
                group = new String(ch, start, length);
                break;
            case "railRoadName":
                railRoadName = new String(ch, start, length);
                break;
            case "railRoadPrice":
                railRoadPrice = price;
                break;
            case "utilityName":
                utilityName = new String(ch, start, length);
                break;
            case "utilityPrice":
                utilityPrice = price;
                break;
            case "currencySign":
                currencySign = new String(ch, start, length);
                break;
        }
    }
}
