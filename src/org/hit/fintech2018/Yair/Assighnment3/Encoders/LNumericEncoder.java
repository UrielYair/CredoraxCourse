package org.hit.fintech2018.Yair.Assighnment3.Encoders;
import static org.hit.fintech2018.Yair.Assighnment3.Encoders.HelpingMethods.*;

public class LNumericEncoder extends AbstractISO8583Encoder
{
    private final int numOfLs = 1;

    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{

        return numericEncoding(src,maxLength,isFixed,numOfLs);

    }
}
