package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class BitmapEncoder extends AbstractISO8583Encoder
{
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{
        /**
         * The method will get the byte array of the bitmap. (will be represented by a binary form)
         * and return it as hex bits packed (nibbles).
         * */
        // TODO: FINISHED!
        binaryValidation(src);
        if (src.length!=64) throw new Exception("the bitmap must be exactly 64 bits long");
        byte[] hexArray = binaryToHex(src);
        return packIntoPairsArray(hexArray);
    }
}
