package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class BinaryEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{

        /************************************************************************************************
         * The method will get the binaries values from the src                                         *
         * and return them in the right length according to the bit field as defined in the iso standard*
         *                                                                                              *
         *                                                                                              *
         * Note:                                                                                        *
         * If fixed size field - value will be right padded with zeros.                                 *
         * else- will calculate the length of the input and will add it as a prefix.                    *
         *                                                                                              *
         * ******************************************************************************************** *
         *                                          Examples:                                           *
         * ******************************************************************************************** *
         *       isFixed=false                                                                          *
         * ***************************                                                                  *
         * Input:    {0,0,1,0}                                                                          *
         * Output:   {4,0,0,1,0}                                                                        *
         *                                                                                              *
         * ***                                                                                          *
         * Input:    {1,1,1,0,1,1}                                                                      *
         * Output:   {6,1,1,1,0,1,1}                                                                    *
         *            ^                                                                                 *
         *      Prefix length                                                                           *
         *                                                                                              *
         * ******************************************************************************************** *
         *       isFixed=true                                                                           *
         * ***************************                                                                  *
         * Input:    {1,1,1,0,0,0,0,1,1,1}                                                              *
         * Output:   {1,1,1,0,0,0,0,1,1,1}      - no prefix and length is as maxLength for this field.  *
         * Output:   {1,1,1,0,0,0,0,1,1,1,0,0}  - no prefix and length is smaller by 2 from maxLength   *
         *                                                                                              *
         ************************************************************************************************
         **/

        binaryValidation(src);

        if (isFixed) {
            if (src.length > maxLength)
                throw new Exception("Binary input array length is too long.");
            return rightPadding(src,maxLength,'0');
        }

        else {
            return byteArraysConcat( new byte[] {(byte)src.length} , src);
        }
    }
}