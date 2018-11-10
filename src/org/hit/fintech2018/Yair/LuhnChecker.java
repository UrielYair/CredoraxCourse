package org.hit.fintech2018.Yair;

public class LuhnChecker implements ILuhnChecker
{

    //TODO: fix throwing exceptions in case of detecting 09-90, 22-55, 33-66 or 44-77

    public LuhnChecker()
    {
    }

    @Override
    public byte getLuhnDigit(byte[] data) throws Exception
    {
        byte sum=0,doubledValue;

        for (int i = data.length-1 ; i >=0; i--)
        {
            if ((i+1)%2!=0)
                sum+=data[i];
            else {
                doubledValue = (byte) ((data[i] * 2)%9);
                sum+=doubledValue;
            }
        }
        return (byte) (sum%10);
    }

    @Override
    public boolean isLuhnValid(byte[] data) throws Exception
    {
        return (getLuhnDigit(data)==0);
    }

}
