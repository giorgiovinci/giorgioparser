package nl.elmar.parser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;
import nl.elmar.persistence.EPersist;

import org.junit.Before;
import org.junit.Test;

public class TestELoader {

    
    @Test
    public void testLoadParsingAccommodation() throws Exception{
        
        String filename = "ABADE01.xml";
        EParser eparser = new EParser();
        ELoader elmarParser = new ELoader();
        
       
        XMLStreamReader xmlr = eparser.createXMLStreamReader(filename);
        elmarParser.loadAccommodation(xmlr);
       
        
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
    public void testLoadPersistAccommodation() throws Exception{
        
        EPersist ePersist = new EPersist();
        testLoadParsingAccommodation();
        List<Accommodation> accommodations = ePersist.getAll();
        
        Accommodation accommodation1 = accommodations.get(0);
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
}
