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
         * Only the prefix will packed.                                                                 *
         *                                                                                              *
         * ******************************************************************************************** *
         *                                          Examples:                                           *
         * ******************************************************************************************** *
         *       isFixed=false                                                                          *
         * ***************************                                                                  *
         * Input:    {0,0,1,0}                                                                          *
         * Output:   {04,0,0,1,0}                                                                       *
         *                                                                                              *
         * ***                                                                                           *
         * Input:    {1,1,1,0,1,1}                                                                      *
         * Output:   {03,1,1,1,0,1,1}                                                                   *
         *            ^                                                                                 *
         *      Prefix length                                                                           *
         *                                                                                              *
         * ******************************************************************************************** *
         *       isFixed=true                                                                           *
         * ***************************                                                                  *
         * Input:    {1,1,1,0,0,0,0,1,1,1}                                                              *
         * Output:   {1,1,1,0,0,0,0,1,1,1} -no prefix                                                   *
         ************************************************************************************************
         **/

        //TODO: check prefix values
        //TODO: fix packing method such that won't convert to hexa.
        //TODO: NOT FINISHED!
        if (isFixed) {
            return src;
        }

        else {
            byte[] prefixLength = packIntoPairsArray(getDigitsInBitesArrayForm(maxLength));

            return byteArraysConcat(prefixLength,src);
        }
    }
}