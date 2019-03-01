package org.hit.fintech2018.Yair.Assighnment3;

import java.util.HashMap;
import java.util.Map;

public class Check
{

    public static void main(String[] args) {

        try {
            Map<Integer, byte[]> data = new HashMap<>();
            byte[] bitmap = new byte[]{1,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            data.put(1,bitmap);
            data.put(2,new byte[]{0,4,0,5,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
            data.put(8,new byte[]{0,1,0,2,0,3});
            data.put(9,new byte[]{0,4,0,5,0,6,0,7});
            //data.put(18,new byte[]{0,8,0,9,0,0,0,1});
            data.put(18,new byte[]{8,9,0,1});
            //data.put(49,new byte[]{0,2,0,3,0,4,0,5});
            data.put(49,new byte[]{2,3,4});

            ISO8583Serializer iso8583Serializer = new ISO8583Serializer();
            byte[] result = iso8583Serializer.serializeISO8583(1100,data);

            System.out.println();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



}
