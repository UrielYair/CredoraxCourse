package org.hit.fintech2018.Yair.HelpingMethods;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Auxiliaries
{
    public static   byte[]  byteArraysConcat(byte[]...arrays)   throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] arr : arrays)
            outputStream.write(arr);
        return outputStream.toByteArray();

    }
    public static   byte[]  xorBytesArrays(byte[] arrA, byte[] arrB) {
        byte[] result = new byte[arrA.length];
        for (int i = 0; i < arrA.length; i++)
            result[i] = (byte)((int)arrA[i] ^ (int)arrB[i]);
        return result;
    }
    public static   byte[]  digitsExtraction(byte[] beforeExtract) {
        byte[] digitsToReturn = new byte[beforeExtract.length];
        int index=0;
        for (byte b : beforeExtract) {
            if (b >= 0 && b <= 9)
                digitsToReturn[index++] = b;
        }
        return Arrays.copyOf(digitsToReturn,index);
    }
    public static   byte[]  hexExtraction(byte[] beforeExtract) {
        byte[] hexBytes = new byte[beforeExtract.length];
        int index = 0;
        for (byte b : beforeExtract) {
            if (b >= 10 && b <= 15)
                hexBytes[index++] = (byte) (b - 10);
        }
        return Arrays.copyOf(hexBytes, index);
    }
    public static   boolean byteArrayChecking(byte[]... args) throws Exception{
        for (byte[] arr :   args) {
            if (arr == null)
                throw new Exception("array can not be null.");
        }
        return true;
    }

    public static   byte[]  getNDigitsFromLeft(byte[] source, int amountOfDigits) throws Exception{
        if (source.length<amountOfDigits)
            throw new Exception("The value of the digits to return is too high!.");
        return Arrays.copyOf(source, amountOfDigits);
    }

    // Encryption methods:
    public static  byte[]   encryptOrDecryptDES(byte[] data, byte[] key, boolean toEncrypt)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey sKey = new SecretKeySpec(key,"DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        if (toEncrypt)
            cipher.init(Cipher.ENCRYPT_MODE,sKey);
        else
            cipher.init(Cipher.DECRYPT_MODE,sKey);
        return cipher.doFinal(data);
    }
    public static  byte[]   encryptOrDecrypt3DES(byte[] data, byte[] key1, byte[] key2, boolean toEncrypt)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        SecretKey sKey;

        if (toEncrypt)
        {
            sKey = new SecretKeySpec(key1, "DES");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            data = cipher.doFinal(data);

            sKey = new SecretKeySpec(key2, "DES");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            data = cipher.doFinal(data);

            sKey = new SecretKeySpec(key1, "DES");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            return cipher.doFinal(data);
        }
        else
        {
            sKey = new SecretKeySpec(key1, "DES");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            data = cipher.doFinal(data);

            sKey = new SecretKeySpec(key2, "DES");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            data = cipher.doFinal(data);

            sKey = new SecretKeySpec(key1, "DES");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            return cipher.doFinal(data);
        }
    }

    // Printing methods:
    public static   void    printByteArray(byte[] array){
        System.out.println(byteArrayToHexString(array));
    }

    // Converting methods:
    public static   byte[]  hexStringToByteArray(String s) {

        byte[] data = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    public static   byte[]  stringToByteArray(String byteArrayAsString){
        byte[] arrayToReturn = new byte[byteArrayAsString.length()];
        for (int i = 0; i < byteArrayAsString.length(); i++) {
            arrayToReturn[i] = (byte)((byteArrayAsString.charAt(i))-'0');
        }
        return arrayToReturn;

    }
    public static   String  byteArrayToHexString(byte[] array){
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();

    }
    public static   byte[]  stringToBytePackedArray(String byteArrayAsString){
        byte[] stringAsByteArr = stringToByteArray(byteArrayAsString);
        return packIntoPairsArray(stringAsByteArr);
    }
    public static   byte[]  packIntoPairsArray(byte[] data) {
        byte[] arrayOfPairs = new byte[data.length / 2];
        for (int i = 0; i < data.length; i+=2)
            arrayOfPairs[i/2] = (byte) ((data[i] << 4) | (data[i+1] & 0x0F));
        return arrayOfPairs;
    }
    public static   byte[]  unpackToBytesArray(byte[] data) {
        byte[] arrayToReturns = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            arrayToReturns[ i * 2 ] = (byte) ((data[i] & 0xF0) >> 4);
            arrayToReturns[ i * 2 + 1 ] = (byte) (data[i] & 0x0F);
        }
        return arrayToReturns;
    }
}
