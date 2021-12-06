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
 *
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


    public VersionFormat(){
        this.elementStacks = new Stack<>();
        this.tiles = new ArrayList<>();
    }
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

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public String getCurrencySign() {
        return currencySign;
    }


    @Override
    public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, org.xml.sax.Attributes attributes) throws SAXException {
        elementStacks.add(qName);
    }

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
