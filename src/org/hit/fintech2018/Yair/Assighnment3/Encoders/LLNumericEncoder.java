package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.Assighnment3.Encoders.HelpingMethods.numericEncoding;

public class LLNumericEncoder extends AbstractISO8583Encoder
{
    private final int numOfLs = 2;

    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{
        // Same method as encode in LNumeric.

        return numericEncoding(src,maxLength,isFixed,numOfLs);
    }
}
