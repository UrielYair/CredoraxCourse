package org.hit.fintech2018.Yair.HelpingMethods;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Auxiliaries
{
    public static byte[] byteArraysConcat(byte[]...arrays) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] arr : arrays)
            outputStream.write(arr);
        return outputStream.toByteArray();

    }
    public static byte[] rightPadding(byte[] source, int lengthOfNewArray, byte paddingValue)
    {
        byte[] output= new byte[lengthOfNewArray];
        output=Arrays.copyOf(source,source.length);
        for (int i=source.length;i<lengthOfNewArray;i++)
            output[i]=paddingValue;
        return output;
    }
    public static byte[] xorBytesArrays(byte[] arrA, byte[] arrB)
    {
        byte[] result = new byte[arrA.length];
        for (int i = 0; i < arrA.length; i++)
            result[i] = (byte)((int)arrA[i] ^ (int)arrB[i]);
        return result;
    }
    public static byte[] encrypt(byte[] data, byte[] key, String algorithmType) throws Exception
    {
        SecretKey sKey = (SecretKey) new SecretKeySpec(key,algorithmType);
        //Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE,sKey);
        return cipher.doFinal(data);
    }
    public static byte[] decrypt(byte[] data, byte[] key, String algorithmType) throws Exception
    {
        SecretKey sKey = (SecretKey) new SecretKeySpec(key,algorithmType);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE,sKey);
        return cipher.doFinal(data);
    }
    public static byte[] digitsExtraction(byte[] beforeExtract)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        for (byte b : beforeExtract) {
            if (b >= 0 && b <= 9)
                outputStream.write( b );
        }
        return outputStream.toByteArray();
    }
    public static byte[] hexExtraction(byte[] beforeExtract)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        for (byte b : beforeExtract) {
            if (b >= 'A' && b <= 'F')
                outputStream.write( b );
        }
        byte[] afterSubstruction = outputStream.toByteArray();
        for (int i=0;i<afterSubstruction.length;i++) {
            afterSubstruction[i] -= 'A';
        }
        return afterSubstruction;
    }
    public static byte[] getNDigitsFromLest(byte[] source,int amountOfDigits)
    {
        byte[] result = new byte[amountOfDigits];
        for (int i=0;i<amountOfDigits;i++)
            result[i] = source[i];
        return result;
    }
}
