package org.hit.fintech2018.Yair.Assighnment3.Encoders;
import static org.hit.fintech2018.Yair.Assighnment3.Encoders.HelpingMethods.*;

public class LNumericEncoder extends AbstractISO8583Encoder
{
    private final int numOfLs = 1;

    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{

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

        return numericEncoding(src,maxLength,isFixed,numOfLs);

    }
}
