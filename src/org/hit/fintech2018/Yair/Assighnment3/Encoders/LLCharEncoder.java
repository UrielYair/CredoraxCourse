package org.hit.fintech2018.Yair.Assighnment3.Encoders;

public class LLCharEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{
        // Same method as encode in LCharEncoder.

        LCharEncoder encoder = new LCharEncoder();
        return encoder.encode(src,maxLength,isFixed);
    }
}