package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;

import java.io.IOException;

public class CommodityKeyDeserializer extends KeyDeserializer
{
    @Override
    public Object deserializeKey(final String key, final DeserializationContext ctxt ) throws IOException
    {
        return Commodity.forName(key);
    }
}