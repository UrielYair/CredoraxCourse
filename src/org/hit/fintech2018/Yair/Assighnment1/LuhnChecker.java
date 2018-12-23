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

        for (int i = 0 ; i<data.length  ; i++)
        {
            int positionByLuhnIndexing = data.length-i;
            if (data[i]<0 || data[i]>9 )    throw new Exception("digit must be at range 0-9");
            if (positionByLuhnIndexing%2!=0)     sum += data[i];
            else    sum += (byte) ((data[i] * 2)%9);
        }
        return (byte) (sum%10);
    }

    @Override
    public boolean isLuhnValid(byte[] data) throws Exception
    {
        return (getLuhnDigit(data)==0);
    }

}
