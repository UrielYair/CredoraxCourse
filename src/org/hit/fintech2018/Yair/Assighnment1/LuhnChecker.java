package org.hit.fintech2018.Yair.Assighnment1;

public class LuhnChecker implements ILuhnChecker
{
    public LuhnChecker(){
    }

    @Override
    public byte getLuhnDigit(byte[] data) throws Exception
    {
        if (data==null) throw new Exception("data is null.");
        byte sum=0;

        for (int i = 0 ; i<data.length-1  ; i++)
        {
            int positionByLuhnIndexing = data.length-i-1;
            if (data[i]<0 || data[i]>9 )
                throw new Exception("digit must be at range 0-9");
            if (positionByLuhnIndexing%2!=0)
            {
                if (data[i] * 2 > 9)
                    sum += (byte) ((data[i] * 2) - 9);
                else
                    sum += (byte) ((data[i] * 2));
            }
            else    sum += data[i];
        }
        //System.out.println("sum was " + sum);
        return (byte) ((10-(sum%10))%10);
    }

    @Override
    public boolean isLuhnValid(byte[] data) throws Exception
    {
        return (getLuhnDigit(data)== data[data.length-1]);
    }

}
