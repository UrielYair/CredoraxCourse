package org.hit.fintech2018.Yair.Assighnment3;

import java.util.Map;

public interface IISO8583Serializer
{
    byte[] serializeISO8583(int version, Map<Integer, byte[]> data);
}
