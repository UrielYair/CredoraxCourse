package org.hit.fintech2018.Yair.Assighnment3.XMLStAXParser;

import org.hit.fintech2018.Yair.Assighnment3.BitInformation;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

public class BitInformationXMLStAXHandler
{
    private static final String FIELDS_FILE_NAME = "C:/Users/Uriel/Desktop/Credorax/src/org/hit/fintech2018/Yair/Assighnment3/XMLStAXParser/ISO8583_BitFieldsInfo.xml";
    // TODO: fix file path.
    public static BitInformation getInformationBasedOnBitNumber(Integer bitNumber) {

        try {
            String currentId = null;
            int length = 0;
            String bitDescription = null;
            String classPath = null;
            boolean fixed = false;

            /*
                Example of an entry in the xml file:
                XML Structure:
                ----------------------------------------
                    <bitfield
                      id="0"
                      length="4"
                      fixed="true"
                      name="MESSAGE TYPE INDICATOR"
                      class="org.hit.fintech2018.Yair.Assighnment3.Encoders.**Encoder"/>
                ----------------------------------------
            */

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader(FIELDS_FILE_NAME));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch (event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT: {

                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase("bitfield")) {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            Attribute currentAttribute;
                            HashMap <String, String> bitInfo = new HashMap<>();

                            while (attributes.hasNext()){
                                currentAttribute = attributes.next();
                                String keyName = currentAttribute.getName().toString();
                                String value = currentAttribute.getValue();
                                bitInfo.put(keyName,value);
                            }

                            if (bitInfo.get("id").equals(bitNumber.toString())){
                                return new BitInformation(
                                        bitNumber,
                                        Integer.parseInt(bitInfo.get("length")),
                                        bitInfo.get("class"),
                                        bitInfo.get("name"),
                                        Boolean.parseBoolean(bitInfo.get("fixed")));
                            }
                        }
                        break;
                    }
                }
            }
            return new BitInformation(bitNumber, length, classPath, bitDescription, fixed);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }
}

