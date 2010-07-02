package nl.elmar.parser;

import java.io.FileInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class EParser {

   
    private static void printUsage() {
        System.out.println("usage: java nl.elmar.parser.EParser <xmlfile>");
    }

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		
		if(args.length != 1){
			printUsage();
			System.exit(0);
		}
		String filename = args[0];
		
		EParser eparser = new EParser();
		XMLStreamReader xmlr = eparser.createXMLStreamReader(filename);
        ELoader elmarParser = new ELoader();
        elmarParser.loadAccommodation(xmlr);
	}

    public XMLStreamReader createXMLStreamReader(String filename) throws Exception{
        XMLInputFactory xmlif = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.TRUE);
            xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,Boolean.FALSE);
            xmlif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE,Boolean.FALSE);
            xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("XMLInputFactory: " + xmlif);
        System.out.println("filename = " + filename);

        XMLStreamReader xmlr = null;

            FileInputStream fis = new FileInputStream(filename);
            xmlr = xmlif.createXMLStreamReader(fis);

        return xmlr;
    }



}
