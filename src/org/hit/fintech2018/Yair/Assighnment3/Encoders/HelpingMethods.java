package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.byteArraysConcat;

public class HelpingMethods
{
    public static byte[] alphanumericEncoding(byte[] src, int maxLength, boolean isFixed, int numOfLs) throws Exception {
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
            return byteArraysConcat(prefixLength, arrayToReturn);

        }
    }

    public static byte[] numericEncoding(byte[] src, int maxLength, boolean isFixed, int numOfLs) throws Exception {
        numericValidation(src);

        byte[] arrayToReturn = null;

        if (isFixed) {
            arrayToReturn = leftPadding(src, maxLength, '0');
            return packIntoPairsArray(arrayToReturn);
        }
        else {
            // Concatenation of length as prefix
            // followed by the value of the data element.

            byte[] prefixLength = getPrefixForInputLengthBetween_L_LL_LLL(src.length, numOfLs);

            if (src.length % 2 != 0) src = leftPadding(src, src.length + 1, '0');
            src = packIntoPairsArray(src);

            return byteArraysConcat(prefixLength, src);
        }

    }
}
