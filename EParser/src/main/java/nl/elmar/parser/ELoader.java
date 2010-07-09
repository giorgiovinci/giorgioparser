package nl.elmar.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

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

public class ELoader{
    private static final Logger log = Logger.getLogger(ELoader.class);
    
    private static final String ACCOMMODATION_ID = "AccommodationID";
    // Maintains the status across the different loaders
    protected Map<Statuses,Object> accommodationStatus = new HashMap<Statuses, Object>();
    private static List<String> accommodationsIds = new ArrayList<String>();
    
    private Map<String,Loader> loaders = new HashMap<String, Loader>();
    private Loader loader;
    private EPersist ePersist = new EPersist();
    
    private boolean validAccommodation;
    
    public ELoader(){
        loaders.put(ACCOMMODATION_ID, new AccommodationLoader());
        loaders.put("UnitInfo", new UnitLoader());
        loaders.put("FacilityInfo", new FacilityInfoLoader());
        loaders.put("SupplyPriceAvailabilityInfo", new PricePreLoader());
        loaders.put("UnitPrice", new PriceLoader());
        loaders.put("BoardInfo", new BoardInfoLoader());
        
        accommodationStatus.put(Statuses.MAP_UNIT, new LinkedHashMap<String, Unit>());
        accommodationStatus.put(Statuses.ACCOMMODATION_IDS, accommodationsIds);
    }
    
    @SuppressWarnings("unchecked")
    public void loadAccommodation(XMLStreamReader xmlr) throws XMLStreamException {
        for (int event = xmlr.next(); event != XMLStreamConstants.END_DOCUMENT; event = xmlr.next()) {
            if (event == XMLStreamConstants.START_ELEMENT) {
                    
                String elementName = xmlr.getLocalName();
                if(isValidAccommodation(elementName, xmlr)){
                    determinePosition(elementName, xmlr);
    
                    if(loader != null){
                        loader.load(accommodationStatus, xmlr);
                    }
                }
            }

        }
        //Save the latest accommodation loaded
        Accommodation accommodation = (Accommodation) accommodationStatus.get(Statuses.ACCOMMODATION);
        if(accommodation!=null){
            ePersist.save(accommodation);
        }
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
    
    //Avoid 2 thread to work on the same accommodation
    private boolean isValidAccommodation(String elementName, XMLStreamReader xmlr) throws XMLStreamException {
        
        if(ACCOMMODATION_ID.equals(elementName)){
            String accommodationId = xmlr.getElementText();
            
            synchronized (this) {
              accommodationStatus.put(Statuses.ACCOMMODATION_ID, accommodationId);
              if(accommodationsIds.contains(accommodationId)){
                  log.warn("Thread skip id:" + accommodationId);
                  validAccommodation = false;
              }else{
                  accommodationsIds.add(accommodationId);
                  validAccommodation = true;
                  log.warn("Thread start id:" + accommodationId);
                  if(accommodationsIds.size() > 20){
                   //   System.exit(0);
                  }
              }
            }
        }
        
        return validAccommodation;
    }



}
