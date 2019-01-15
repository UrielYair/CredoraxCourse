package org.hit.fintech2018.Yair.Assighnment2;


import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class CVCGenerator implements ICVCGenerator
{
    public CVCGenerator() {
    }

    @Override
    public byte[] getCVCValue(byte[] data, byte[] key1, byte[] key2, int digits) {
        return getCVCValue(data,null,null,key1,key2,digits);
    }

    @Override
    public byte[] getCVCValue(byte[] pan, byte[] expiry, byte[] serviceCode, byte[] key1, byte[] key2, int digits)
    //Todo: check if method signature can be changed.
    //throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException
    {
        try {
            // Step 1 & 2 : get all data concatenated one after another into 128bit array right padded with zeros.
            // [PAN] [EXPIRY DATE] [SERVICE CODE] [00...0]
            byte[] concatenated = byteArraysConcat(pan, expiry, serviceCode);

            // Right padding with zeros.
            concatenated = Arrays.copyOf(concatenated, 32);

            //*************************************************
            // Step 3: Split into 2 blocks of 64bit.
            byte[] block1 = Arrays.copyOfRange(concatenated, 0, 16);
            byte[] block2 = Arrays.copyOfRange(concatenated, 16, 32);

            // --- Make block1 and block2 in arrays of size: 8:
            block1 = packIntoPairsArray(block1);
            block2 = packIntoPairsArray(block2);

            // For checking purposes:
            // System.out.println(byteArrayToHexString(block1));
            // System.out.println(byteArrayToHexString(block2));

            //*************************************************
            //Step 4: Encrypt block 1 with key 1.
            byte[] step4Result = encryptOrDecryptDES(block1, key1, true);

            // For checking purposes:
            // System.out.println(byteArrayToHexString(step4Result));

            //*************************************************
            //Step 5: XOR result of step 4 with block 2.
            byte[] step5Result = xorBytesArrays(step4Result, block2);

            // For checking purposes:
            // System.out.println(byteArrayToHexString(step5Result));

            //*************************************************
            //Step 6: 3DES encryption of step5Result with key1 & key2.
            byte[] resultAfter3DES = encryptOrDecrypt3DES(step5Result, key1, key2, true);

            // For checking purposes:
            // System.out.println(byteArrayToHexString(resultAfter3DES));

            //*************************************************
            //Step 7: Split each pair to two values: 4 higher bits and 4 lower bits.

            // Example:
            //        00010100
            //        /     \
            // =>  0001  &  0100

            byte[] resultAfter3DESUnpacked = unpackToBytesArray(resultAfter3DES);

            // For checking purposes:
            // System.out.println("After splitting each pair: ");
            // printByteArray(resultAfter3DESUnpacked);

            //*************************************************
            //Step 8: Extract digits (0-9) from result of step 7.
            byte[] onlyDigits = digitsExtraction(resultAfter3DESUnpacked);

            // For checking purposes:
            // System.out.println("only the digits: ");
            // printByteArray(onlyDigits);

            //*************************************************
            //Step 9: Extract hexadecimal values (A-F) from result of step 7.
            //        and subtract 10 from each. It will convert each value to digits.
            byte[] onlyHexCharactersSubtracted = hexExtraction(resultAfter3DESUnpacked);

            // For checking purposes:
            // System.out.println("hex after subtraction: ");
            // printByteArray(onlyHexCharactersSubtracted);

            //*************************************************
            //Step 10: Concat resultAfter3DESUnpacked + onlyHexCharactersSubtracted.
            byte[] step10Result = byteArraysConcat(onlyDigits,onlyHexCharactersSubtracted);

            // For checking purposes:
            // System.out.println("after concatenation: ");
            // printByteArray(step10Result);

            //*************************************************
            //Step 11: Get specific amount of digits from left of the result of step 10.
            return getNDigitsFromLeft(step10Result, digits);

        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO:
        }
        return null;

    }


    @Override
    public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte[] cvcValue) {
        //TODO:
        return false;
    }

}
