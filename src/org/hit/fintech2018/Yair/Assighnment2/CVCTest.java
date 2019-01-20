package org.hit.fintech2018.Yair.Assighnment2;

import java.io.IOException;
import java.util.Arrays;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class CVCTest
{
    public static void main(String[] args) {
        try {
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
            CVCGenerator generator = new CVCGenerator();

            byte[] pan = {4, 1, 2, 3, 4, 5, 0, 9, 9, 0, 1, 2, 2, 1, 2, 9};
            byte[] expiry = {0, 7, 2, 1};
            byte[] serviceCode = {2, 0, 2};
            int digits = 4;
            String k1, k2;
            k1 = "D676DCF3AFB73505";
            k2 = "32EAF00EA253D875";
            byte[] key1 = hexStringToByteArray(k1);
            byte[] key2 = hexStringToByteArray(k2);
            byte[] cvc1 = {2, 4, 1, 0};

            byte[] cvcReturned1 = generator.getCVCValue(pan, expiry, serviceCode, key1, key2, digits);
            System.out.println(Arrays.toString(cvcReturned1));

            System.out.println(generator.checkCVCValue(byteArraysConcat(pan, expiry, serviceCode), key1, key2, cvc1));

            /*
            -------------------------------------------------
            Example 2
            -------------------------------------------------
            Padded data:4123450990122129 0721202000000000
            Key A:CF39DA32065FF7AC
            Key B:C8753B0D1FE4582F
            Block 1:4123450990122129
            Block 2:0721202000000000
            Block 1 encrypted with key A:28DF9E861B98C389
            Block 1 XORed with block 2:2FFEBEA61B98C389
            Result after 3DES:83AD4B97D7922CA5
            Decimalization candidates:8349779225031320
            CVC: 8349
            -------------------------------------------------
            */

            k1 = "CF39DA32065FF7AC";
            k2 = "C8753B0D1FE4582F";

            key1 = hexStringToByteArray(k1);
            key2 = hexStringToByteArray(k2);

            byte[] cvcReturned2 = generator.getCVCValue(pan, expiry, serviceCode, key1, key2, digits);
            System.out.println(Arrays.toString(cvcReturned2));

            byte[] cvc2 = {8, 3, 4, 9};
            System.out.println(generator.checkCVCValue(byteArraysConcat(pan, expiry, serviceCode), key1, key2, cvc2));
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
