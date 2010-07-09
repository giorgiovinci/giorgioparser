package nl.elmar.parser;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;
import nl.elmar.parser.loader.Statuses;
import nl.elmar.persistence.EPersist;

import org.junit.Test;

public class TestELoader {

    
    @Test
    public void testLoadParsingAccommodation() throws Exception{
        
        String filename = "ABADE01.xml";
        
        ELoader elmarParser = new ELoader();
       
        XMLStreamReader xmlr = EParser.createXMLStreamReader(filename);
        elmarParser.loadAccommodation(xmlr);
       
        
        Accommodation accommodation1 = (Accommodation) elmarParser.accommodationStatus.get(Statuses.ACCOMMODATION);
        assertEquals("ACADEBEA",accommodation1.getId());
        assertEquals("Beau-Site",accommodation1.getName());
        assertEquals("Zwitserland",accommodation1.getCountry());
        assertEquals("Adelboden",accommodation1.getPlace());

        Unit unit11 = accommodation1.getUnits().get(0);
        assertEquals("1PK  BDWC",unit11.getId());
        assertEquals("1-Persoonskamer",unit11.getDescription());

        Price price111 = unit11.getPrices().get(0);
        assertEquals("480,00",price111.getAmount());

        Unit unit12 = accommodation1.getUnits().get(1);
        assertEquals("2PK2 BDWC",unit12.getId());
        assertEquals("2-Persoonskamer",unit12.getDescription());
        Price price121 = unit12.getPrices().get(0);
        assertEquals("394,00",price121.getAmount());
        
        Unit unit13 = accommodation1.getUnits().get(2);
        assertEquals("2PK3 BDWCBL",unit13.getId());
        assertEquals("Comfort",unit13.getDescription());
        Price price131 = unit13.getPrices().get(0);
        assertEquals("422,00",price131.getAmount());
        Price price132 = unit13.getPrices().get(1);
        assertEquals("350,00",price132.getAmount());
        
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
        assertEquals("1-Persoonskamer",unit11.getDescription());

        Price price111 = unit11.getPrices().get(0);
        assertEquals("480,00",price111.getAmount());

        Unit unit12 = accommodation1.getUnits().get(1);
        assertEquals("2PK2 BDWC",unit12.getId());
        assertEquals("2-Persoonskamer",unit12.getDescription());

                
    }
}
