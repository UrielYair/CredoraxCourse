package org.hit.fintech2018.Yair.Assighnment2;

import java.util.Arrays;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.hexStringToByteArray;

public class CVCTest
{
    public static void main(String[] args)
    {
        /*
        -------------------------------------------------
        Example 1
        -------------------------------------------------
        Padded data:4123450990122129 0721202000000000
        Key A:D676DCF3AFB73505
        Key B:32EAF00EA253D875
        Block 1:4123450990122129
        Block 2:0721202000000000
        Block 1 encrypted with key A:4DC867AA9BCE665B
        Block 1 XORed with block 2:4AE9478A9BCE665B
        Result after 3DES:24E1A05574051BBF
        Decimalization candidates:2410557405140115
        CVC: 2410
        */

        try{
            CVCGenerator generator = new CVCGenerator();

            byte[] pan =            {4,1,2,3,4,5,0,9,9,0,1,2,2,1,2,9};
            byte[] expiry =         {0,7,2,1}   ;
            byte[] serviceCode =    {2,0,2}     ;
            int digits =             4;
            String k1,k2;
            k1 = "D676DCF3AFB73505";
            k2 = "32EAF00EA253D875";

            byte[] key1 = hexStringToByteArray(k1);
            byte[] key2 = hexStringToByteArray(k2);
            System.out.println(Arrays.toString(generator.getCVCValue(pan, expiry, serviceCode, key1, key2, digits)));

        }
        catch (Exception e) { e.printStackTrace();}
    }
}
