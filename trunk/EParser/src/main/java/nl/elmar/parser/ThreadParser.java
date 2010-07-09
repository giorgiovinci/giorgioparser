package nl.elmar.parser;

import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;



public class ThreadParser implements Runnable{

    private String fileName;
    private static final Logger log = Logger.getLogger(EParser.class);

    public ThreadParser(String fileName){
        this.fileName = fileName;
    }
    
    @Override
    public void run() {
        log.warn("Start processing");
        XMLStreamReader xmlr;
        try {
            
            xmlr = EParser.createXMLStreamReader(fileName);
            log.warn("Loaded xmlr" + xmlr);
            ELoader e = new ELoader();
            e.loadAccommodation(xmlr);
            
        } catch (Exception e) {
            log.warn("Exception " + e.getMessage());
            e.printStackTrace();
        }
        log.warn("End processing");
        
    }

}
