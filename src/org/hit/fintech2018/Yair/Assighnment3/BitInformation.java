package org.hit.fintech2018.Yair.Assighnment3;

public class BitInformation
{
    private Integer bitNumber;
    private int length;
    private String classPath;
    private String bitDescription;
    private boolean fixed;

    public BitInformation(Integer bitNumber, int length, String classPath, String bitDescription, boolean fixed) {
        this.bitNumber = bitNumber;
        this.length = length;
        this.classPath = classPath;
        this.bitDescription = bitDescription;
        this.fixed = fixed;
    }

    public Integer getBitNumber() {
        return bitNumber;
    }

    public int getLength() {
        return length;
    }

    public String getClassPath() {
        return classPath;
    }

    public String getBitDescription() { return bitDescription;  }

    public boolean getFixed()   {return fixed;}
}
