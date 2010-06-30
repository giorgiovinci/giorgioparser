package nl.elmar.parser;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.xml.sax.XMLReader;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;

/**
 * MyNamespaceFilter sample is used to demonstrates the use of STAX stream filter api's. This filter accepts only StartElement
 * events that belong to a particular namespace and filters out rest of the events.
 */

public class EParser {

    private static String filename = null;

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
