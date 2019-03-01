package org.hit.fintech2018.Yair.Assighnment3;

import org.hit.fintech2018.Yair.Assighnment3.Encoders.AbstractISO8583Encoder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hit.fintech2018.Yair.Assighnment3.XMLStAXParser.BitInformationXMLStAXHandler.getInformationBasedOnBitNumber;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

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

            if (data.get(0) == null){
                if (version>9999)
                    throw new Exception("Message Type Indicator is too long");
                byte[] MTID;
                if (version>1000) {
                    String MTIDStr = String.valueOf(version);
                    MTID = new byte[]{ (byte) (MTIDStr.charAt(0)-'0'), (byte) (MTIDStr.charAt(1)-'0'), (byte) (MTIDStr.charAt(2)-'0'), (byte) (MTIDStr.charAt(3)-'0')};
                }
                else
                    MTID = getDigitsOfSpecificNumberInBytesArrayForm(version);
                data.put(0,MTID);
                dataElements.add(0);
            }

            TreeMap<Integer, byte[]> beforeConcatenation = new TreeMap<>();
            // For each of the DE's that found earlier,
            // will convert them as their preferences according to the ISO8583 standard.
            for (Integer i : dataElements) {
                // The method getBytesFromDataElement is responsible for fetching information about a specific DE.
                // and run the appropriate conversion for it.
                byte[] toAdd = getBytesFromDataElement(i, data.get(i));

                toAdd= packIntoPairsArray(toAdd);
                beforeConcatenation.put(i,toAdd);
            }

            for(Map.Entry<Integer, byte[]> entry : beforeConcatenation.entrySet())
                outputStream.write(entry.getValue());

            byte[] finish = outputStream.toByteArray();
            for (byte b: finish)
                System.out.print((char) b);
            System.out.println();
            return finish;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    private List<Integer>   getElementsToProcess(byte[] data) {
        /**
         * Method for getting all data element which takes part in the ISO8583 message.
         * */

        List<Integer> elementsToReturn = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i]==1)
                elementsToReturn.add(i+1);
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
