package nl.elmar.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.FacilityInfo;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;
import nl.elmar.persistence.EPersist;

enum Tag {AccommodationID,SupplyPriceAvailabilityInfo,UnitInfo, FacilityInfo, BoardInfo}

public class ELoader {

    EPersist ePersist = new EPersist(); 
    
    List<String> accommodationIds = new ArrayList<String>();
    Map<String, Unit> mapUnits = new LinkedHashMap<String, Unit>();
    
    Accommodation accommodation = null;
    Unit unit = null;
    Price price = null; 
    FacilityInfo facilityInfo = null;
    
    boolean loadAccommodation =false;
    
    boolean baccommodation = false;
    boolean bunit = false;
    boolean bprice = false;
    boolean bfacilityInfo = false;

    private boolean bboardInfo;
    
    public void loadAccommodation(XMLStreamReader xmlr) throws XMLStreamException{
        
        for (int event = xmlr.next(); event != XMLStreamConstants.END_DOCUMENT; event = xmlr.next()) {
            if (event == XMLStreamConstants.START_ELEMENT) {

               String element = xmlr.getLocalName();
               try{
	               switch (Tag.valueOf(element)) {
	                	case AccommodationID:
	                        String accommodationId = xmlr.getElementText();
	                        persistPrevious(accommodation);
	                        resetAccommodation(accommodationId);
	                        baccommodation = true;
                            //skip this accommodation if already loaded TODO: Remove or complete syncronized block
                            if(!accommodationIds.contains(accommodationId)){
                                loadAccommodation = true;
                            }
	                        break;
		                case UnitInfo:
		                    resetBooleans();
	                        bunit = true;
	                        unit = new Unit();
	                        unit.setPrices(new ArrayList<Price>());
	                        break;
		                case FacilityInfo:
		                    resetBooleans();
		                    bfacilityInfo = true;
		                    facilityInfo = new FacilityInfo();
		                    unit.setFacilityInfos(new ArrayList<FacilityInfo>());
		                    break;
		                case BoardInfo:
		                    resetBooleans();
		                    bboardInfo = true;
		                    break;
		                case SupplyPriceAvailabilityInfo:
		                    resetBooleans();
	                        bprice = true;
	                        break;
		                
	                }
               }catch(IllegalArgumentException iae){ /*not relevant tag FIXME:This exception is thrown also from persistence. */  }
               
	                if (baccommodation) {
	                    loadAccommodationDetails(xmlr);
	                }
	                if (bunit) {
	                   loadUnits(xmlr);
	                }
                    if (bfacilityInfo) {
                       loadFacilityInfo(xmlr);
                    }
	                if (bprice) {
	                    loadPrices(xmlr);
	                }
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
        
        resetBooleans();
    }

    private void resetBooleans() {
        
        baccommodation = false;
        bunit = false;
        bprice = false;
        bfacilityInfo = false;
    }	
	
    private void loadAccommodationDetails(XMLStreamReader xmlr) throws XMLStreamException {
        if (xmlr.getLocalName().equalsIgnoreCase("Name")) {
            accommodation.setName(xmlr.getElementText());
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Country")) {
            accommodation.setCountry(xmlr.getElementText());
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Place")) {
            accommodation.setPlace(xmlr.getElementText());
        }
    }

    private void loadUnits(XMLStreamReader xmlr) throws XMLStreamException {

        if (xmlr.getLocalName().equalsIgnoreCase("UnitID")) {
            String unitId = xmlr.getElementText();
            accommodation.getUnits().add(unit);
            mapUnits.put(unitId, unit);
            unit.setId(unitId);
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Description")) {
            unit.setDescription(xmlr.getElementText());
        }
    }

    private void loadFacilityInfo(XMLStreamReader xmlr) throws XMLStreamException{
        if (xmlr.getLocalName().equalsIgnoreCase("Description")) {
            facilityInfo.setDescription(xmlr.getElementText());
        }
        if (xmlr.getLocalName().equalsIgnoreCase("FacilityType")) {
            facilityInfo.setType(xmlr.getElementText());
        }
    }
    
    private void loadPrices(XMLStreamReader xmlr) throws XMLStreamException {

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
