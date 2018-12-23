package org.hit.fintech2018.Yair.Assighnment2;


import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class CVCGenerator implements ICVCGenerator
{
    public CVCGenerator(){}

    @Override
    public byte[] getCVCValue(byte[] data, byte[] key1, byte[] key2, int digits)
    {

        return new byte[0];
    }

    @Override
    public byte[] getCVCValue(byte[] pan, byte[] expiry, byte[] serviceCode, byte[] key1, byte[] key2, int digits)
    {

        try {
            // Step 1&2: get all data concatenated one after another into 128bit right padded with zeros.
            // [PAN] [EXPIRY DATE] [SERVICE CODE] [00...0]
            byte[] concatenated = byteArraysConcat(pan,expiry,serviceCode);
            concatenated = rightPadding(concatenated, 32, (byte) 0);

            //*************************************************
            // Step 3: Split into 2 blocks of 64bit.
            byte[]  block1=new byte[16],
                    block2 = new byte[16];

            for (int i=0;i<32;i++)
            {
                if (i<16)
                    block1[i]=concatenated[i];
                else
                    block2[i%16]=concatenated[i];
            }

            //*************************************************
            //Step 4: Encrypt block 1 with key 1.
            byte[] step4Result = encrypt(block1,key1,"DES");

            //*************************************************
            //Step 5: XOR result of step 4 with block 2.
            byte[] step5Result=xorBytesArrays(step4Result,block2);

            //*************************************************
            //Step 6: Decrypt the result of step 5 with key 2.
            byte[] step6Result = decrypt(step5Result,key2,"DES");

            //*************************************************
            //Step 7: Encrypt the result of step 6 with key 1.
            byte[] step7Result = encrypt(step6Result,key1,"DES");

            //*************************************************
            //Step 8: Extract digits (0-9) from result of step 7.
            byte[] step8Result = digitsExtraction(step7Result);

            //*************************************************
            //Step 9: Extract hexadecimal values (A-F) from result of step 7.
            //        and subtract 10 ('A') from each. It will convert each value to digits.
            byte[] step9Result = hexExtraction(step7Result);

            //*************************************************
            //Step 10: Concat step8Result + step9Result.
            byte[] step10Result = byteArraysConcat(step8Result,step9Result);

            //*************************************************
            //Step 11: Get specific amount of digits from left of the result of step 10.

            return getNDigitsFromLeft(step10Result,digits);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];

    }













    @Override
    public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte[] cvcValue)
    {
        return false;
    }

}
