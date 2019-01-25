package org.hit.fintech2018.Yair.Assighnment3;

import org.hit.fintech2018.Yair.Converters.AbstractConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hit.fintech2018.Yair.HelpingMethods.BitInformationXMLStAXHandler.getInformationBasedOnBitNumber;

public class ISO8583Serializer implements IISO8583Serializer
{
    public ISO8583Serializer() {}

    @Override
    public byte[] serializeISO8583(int version, Map<Integer, byte[]> data) {
        /*
        * Version (or MTI - Message type indicator):
        * Four numeric digits that specify the version of the ISO8583 standard: 1987, 1993 or 2003
        * and also define the message class, message function, and message origin.
        **
        * data -    the data elements.
        * the bitmap wil be stored on Data Element #1.
        * the bitmap will indicate what other element will be presented in the map.
        */

        // Assuming that each value of data in the key-value pair is valid.

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

        List<Integer> dataElements = getElementsToProcess(data.get(1));
        for (Integer i : dataElements) {
            try {
                byte[] toAdd = getBytesFromDataElement(i, data.get(i));
                outputStream.write(toAdd);
            }
            catch (IOException e) {
                //Todo: check option of changing the body of the catch block.
                e.printStackTrace();
            }
        }

        return outputStream.toByteArray( );
    }

    private byte[] getBytesFromDataElement(Integer bitNumber, byte[] data) {
        try {


            BitInformation infoOfSpecificBit = getInformationBasedOnBitNumber(bitNumber);
            //Todo: fix all class paths in field.xml file. to my classes after implementing them.
            //reflection
            if (infoOfSpecificBit!=null) {
                AbstractConverter converterInstance = (AbstractConverter) Class.forName(infoOfSpecificBit.getClassPath()).newInstance();
                converterInstance.setData(data); //Todo: maybe there is no need for convering and only return the array.
                return converterInstance.convertToHexByte(infoOfSpecificBit.getLength());

                // include data in the class converter object.
                //Method convertingMethod = classConverter.getClass().getDeclaredMethod("convertToHexByte", null);
                //return (byte[]) convertingMethod.invoke(classConverter);
            }
            else
                return null; //Todo: fix and manage correctly all nullable returned values.
        }

        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data; //Todo: fix
    }

    //Todo: change to private after checking.
    private List<Integer> getElementsToProcess(byte[] data) {
        //data = unpackToBytesArray(data);
        List<Integer> elementsToReturn = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i]==1)
                elementsToReturn.add(i);
        }
        return elementsToReturn;
    }
}
