package org.hit.fintech2018.Yair.Assighnment3.Encoders;

import java.util.Arrays;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;

public class AmountEncoder extends AbstractISO8583Encoder
{
    /*
    * This class is used to work with amount fields in the ISO8583 message.
    * Amount structure will be as follows:
    * - First 3 bits are for a currency code - The currency code is based on ISO4217 standard.
    * - The other bits represent the amount of money of the specific currency.
    *
    * note: The standard defines the exponent of specific currency.
    * */

    @Override
    public byte[] encode(byte[] src, int maxLength, boolean isFixed) throws Exception{
        numericValidation(src);

        if (src.length<3)
            throw new Exception("The input source for the amount of certain currency is not valid.");

        // Extract values from input:
        byte[] currencyCode = Arrays.copyOfRange(src,0,2);
        byte[] amountOfMoneyRepresentedInBits = Arrays.copyOfRange(src,3,src.length);

        // Padding the extracted data to fit standard rules:
        currencyCode = leftPadding(currencyCode,4,'0');
        if (amountOfMoneyRepresentedInBits.length %2 != 0)
            amountOfMoneyRepresentedInBits = leftPadding(amountOfMoneyRepresentedInBits,amountOfMoneyRepresentedInBits.length+1,'0');

        // Checking if output should be adjusted because the field if fixed size:
        if (isFixed){
            int lengthOfAmountValue = maxLength - 4; // subtracting the first 4 bits which represents the currency.
            if (amountOfMoneyRepresentedInBits.length>lengthOfAmountValue)
                throw new Exception("The input have too many digits that represent the amount of money, " +
                        "than the defined length in the ISO8583 standard.");
            else
                amountOfMoneyRepresentedInBits = leftPadding(amountOfMoneyRepresentedInBits,lengthOfAmountValue,'0');
        }
        // Data concatenation:
        byte[] arrayToReturn = byteArraysConcat(currencyCode,amountOfMoneyRepresentedInBits);

        if (arrayToReturn.length > maxLength)
            throw new Exception("Value of amount of money have too many bits.");

        return arrayToReturn;
    }
}