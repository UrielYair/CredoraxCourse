package org.hit.fintech2018.Yair.Assighnment3.Encoders;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class LCharEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception {
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

        if (isFixed) {
            arrayToReturn = rightPadding(src, maxLength, ' ');
            return packIntoPairsArray(arrayToReturn);
        }

        else {
            // Concatenation of length as prefix
            // followed by the value of the data element.

            byte[] prefixLength = getPrefixForInputLengthBetween_L_LL_LLL(src.length);

            if (src.length%2!=0)    src = rightPadding(src,src.length+1,' ');
            src = packIntoPairsArray(src);

            return byteArraysConcat(prefixLength,src);
        }
    }
}