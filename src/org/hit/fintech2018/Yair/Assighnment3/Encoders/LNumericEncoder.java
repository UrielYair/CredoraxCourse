package org.hit.fintech2018.Yair.Assighnment3.Encoders;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class LNumericEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{

        /************************************************************************************************
         * The method will get the numeric values from the src                                          *
         * and return them in the right format according to the bit field as defined in the iso standard*
         *                                                                                              *
         *
         * Notes:
         * 1. The returned values will be padded - if needed (left padding with zeros).
         * 2. Can handle fixed length or not.
         * 3. The returned bytes will be packed.
         * 4. The prefix will be divided by 2 because of packing the byte array.                        *
         *
         * ******************************************************************************************** *
         *                                          Examples:                                           *
         * ******************************************************************************************** *
         *       isFixed=false                                                                          *
         * ***************************                                                                  *
         * Input:    {3,7,9,0}                                                                          *
         * Output:   {02 37 90}                                                                         *
         *            ^Prefix length                                                                    *
         ****                                                                                           *
         * Input:    {3,7,9,0,8}                                                                        *
         * Output:   {03 03 79 08}                                                                      *
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

        //TODO: check prefix values
        byte[] arrayToReturn = null;

        if (isFixed) {
            arrayToReturn = leftPadding(src, maxLength, '0');
            return packIntoPairsArray(arrayToReturn);
        }

        else {
            if (src.length%2!=0)    src = leftPadding(src,src.length+1,'0');

            src = packIntoPairsArray(src);
            byte[] prefixLength = packIntoPairsArray(getDigitsInBitesArrayForm(maxLength/2));

            return byteArraysConcat(prefixLength,src);
        }
    }
}
