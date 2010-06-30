package nl.elmar.parser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;

import org.junit.Test;

public class TestELoader {

    @Test
    public void testLoadAccommodation(){
        
        String filename = "ABADE01.xml";
        EParser eparser = new EParser();
        ELoader elmarParser = new ELoader();
        
        try {
            XMLStreamReader xmlr = eparser.createXMLStreamReader(filename);
            elmarParser.loadAccommodation(xmlr);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        
        
        Accommodation accommodation1 = elmarParser.accommodation;
        assertEquals("ACADEBEA",accommodation1.getId());
        assertEquals("Beau-Site",accommodation1.getName());

        Unit unit11 = accommodation1.getUnits().get(0);
        assertEquals("1PK  BDWC",unit11.getId());
        //assertEquals("1-Persoonskamer",unit11.getDescription());

        Price price111 = unit11.getPrices().get(0);
        System.out.println("480,00".equals(price111.getAmount()));

        Unit unit12 = accommodation1.getUnits().get(1);
        System.out.println("2PK2 BDWC".equals(unit12.getId()));
//        System.out.println("2-Persoonskamer".equals(unit12.getDescription()));

        
    }
    
    @Test
    public void testPersistAccommodation(){
        
        String filename = "JI631.xml";
        EParser eparser = new EParser();
        ELoader elmarParser = new ELoader();
        
        try {
            XMLStreamReader xmlr = eparser.createXMLStreamReader(filename);
            elmarParser.loadAccommodation(xmlr);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        
        
        Accommodation accommodation1 = elmarParser.accommodation;
        
         
        
    }
}
