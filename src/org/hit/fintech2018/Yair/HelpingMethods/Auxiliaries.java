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
    // TODO: add examples to each of the methods !!!!!

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
    public static   byte[]  leftPadding(byte[] src, int newSize, char characterToPadWith) throws Exception{
        if (newSize==src.length)    return src;
        if (newSize<src.length)
            throw new Exception("New size for padding method is smaller than the length of the original array.");

        byte[] arrayToReturn = new byte[newSize];

        int paddingValueAsInteger = characterToPadWith-'0';
        // The left side - padded area.
        for (int i = 0; i < newSize-src.length; i++)
            arrayToReturn[i] = (byte) paddingValueAsInteger;

        // The right side - original values.
        System.arraycopy(src,0,arrayToReturn,newSize-src.length,src.length);

        return arrayToReturn;
    }
    public static   byte[]  rightPadding(byte[] src, int newSize, char characterToPadWith) throws Exception{
        /*
        * The method will pad on the right side with givven character.
        * Input:    {H,i, ,3,4,5}, 9, 'B'
        * Output:   {H,i, ,3,4,5,B,B,B}
        * */

        if (newSize==src.length)    return src;
        if (newSize<src.length)
            throw new Exception("New size for padding method is smaller than the length of the original array.");

        byte[] arrayToReturn = new byte[newSize];

        // The left side - original values.
        System.arraycopy(src,0,arrayToReturn,0 ,src.length);

        // The right side - padded area.
        for (int i = src.length; i < newSize; i++)
            arrayToReturn[i] = (byte) characterToPadWith;

        return arrayToReturn;
    }
    public static   byte[]  getDigitsOfSpecificNumberInBytesArrayForm(int size) throws Exception{
        /*
        * Input: int number represent length of value.
        * Output: byte array which represent the length.
        *
        * Examples:
        *
        * input- 7
        * Output- {7}
        *
        * input- 10
        * Output- {1,0}
        *
        * input- 152
        * output-  {0,1,5,2}
        * */
        if (size<0)   throw new Exception("value of length can not be negative.");

        String hexString = Integer.toHexString(size);
        int hexLength = hexString.length();

        if (hexLength>4)
            throw new Exception("Length value is not valid for ISO8583 data element");

        switch (hexLength)
        {
            case 1: {
                return new byte[]{(byte)(size-'0')};
                // break;
            }
            case 2: {
                return (new byte[]{ (byte) (hexString.charAt(0)-'0'),
                                    (byte) (hexString.charAt(1)-'0')});
                //break;
            }
            case 3:{
                return leftPadding((new byte[]{ (byte) (hexString.charAt(0)-'0'),
                                    (byte) (hexString.charAt(1)-'0'),
                                    (byte) (hexString.charAt(2)-'0')}),4,'0');
                //break;
            }
        }
        return null;
    }
    public static   byte[]  getPrefixForInputLengthBetween_L_LL_LLL(int srcArraySize, int numOfLs) throws Exception{
        /*
        * The method will calculate the length prefix based on the class structure (amount of L's in the name of the class).
        *
        * for Example:
        * Calling class is LLLCharEncoder (3 L's) and srcArraySize is 56.
        * Output: {0,0,5,6} - ( four bits that eventually will be packed )
        * - the leading 0 is left padding, and the reason for 4 bits long is to support length of 3 digits like 997.
        *   the padding is because of the later packing on the prefix.
        *
        * Input: Calling class: LLNumericEncoder (2 L's) and srcArraySize of 6.
        * Output: {06}
        * Output: If the calling class was LCharEncoder {06} - padding because of the later packing.
        * */

        if (numOfLs == 1){
            if (srcArraySize<10) {
                return leftPadding(getDigitsOfSpecificNumberInBytesArrayForm(srcArraySize),2,'0');
            }
            else
                throw new Exception("The array length of the input doesn't fit the bit field.");
        }
        else if (numOfLs == 2){
            if (srcArraySize<100){
                return leftPadding(getDigitsOfSpecificNumberInBytesArrayForm(srcArraySize),2,'0');
            }
            else
                throw new Exception("The array length of the input doesn't fit the bit field.");
        }
        else if (numOfLs == 3){
            if (srcArraySize<1000) {
                return leftPadding(getDigitsOfSpecificNumberInBytesArrayForm(srcArraySize), 4, '0');
            }
            else
                throw new Exception("The array length of the input doesn't fit the bit field.");
        }
        else
            throw new Exception("Number of L's: " + numOfLs + " is not defined in ISO8583 standard.");

    }

    // Validation methods:
    public static   void    binaryValidation(byte[] src) throws Exception{
        for (byte b : src) {
            if (b < 0 || b > 1) throw new Exception("Not in a valid binary format.");
        }
    }
    public static   void    numericValidation(byte[] src) throws Exception{
        for (byte b : src) {
            if (b < 0 || b > 9) throw new Exception("Not in a valid numeric format.");
        }
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
    public static  void     printByteArray(byte[] array){
        System.out.println(byteArrayToHexString(array));
    }

    // Converting methods:
    public static   byte[]  packIntoPairsArray(byte[] data) throws Exception{
        /**
         * Pack input to nibbles (packed bytes) .
         * - Assuming 'data' comes as singles bytes.
         *
         * Input:           {A,5,B,F,3,0}
         * Output:          {A5,BF,30}
         */

        if (data.length%2!=0) data = leftPadding(data,data.length+1,'0');
        byte[] arrayOfPairs = new byte[data.length / 2];
        for (int i = 0; i < data.length; i+=2)
            arrayOfPairs[i/2] =  (byte) ((byte)(data[i] << 4) | (byte)(data[i+1]));
        return arrayOfPairs;
    }
    public static   byte[]  unpackToBytesArray(byte[] data) {
        // Assuming 'data' comes as nibbles and will return as character of byte.
        /*
        * Input:            {A5,BF,30}
        * Output:           {A,5,B,F,3,0}
        */

        byte[] arrayToReturns = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            arrayToReturns[ i * 2 ] = (byte) ((data[i] & 0xF0) >> 4);
            arrayToReturns[ i * 2 + 1 ] = (byte) (data[i] & 0x0F);
        }
        return arrayToReturns;
    }
    public static   byte[]  binaryToHex(byte[] data) throws Exception{

        if (data.length%4!=0) throw new Exception("length of binary data must be able to divide by 4 in order to be converted to HexDecimal value.");
        byte[] arrayToReturn = new byte[data.length/4];

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<data.length;i++){
            sb.append(data[i]);
        }
        String binaryStr = sb.toString();

        for (int i = 0; i < data.length / 4; i++) {
            String current4bit = binaryStr.substring(i*4,i*4+4);
            int decimal = Integer.parseInt(current4bit, 2);
            String hexStr = Integer.toString(decimal, 16).toUpperCase();
            arrayToReturn[i] = (byte) (hexStr.charAt(0) -'0');
        }

        return arrayToReturn;

    }
    public static   byte[]  ASCIIBytesArrayToHexByteArray(byte[] data) {

        String dataAsString = new String(data);
        byte[] toRet = new byte[dataAsString.length()];

        for (int i=0; i<dataAsString.length() ;i++)
            toRet[i] = (byte) dataAsString.charAt(i);

        return toRet;

        }
    public static   byte[]  hexStringToByteArray(String s) {

        byte[] data = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    public static   byte[]  stringToBytePackedArray(String byteArrayAsString) throws Exception{
        byte[] stringAsByteArr = stringToByteArray(byteArrayAsString);
        return packIntoPairsArray(stringAsByteArr);
    }
    public static   byte[]  stringToByteArray(String byteArrayAsString){
        byte[] arrayToReturn = new byte[byteArrayAsString.length()];
        for (int i = 0; i < byteArrayAsString.length(); i++) {
            arrayToReturn[i] = (byte)((byteArrayAsString.charAt(i))-'0');
        }
        return arrayToReturn;

    }
    public static   String  convertByteArrayToASCIIString(byte[] src){
        /*
         * Convert byte array values to ASCII characters.
         * Input:    61 61 61 42 42 42 42
         * Output:   aaaBBBB
         * */
        char[] asciiCharactersOfBytesValue = new char[src.length];
        for (int i=0; i<src.length;i++)
            asciiCharactersOfBytesValue[i] = (char) src[i];
        return String.valueOf(asciiCharactersOfBytesValue);
    }
    public static   String  byteArrayToHexString(byte[] array){
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();

    }
    public static   String  toStringOfHexArray(byte[] data){
        /*
        String dataAsString = new String(data);
        //For creating a string for this hex byte array:

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<dataAsString.length();i++)
            sb.append(Integer.toHexString((int) dataAsString.charAt(i)));
        return sb.toString();
        */


        StringBuilder sb = new StringBuilder();
        for (int i=0;i<data.length;i++)
            sb.append(String.format("%02X ",  data[i]));
        String result = sb.toString();
        return result;

    }

}
