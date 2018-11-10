package org.hit.fintech2018.Yair;

public interface ILuhnChecker
{
    byte getLuhnDigit(byte[] data) throws Exception;
    boolean isLuhnValid(byte[] data) throws Exception;
}
