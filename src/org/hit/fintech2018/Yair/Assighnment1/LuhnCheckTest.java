package org.hit.fintech2018.Yair.Assighnment1;

import static org.hit.fintech2018.Yair.HelpingMethods.Auxiliaries.*;
public class LuhnCheckTest
{
    public static void main(String[] args)
    {

        try {
            LuhnChecker luhnChecker = new LuhnChecker();
            //Check for Visa, Mastercard, Discover, American Express
            String[] cards = {"4201010294522212",
                    "4024007104829261",
                    "4532014403898182",
                    "4916296483650783",
                    "4024007157668707",
                    "5459621493173734",
                    "5560652126577179",
                    "5571373333863339",
                    "5457803541896738",
                    "5216693622898824",
                    "6011673335162796",
                    "6011159291084539",
                    "6011911805617489",
                    "6011623653601743",
                    "6011743302020614",
                    "344541500246465",
                    "376640414654490",
                    "373958410917932",
                    "373339490336816",
                    "371609213873804"};

            for (String cc : cards) {
                //System.out.println("the card number is: " + cc);
                byte[] strAsByteArray = stringToByteArray(cc);
                //System.out.println("the last digit is: " + cc.charAt(cc.length() - 1));
                //System.out.println("the luhn digit calculated: " + luhnChecker.getLuhnDigit(strAsByteArray));
                System.out.println("valid? " + luhnChecker.isLuhnValid(strAsByteArray));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

