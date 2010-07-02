package nl.elmar.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.elmar.model.Accommodation;
import nl.elmar.model.FacilityInfo;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;
import nl.elmar.persistence.EPersist;

import org.junit.Test;

public class TestEPersistence {

    EPersist ePersist = new EPersist();
    
    
	@Test
	public void testAccommodationPersistence(){
	
	    
	    Price price = new Price();
	    price.setAmount("100");
	    List<Price> listPrice = new ArrayList<Price>();
	    listPrice.add(price);
	    
	    Unit unit = new Unit();
	    unit.setId("1");
	    unit.setDescription("desc");
	    List<Unit> listUnit = new ArrayList<Unit>();
	    listUnit.add(unit);
	    
	    FacilityInfo facilityInfo = new FacilityInfo();
	    facilityInfo.setDescription("facilityInfo");
	    
	    List<FacilityInfo> facilityInfos = new ArrayList<FacilityInfo>();
	    facilityInfos.add(facilityInfo);
	    
	    unit.setPrices(listPrice);
	    unit.setFacilityInfos(facilityInfos);
	    
	    Accommodation acc = new Accommodation();
	    acc.setId("1");
	    acc.setName("test");
	    acc.setUnits(listUnit);
	    ePersist.save(acc); 
	    
	    List<Accommodation> accommodations = ePersist.getAll();
	    //AccommodPERSIST, CascadeType.MERGEation check
	    Accommodation a = accommodations.get(0);
    	assertEquals("1", a.getId());
    	assertEquals("test", a.getName());
    	
    	//Unit check
	    Unit u = a.getUnits().get(0);
	    assertEquals("1", u.getId());
	    assertEquals("desc", u.getDescription());
	    
	    //Price check
	    Price p = unit.getPrices().get(0);
        assertEquals("100", p.getAmount());
        
        FacilityInfo f = unit.getFacilityInfos().get(0);
        assertEquals("facilityInfo", f.getDescription());
        
	    System.out.println(accommodations);
	}
    
}
