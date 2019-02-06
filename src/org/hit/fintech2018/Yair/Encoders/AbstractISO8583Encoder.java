package org.hit.fintech2018.Yair.Encoders;

public abstract class AbstractISO8583Encoder
{

    public AbstractISO8583Encoder() {}

    public abstract byte[] encode(byte[] src, int maxLength);

}
