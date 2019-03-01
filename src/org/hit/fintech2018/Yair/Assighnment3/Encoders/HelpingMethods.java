package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.byteArraysConcat;

public class HelpingMethods
{
    public static byte[] alphanumericEncoding(byte[] src, int maxLength, boolean isFixed, int numOfLs) throws Exception {

        /************************************************************************************************
         * The method will get all character bit from the src                                           *
         * and returns the ASCII value of them.                                                         *
         *                                                                                              *
         *                                                                                              *
         * Notes:                                                                                       *
         * 1. The returned values will be padded - if needed (right padding with spaces).               *
         * 2. Can handle fixed length or not.                                                           *
         * 3. The returned bytes will be packed.                                                        *
         * 4. The prefix will be divided by 2 because of packing the byte array.                        *
         *                                                                                              *
         * ******************************************************************************************** *
         *                                          Examples:                                           *
         * ******************************************************************************************** *
         *                                                                                              *
         * isFixed=false                                                                                *
         * Input:    {h,i, ,3,7,9,0}                                                                    *
         * Output:   {7 68 69 20 33 37 39 30}                                                           *
         *            ^Prefix length                                                                    *
         *                                                                                              *
         * Input:    {H,E,L,L, ,Y,E,S,!,!}                                                              *
         * Output:   {10 48 45 4c 4c 20 59 45 53 21 21}                                                 *
         *            ^Prefix length                                                                    *
         *                                                                                              *
         * ******************************************************************************************** *
         *                                                                                              *
         * isFixed=true                                                                                 *
         * Input:    {3,7,9,0,5,4,2}                                                                    *
         * Output:   {03 79 05 42}                                                                      *
         *            ^padding                                                                          *
         *                                                                                              *
         * Input:    {1,3,7,9,0,5,4,2}                                                                  *
         * Output:   {13 79 05 42}                                                                      *
         *                                                                                              *
         ************************************************************************************************
         **/



        byte[] arrayToReturn = null;
        arrayToReturn = ASCIIBytesArrayToHexByteArray(src);

        if (isFixed) {

            if (arrayToReturn.length<=maxLength)
                arrayToReturn = rightPadding(arrayToReturn, maxLength, ' ');
            else
                throw new Exception("The input array is too long for this bit field.");
            return arrayToReturn;

        }
        else {
            // Concatenation of length as prefix
            // followed by the value of the data element.

            byte[] prefixLength = getPrefixForInputLengthBetween_L_LL_LLL(src.length, numOfLs);

            if (arrayToReturn.length % 2 != 0)
                arrayToReturn = rightPadding(arrayToReturn, src.length + 1, ' ');

            byte[] result = byteArraysConcat(prefixLength, arrayToReturn);

            int prefix = Integer.valueOf(String.valueOf(result[0]));
            int x= 10*(prefix/16) + prefix%16;
            result[0] = (byte)x;
            return result;
        }
    }

    public static byte[] numericEncoding(byte[] src, int maxLength, boolean isFixed, int numOfLs) throws Exception {

        /************************************************************************************************
         * The method will get the numeric values from the src                                          *
         * and return them in the right format according to the bit field as defined in the iso standard*
         *                                                                                              *
         *                                                                                              *
         * Notes:                                                                                       *
         * 1. The returned values will be padded - if needed (left padding with zeros).                 *
         * 2. Can handle fixed length or not.                                                           *
         * 3. The returned bytes will be packed.                                                        *
         * 4. The prefix will be divided by 2 because of packing the byte array.                        *
         *                                                                                              *
         * ******************************************************************************************** *
         *                                          Examples:                                           *
         * ******************************************************************************************** *
         *       isFixed=false                                                                          *
         * ***************************                                                                  *
         * Input:    {3,7,9,0}                                                                          *
         * Output:   {2 37 90}                                                                         *
         *            ^Prefix length                                                                    *
         ****                                                                                           *
         * Input:    {3,7,9,0,8}                                                                        *
         * Output:   {3 03 79 08}                                                                      *
         *            ^  ^padding                                                                       *
         *            ^                                                                                 *
         *      Prefix length                                                                           *
         *                                                                                              *
         * ******************************************************************************************** *
         *       isFixed=true                                                                           *
         * ***************************                                                                  *
         * Input:    {8,7,9,0,5,4,2}                                                                    *
         * Output:   {08 79 05 42}                                                                      *
         *            ^padding                                                                          *
         ****                                                                                           *
         * Input:    {3,7,9,0,5,4}                                                                      *
         * Output:   {00 37 90 54}                                                                      *
         *            ^padding                                                                          *
         ************************************************************************************************
         **/

        numericValidation(src);

        byte[] arrayToReturn = null;

        if (isFixed) {
            if (src.length<=maxLength)
                arrayToReturn = leftPadding(src, maxLength, '0');
            else
                throw new Exception("The input array is too long for this bit field.");

            return packIntoPairsArray(arrayToReturn);
        }
        else {
            // Concatenation of length as prefix
            // followed by the value of the data element.

            byte[] prefixLength = getPrefixForInputLengthBetween_L_LL_LLL(src.length, numOfLs);

            if (src.length % 2 != 0) src = leftPadding(src, src.length + 1, '0');
            src = packIntoPairsArray(src);

            byte[] result = byteArraysConcat(prefixLength, src);

            int prefix = Integer.valueOf(String.valueOf(result[0]));
            int x= 10*(prefix/16) + prefix%16;
            result[0] = (byte)x;
            return result;

        }

    }
}
