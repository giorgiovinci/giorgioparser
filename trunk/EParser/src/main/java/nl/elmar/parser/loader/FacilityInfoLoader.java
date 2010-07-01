package nl.elmar.parser.loader;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.FacilityInfo;
import nl.elmar.model.Unit;

public class FacilityInfoLoader implements Loader {

    @Override
    public void initialize(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        Unit unit = (Unit) accommodationStatus.get(Statuses.UNIT);
        accommodationStatus.put(Statuses.FACILITY_INFO, new FacilityInfo());
        unit.setFacilityInfos(new ArrayList<FacilityInfo>());
    }

    @Override
    public void load(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        FacilityInfo facilityInfo = (FacilityInfo) accommodationStatus.get(Statuses.FACILITY_INFO);
        if (xmlr.getLocalName().equalsIgnoreCase("Description")) {
            facilityInfo.setDescription(xmlr.getElementText());
        }
        if (xmlr.getLocalName().equalsIgnoreCase("FacilityType")) {
            facilityInfo.setType(xmlr.getElementText());
        }
    }

}
