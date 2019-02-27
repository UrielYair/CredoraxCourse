package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;
import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.byteArraysConcat;

public class HelpingMethods
{
    public static byte[] alphanumericEncoding(byte[] src, int maxLength, boolean isFixed, int numOfLs) throws Exception {
        byte[] arrayToReturn = null;

        if (isFixed) {
            arrayToReturn = rightPadding(src, maxLength, ' ');
            return packIntoPairsArray(arrayToReturn);
        }
        else {
            // Concatenation of length as prefix
            // followed by the value of the data element.

            byte[] prefixLength = getPrefixForInputLengthBetween_L_LL_LLL(src.length, numOfLs);

            if (src.length % 2 != 0) src = rightPadding(src, src.length + 1, ' ');
            src = packIntoPairsArray(src);

            return byteArraysConcat(prefixLength, src);

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
