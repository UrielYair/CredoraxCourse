package org.hit.fintech2018.Yair.HelpingMethods;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Auxiliaries
{
    public static   byte[]  byteArraysConcat(byte[]...arrays)   throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] arr : arrays)
            outputStream.write(arr);
        return outputStream.toByteArray();

    }
    public static   byte[]  rightPadding(byte[] source, int lengthOfNewArray, byte paddingValue) {
        byte[] output= new byte[lengthOfNewArray];
        System.arraycopy(source,0,output,0,source.length);
        for (int i=source.length;i<lengthOfNewArray;i++)
            output[i]=paddingValue;
        return output;
    }
    public static   byte[]  xorBytesArrays(byte[] arrA, byte[] arrB) {
        byte[] result = new byte[arrA.length];
        for (int i = 0; i < arrA.length; i++)
            result[i] = (byte)((int)arrA[i] ^ (int)arrB[i]);
        return result;
    }
    public static   byte[]  encryptDES(byte[] data, byte[] key, String algorithmType) throws Exception {
        return encryptOrDecryptWithDES(data, key, algorithmType, true);
    }
    public static   byte[]  decryptDES(byte[] data, byte[] key, String algorithmType) throws Exception {
        return encryptOrDecryptWithDES(data, key, algorithmType, false);
    }
    private static  byte[]  encryptOrDecryptWithDES(byte[] data, byte[] key, String algorithmType, boolean toEncrypt) throws Exception {
        SecretKey sKey = new SecretKeySpec(key,algorithmType);
        Cipher cipher = Cipher.getInstance("DES");
        if (toEncrypt)
            cipher.init(Cipher.ENCRYPT_MODE,sKey);
        else
            cipher.init(Cipher.DECRYPT_MODE,sKey);
        return cipher.doFinal(data);
    }
    public static   byte[]  digitsExtraction(byte[] beforeExtract) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        for (byte b : beforeExtract) {
            if (b >= 0 && b <= 9)
                outputStream.write( b );
        }
        return outputStream.toByteArray();
    }
    public static   byte[]  hexExtraction(byte[] beforeExtract){
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
    public static   byte[]  getNDigitsFromLeft(byte[] source, int amountOfDigits) {
        byte[] result = new byte[amountOfDigits];
        System.arraycopy(source,0,result,0,amountOfDigits);
        //for (int i=0;i<amountOfDigits;i++)
        //    result[i] = source[i];
        return result;
    }
    public static   byte[]  hexStringToByteArray(String s) {

        byte[] data = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    public static   void    printByteArray(byte[] arr){
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : arr) {
            stringBuilder.append(String.format("%02X ", b));
        }
        System.out.println(stringBuilder.toString());
    }

    public static   byte[]  stringToByteArray(String byteArrayAsString){
        byte[] arrayToReturn = new byte[byteArrayAsString.length()];
        for (int i = 0; i < byteArrayAsString.length(); i++) {
            arrayToReturn[i] = (byte)((byteArrayAsString.charAt(i))-'0');
        }
        return arrayToReturn;

    }
}
