package nl.elmar.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Unit;
import nl.elmar.parser.loader.AccommodationLoader;
import nl.elmar.parser.loader.BoardInfoLoader;
import nl.elmar.parser.loader.FacilityInfoLoader;
import nl.elmar.parser.loader.Loader;
import nl.elmar.parser.loader.PriceLoader;
import nl.elmar.parser.loader.PricePreLoader;
import nl.elmar.parser.loader.Statuses;
import nl.elmar.parser.loader.UnitLoader;
import nl.elmar.persistence.EPersist;

enum Tag {
    AccommodationID, SupplyPriceAvailabilityInfo, UnitInfo, FacilityInfo, BoardInfo, UnitPrice
}


public class ELoader {
    // Maintains the status across the different loaders
    protected Map<Statuses,Object> accommodationStatus = new HashMap<Statuses, Object>();
    private Map<String,Loader> loaders = new HashMap<String, Loader>();
    private Loader loader;
    private EPersist ePersist = new EPersist();
    
    public ELoader(){
        
        loaders.put("AccommodationID", new AccommodationLoader());
        loaders.put("UnitInfo", new UnitLoader());
        loaders.put("FacilityInfo", new FacilityInfoLoader());
        loaders.put("SupplyPriceAvailabilityInfo", new PricePreLoader());
        loaders.put("UnitPrice", new PriceLoader());
        loaders.put("BoardInfo", new BoardInfoLoader());
        
        accommodationStatus.put(Statuses.MAP_UNIT, new LinkedHashMap<String, Unit>());
        accommodationStatus.put(Statuses.ACCOMMODATION_IDS, new ArrayList<String>());
    }
    
    @SuppressWarnings("unchecked")
    public void loadAccommodation(XMLStreamReader xmlr) throws XMLStreamException {

        for (int event = xmlr.next(); event != XMLStreamConstants.END_DOCUMENT; event = xmlr.next()) {
            if (event == XMLStreamConstants.START_ELEMENT) {

                String elementName = xmlr.getLocalName();
                determinePosition(elementName, xmlr);

                if(loader != null){
                    loader.load(accommodationStatus, xmlr);
                }
            }

        }
        Accommodation accommodation = (Accommodation) accommodationStatus.get(Statuses.ACCOMMODATION);
        ePersist.save(accommodation);
    }

    /**
     * Load the right loader depending of the position in the file
     * 
     * @param elementName
     * @param xmlr
     * @throws XMLStreamException
     */
    private void determinePosition(String elementName, XMLStreamReader xmlr) throws XMLStreamException {
        Loader loaderTmp = loaders.get(elementName);
        if(loaderTmp != null){
            loader = loaderTmp;
            loader.initialize(accommodationStatus, xmlr);
        }
    }

}
