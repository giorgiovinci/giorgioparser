package nl.elmar.parser.loader;

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.elmar.model.Price;
import nl.elmar.model.Unit;

public class PriceLoader implements Loader {

    @Override
    public void initialize(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        Unit unitPrice = (Unit) accommodationStatus.get(Statuses.UNIT_PRICE);
        Price price = new Price();
        accommodationStatus.put(Statuses.PRICE, price);
        unitPrice.getPrices().add(price);
    }

    @Override
    public void load(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException {
        //Set in the status map the unit where the following prices must be attached
        if (xmlr.getLocalName().equalsIgnoreCase("UnitID")) {
            Map<String, Unit> mapUnits = (Map<String, Unit>) accommodationStatus.get(Statuses.MAP_UNIT);
            accommodationStatus.put(Statuses.UNIT_PRICE, mapUnits.get(xmlr.getElementText()));
        }
        
        Price price = (Price) accommodationStatus.get(Statuses.PRICE);
        if (xmlr.getLocalName().equalsIgnoreCase("Price")) {
            price.setCurrency(xmlr.getAttributeValue(null, "Currency"));
        }
        if (xmlr.getLocalName().equalsIgnoreCase("Price")) {
            price.setAmount(xmlr.getElementText());
        }
    }

}
