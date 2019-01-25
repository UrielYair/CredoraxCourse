package org.hit.fintech2018.Yair.Converters;

import java.util.Base64;
public class AlphaNumeric extends AbstractConverter
{

    @Override
    public byte[] convertToHexByte(int maxLength) {
        // TODO: maybe delete this class
        Base64.Encoder base64 = Base64.getEncoder();
        return base64.encode(this.getData());
    }
}
