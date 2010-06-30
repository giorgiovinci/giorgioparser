package nl.elmar.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;
import nl.elmar.persistence.EPersist;

enum Tag {AccommodationID,SupplyPriceAvailabilityInfo,UnitInfo}

public class ELoader {

    EPersist ePersist = new EPersist(); 
    
    List<String> accommodationIds = new ArrayList<String>();
    Map<String, Unit> mapUnits = new LinkedHashMap<String, Unit>();
    Accommodation accommodation = null;
    Unit unit = null;
    Price price = null; 

    boolean loadAccommodation =false;
    
    boolean baccommodation = false;
    boolean bunit = false;
    boolean bprice = false;
    
    public void loadAccommodation(XMLStreamReader xmlr) throws XMLStreamException{
        
        for (int event = xmlr.next(); event != XMLStreamConstants.END_DOCUMENT; event = xmlr.next()) {
            if (event == XMLStreamConstants.START_ELEMENT) {

               String element = xmlr.getLocalName();
               try{
	               switch (Tag.valueOf(element)) {
	                	case AccommodationID:
	                        String accommodationId = xmlr.getElementText();
	                        //skip this accommodation if already loaded TODO: Remove or complete syncronized block
	                        if(!accommodationIds.contains(accommodationId)){
	                            loadAccommodation = true;
	                        }
	                        baccommodation = true;
	                        persistPrevious(accommodation);
	                        resetAccommodation(accommodationId);
	                        break;
		                case UnitInfo:
	                        bunit = true;
	                        baccommodation = false;
	                        unit = new Unit();
	                        unit.setPrices(new ArrayList<Price>());
	                        break;
		                case SupplyPriceAvailabilityInfo:
	                        bprice = true;
	                        break;
	                }
               }catch(IllegalArgumentException iae){ /*not relevant tag FIXME:This exception is thrown also from persistence. */  }
               
	                if (baccommodation) {
	                    loadAccommodation(xmlr, accommodation);
	                }
	                if (bunit) {
	                   loadUnits(xmlr, accommodation, unit, mapUnits);
	                }
	                if (bprice) {
	                    loadPrices(xmlr, mapUnits);
	                }
               }   

	           if (event == XMLStreamConstants.END_ELEMENT) {
	
	                String element = xmlr.getLocalName();
	                try{
		                switch (Tag.valueOf(element)) {
		                	case UnitInfo:
		                        bunit = false;
		                        break;
		                	case SupplyPriceAvailabilityInfo:
		                        bprice = false;
		                        break;
		                }
	                }catch(IllegalArgumentException iae){ /*not relevant tag*/ }
	           }
        
        }//END FOR   
        
        persistPrevious(accommodation);
    }
    
    
    
    /**
     * For each new accomodationId we reset the field
     * <code>accommodation</code>
     * @param accommodationId
     */
	private void resetAccommodation(String accommodationId) {
        accommodation = new Accommodation(accommodationId);
        accommodation.setUnits(new ArrayList<Unit>());
        accommodationIds.add(accommodationId);
    }

    private void loadAccommodation(XMLStreamReader xmlr, Accommodation accomodation) throws XMLStreamException {
        if (xmlr.getLocalName().equalsIgnoreCase("Name")) {
            accomodation.setName(xmlr.getElementText());
        }
    }

    private void loadUnits(XMLStreamReader xmlr, Accommodation accomodation, Unit unit, Map<String, Unit> mapUnits) throws XMLStreamException {

        if (xmlr.getLocalName().equalsIgnoreCase("UnitID")) {
            String unitId = xmlr.getElementText();
            accomodation.getUnits().add(unit);
            mapUnits.put(unitId, unit);
            unit.setId(unitId);
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Description")) {
            unit.setDescription(xmlr.getElementText());
        }
    }

    private void loadPrices(XMLStreamReader xmlr, Map<String, Unit> mapUnits) throws XMLStreamException {

        if (xmlr.getLocalName().equalsIgnoreCase("UnitID")) {
            price = new Price();
            Unit unit = mapUnits.get(xmlr.getElementText());
            unit.getPrices().add(price);
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Price")) {
            price.setCurrency(xmlr.getAttributeValue(null, "Currency"));
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Price")) {
            price.setAmount(xmlr.getElementText());
        }
    }
    
    private void persistPrevious(Accommodation accommodation) {
        if(accommodationIds.size() > 0){
            ePersist.save(accommodation);
        }
    }

}
