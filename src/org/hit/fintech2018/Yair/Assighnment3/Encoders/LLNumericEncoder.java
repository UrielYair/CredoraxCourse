package org.hit.fintech2018.Yair.Assighnment3.Encoders;

public class LLNumericEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{
        // Same method as encode in LNumeric.

        LNumericEncoder encoder = new LNumericEncoder();
        return encoder.encode(src,maxLength,isFixed);
    }
}
