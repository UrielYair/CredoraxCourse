package org.hit.fintech2018.Yair.Converters;

public abstract class AbstractConverter
{
    private byte[] data;

    public AbstractConverter() {}

    public AbstractConverter(byte[] data) {
        this.data = data;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data){
        this.data=data;
    }
    public abstract byte[] convertToHexByte(int maxLength);

}
