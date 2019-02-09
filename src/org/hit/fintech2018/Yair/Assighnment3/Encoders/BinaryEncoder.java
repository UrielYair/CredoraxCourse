package org.hit.fintech2018.Yair.Assighnment3.Encoders;

public class BinaryEncoder extends AbstractISO8583Encoder
{
    //ASCII Binary
    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) {
        /*
         * if is not dividable in 8--> right padding with 0 so it be able to divide by 8.
         * divide to 8bit groups
         * toPairs() on each group
         * convert to hex
         * pack()
         * return
         * */


        return null;
    }
}