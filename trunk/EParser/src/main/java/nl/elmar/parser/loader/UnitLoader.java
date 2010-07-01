package nl.elmar.parser.loader;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;

public class UnitLoader implements Loader {

    @Override
    public void initialize(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        
        Unit unit = new Unit();
        unit.setPrices(new ArrayList<Price>());
        accommodationStatus.put(Statuses.UNIT, unit);
    }

    @Override
    public void load(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        Accommodation accommodation = (Accommodation) accommodationStatus.get(Statuses.ACCOMMODATION);
        Unit unit = (Unit) accommodationStatus.get(Statuses.UNIT);
        Map<String, Unit> mapUnits = (Map<String, Unit>) accommodationStatus.get(Statuses.MAP_UNIT);
        
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

}
