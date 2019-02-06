package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.leftPadding;

public class LNumericEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength) {
        //should be left padded with zeros.
        byte[] arrayToReturn = new byte[maxLength];
        arrayToReturn = leftPadding(src,maxLength);
        return arrayToReturn;
    }
}
