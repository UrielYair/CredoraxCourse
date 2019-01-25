package org.hit.fintech2018.Yair.Converters;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.leftPadding;

public class NumericConverter extends AbstractConverter
{
    public NumericConverter() {
        super();
    }

    @Override
    public byte[] convertToHexByte(int maxLength) {
        // TODO: maybe delete this class
        byte[] arrayToReturn = new byte[maxLength];
        arrayToReturn = leftPadding(this.getData(),maxLength);
        return arrayToReturn;
    }
}
