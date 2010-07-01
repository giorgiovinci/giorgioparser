package nl.elmar.parser.loader;

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Unit;

public class PricePreLoader implements Loader {

    @Override
    public void initialize(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        // No operation is necessary here
    }

    @Override
    /**
     * Load the first unitId where the prices must be attached
     */
    public void load(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        //Set in the status map the unit where the following prices must be attached
        if (xmlr.getLocalName().equalsIgnoreCase("UnitID")) {
            Map<String, Unit> mapUnits = (Map<String, Unit>) accommodationStatus.get(Statuses.MAP_UNIT);
            accommodationStatus.put(Statuses.UNIT_PRICE, mapUnits.get(xmlr.getElementText()));
        }
    }

}
