package org.hit.fintech2018.Yair.Assighnment3.XMLStAXParser;

import org.hit.fintech2018.Yair.Assighnment3.BitInformation;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class BitInformationXMLStAXHandler
{
    private static final String FIELDS_FILE_NAME = "org/hit/fintech2018/Yair/Assighnment3/XMLStAXParser/ISO8583_BitFieldsInfo.xml";

    public static BitInformation getInformationBasedOnBitNumber(Integer bitNumber) {

        boolean bRequestedField, bId, bLength, bName, bClass;
        bRequestedField = bId = bLength = bName = bClass = false;

        try {
            String currentId = null;
            int length = 0;
            String bitDescription = null;
            String classPath = null;

            /*
                XML Structure:
                ----------------------------------------
                    <bitfield
                      id="0"
                      length="4"
                      name="MESSAGE TYPE INDICATOR"
                      class="org.hit.fintech2018.Yair.Encoders.*Encoder"/>
                ----------------------------------------
            */

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader(FIELDS_FILE_NAME));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch (event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase("bitfield")) {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            currentId = attributes.next().getValue();
                            if (currentId.equals(bitNumber.toString()))
                                bRequestedField = true;
                        }

                        if (qName.equalsIgnoreCase("id"))
                            bId = true;
                        if (qName.equalsIgnoreCase("length"))
                            bLength = true;
                        if (qName.equalsIgnoreCase("name"))
                            bName = true;
                        if (qName.equalsIgnoreCase("class"))
                            bClass = true;

                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bId) {
                            currentId = characters.getData();
                            bId = false;
                        }
                        if (bLength) {
                            length = Integer.parseInt(characters.getData());
                            bLength = false;
                        }
                        if (bName) {
                            bitDescription = characters.getData();
                            bName = false;
                        }
                        if (bClass) {
                            classPath = characters.getData();
                            bClass = false;
                        }

                        return new BitInformation(bitNumber, length, classPath, bitDescription);
                        //break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase(
                                "bitfield") && bRequestedField) {
                            bRequestedField = false;
                        }
                        break;
                }

            }
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

