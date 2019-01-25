package org.hit.fintech2018.Yair.Assighnment3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.unpackToBytesArray;
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

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

        List<Integer> dataElements = getElementsFromFirstDataElement(data.get(1));
        for (Integer i : dataElements) {
            try {
                outputStream.write(getBytesFromDataElement(i, data.get(i)));
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
            // The BitInformation have the following members:
            //      - bitNumber
            //      - length
            //      - classPath
            //      - bitDescription.

            //reflection
            if (infoOfSpecificBit!=null) {
                Object classConverter = Class.forName(infoOfSpecificBit.getClassPath()).newInstance();

                Method convertingMethod = classConverter.getClass().getDeclaredMethod("convertToHexByte", null);
                return (byte[]) convertingMethod.invoke(classConverter, bitNumber, data);
            }
            else
                return null; //Todo: fix and manage correctly all nullable returned values.



            // Assuming i have a file that looks like:
        /*
            bitNumber="0"
            length="4"
            name="MESSAGE TYPE INDICATOR"
            package="org.hit.fintech2018.Yair.classSomething/"
        */
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data; //Todo: fix
    }

    //Todo: change to private after checking.
    private List<Integer> getElementsFromFirstDataElement(byte[] data) {
        //data = unpackToBytesArray(data);
        List<Integer> elementsToReturn = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i]==1)
                elementsToReturn.add(i);
        }
        return elementsToReturn;
    }
}
