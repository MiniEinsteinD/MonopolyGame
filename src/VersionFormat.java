import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class VersionFormat extends DefaultHandler {
    private ArrayList<Tile> tiles;



    public VersionFormat(String currencySign){

    }
    public void importFormat(String filename, String currencySign){
        Monopoly.currencySign = currencySign;


    }

    /*

    @Override
    public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, org.xml.sax.Attributes attributes) throws SAXException {
        xmlElements.push(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        xmlElements.pop();
        if (qName.equals("BuddyInfo")) {
            addBuddy(new BuddyInfo(previousBuddyName, previousBuddyAddress, previousBuddyPhoneNumber));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch (xmlElements.peek()) {
            case "name" :
                previousBuddyName = new String(ch, start, length);
                break;
            case "address" :
                previousBuddyAddress = new String(ch, start, length);
                break;
            case "phoneNumber" :
                previousBuddyPhoneNumber = new String(ch, start, length);
        }
    }

     */
}
