package org.hit.fintech2018.Yair.Assighnment3;

import org.hit.fintech2018.Yair.Assighnment3.Encoders.AbstractISO8583Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hit.fintech2018.Yair.Assighnment3.XMLStAXParser.BitInformationXMLStAXHandler.getInformationBasedOnBitNumber;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.getDigitsOfSpecificNumberInBytesArrayForm;

public class ISO8583Serializer implements IISO8583Serializer
{
    public ISO8583Serializer() {}

    @Override
    public byte[]           serializeISO8583(int version, Map<Integer, byte[]> data) {

        /*******************************************************************************
        * Version (or MTI - Message type indicator):
        * Four numeric digits that specify the version of the ISO8583 standard: 1987, 1993 or 2003
        * and also define the message class, message function, and message origin.
        **
        * data -    the data elements.
        * the bitmap wil be stored on Data Element #1.
        * the bitmap will indicate what other element will be presented in the map.
        ********************************************************************************/

        ByteArrayOutputStream outputStream = null;

        // Getting all turned bits which indicate existence of data elements in the map:
        // The data element which holds this data stored in Data Element #1.
        List<Integer> dataElements = getElementsToProcess(data.get(1));

        try {
            outputStream = new ByteArrayOutputStream( );

            // Adding the MTID before all the data elements content of the message.

            // Because of using ByteArrayOutputStream the program will convert the version number to byte array.
            // For Example 1 --> {1}
            outputStream.write(getDigitsOfSpecificNumberInBytesArrayForm(version));

            // For each of the DE's that found earlier,
            // will convert them as their preferences according to the ISO8583 standard.
            for (Integer i : dataElements) {
                byte[] toAdd = getBytesFromDataElement(i, data.get(i));
                // The method getBytesFromDataElement is responsible for fetching information about a specific DE.
                // and run the appropriate conversion for it.

                // The returned value will be added to the outputStream.
                outputStream.write(toAdd);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (outputStream ==null)    return null;
        else
            return outputStream.toByteArray( );

    }
    private List<Integer>   getElementsToProcess(byte[] data) {
        /**
         * Method for getting all data element which takes part in the ISO8583 message.
         * */

        List<Integer> elementsToReturn = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i]==1)
                elementsToReturn.add(i);
        }
        return elementsToReturn;
    }
    private byte[]          getBytesFromDataElement(Integer bitNumber, byte[] data) {

        try {
            BitInformation infoOfSpecificBit = getInformationBasedOnBitNumber(bitNumber); // Using XML StAX Parsing.
            // Information for every bit is stored in an XML file

            if (infoOfSpecificBit!=null) {
                AbstractISO8583Encoder converterInstance = (AbstractISO8583Encoder) Class.forName(infoOfSpecificBit.getClassPath()).newInstance();
                return converterInstance.encode(data,infoOfSpecificBit.getLength(),infoOfSpecificBit.getFixed());

            }
            else
                System.out.println("No such data element in ISO8583.");
                return null;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
