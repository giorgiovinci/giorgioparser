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

public class ELoader {

    List<String> accommodationIds = new ArrayList<String>();
    Map<String, Unit> mapUnits = new LinkedHashMap<String, Unit>();
    Accommodation accommodation = null;
    Unit unit = null;
    Price price = null; 

    final static List<String> validTags = new ArrayList<String>();
    static{
        validTags.add("AccommodationID");
        validTags.add("SupplyPriceAvailabilityInfo");
        validTags.add("UnitInfo");
    }
    
    boolean loadAccommodation =false;
    
    boolean baccommodation = false;
    boolean bunit = false;
    boolean bprice = false;
    
    public void loadAccommodation(XMLStreamReader xmlr) throws XMLStreamException{
        
        for (int event = xmlr.next(); event != XMLStreamConstants.END_DOCUMENT; event = xmlr.next()) {
            if (event == XMLStreamConstants.START_ELEMENT) {

                String element = xmlr.getLocalName();
                if (validTags.contains(element)) {
                    if ("AccommodationID".equals(element)) {
                        //skip this accommodation if already loaded
                        String accommodationId = xmlr.getElementText();
                        if(!accommodationIds.contains(accommodationId)){
                            loadAccommodation = true;
                        }
                        baccommodation = true;
                        persistPrevious(accommodation);
                        if(accommodation != null) break; //TODO: to remove!!!
                        accommodation = new Accommodation(accommodationId);
                    }
                    if ("UnitInfo".equals(element)) {
                        bunit = true;
                        baccommodation = false;
                        unit = new Unit();
                    }
                    if ("SupplyPriceAvailabilityInfo".equals(element)) {
                        bprice = true;
                        price = new Price();
                    }
                }

                if (baccommodation) {
                    loadAccommodation(xmlr, accommodation);
                }
                if (bunit) {
                    loadUnits(xmlr, accommodation, unit, mapUnits);
                }
                if (bprice) {
                    loadPrices(xmlr, mapUnits, price);
                }
            }   
                
            if (event == XMLStreamConstants.END_ELEMENT) {

                String element = xmlr.getLocalName();
                if (validTags.contains(element)) {
                    if ("UnitInfo".equals(element)) {
                        bunit = false;
                    }
                    if ("SupplyPriceAvailabilityInfo".equals(element)) {
                        bprice = false;
                    }
                }
            }
        
        }   
        
    }
    
    
    
    
    private static void loadAccommodation(XMLStreamReader xmlr, Accommodation accomodation) throws XMLStreamException {
        if (xmlr.getLocalName().equalsIgnoreCase("Name")) {
            accomodation.setName(xmlr.getElementText());
        }
    }

    private static void loadUnits(XMLStreamReader xmlr, Accommodation accomodation, Unit unit, Map<String, Unit> mapUnits) throws XMLStreamException {

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

    private static void loadPrices(XMLStreamReader xmlr, Map<String, Unit> mapUnits, Price price) throws XMLStreamException {

        if (xmlr.getLocalName().equalsIgnoreCase("UnitID")) {
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
    
    private static void persistPrevious(Accommodation accommodation) {
        // TODO Auto-generated method stub
        
    }

}
