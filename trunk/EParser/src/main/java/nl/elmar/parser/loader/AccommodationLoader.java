package nl.elmar.parser.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Unit;
import nl.elmar.persistence.EPersist;

public class AccommodationLoader implements Loader {

    private EPersist ePersist = new EPersist();
    
    @Override
    public void initialize(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException{
        String accommodationId = (String) accommodationStatus.get(Statuses.ACCOMMODATION_ID);;
        Accommodation accommodation = (Accommodation) accommodationStatus.get(Statuses.ACCOMMODATION);
        List<String> accommodationIds = (List<String>) accommodationStatus.get(Statuses.ACCOMMODATION_IDS);
        if(accommodation != null){
            ePersist.save(accommodation);
        }
        accommodation = new Accommodation(accommodationId);
        accommodationStatus.put(Statuses.ACCOMMODATION, accommodation );
        accommodation.setUnits(new ArrayList<Unit>());
        accommodationIds.add(accommodationId);
        
    }

    @Override
    public void load(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        Accommodation accommodation = (Accommodation) accommodationStatus.get(Statuses.ACCOMMODATION);
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
    
}
