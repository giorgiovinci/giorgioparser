package nl.elmar.parser.loader;

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface Loader {
    
    /**
     * @param accommodationStatus
     * @param xmlr
     * @throws XMLStreamException
     */
    public void load(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException;

    public void initialize(Map<Statuses, Object> accommodationStatus, XMLStreamReader xmlr) throws XMLStreamException;
}
