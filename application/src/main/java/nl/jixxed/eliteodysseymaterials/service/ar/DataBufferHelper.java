package nl.jixxed.eliteodysseymaterials.service.ar;

import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class DataBufferHelper {
    public static byte[] getData(DataBuffer dataBuffer) {
        if( dataBuffer instanceof DataBufferByte){
            return ((DataBufferByte) dataBuffer).getData();
        } else if( dataBuffer instanceof DataBufferInt){
            final int[] intData = ((DataBufferInt) dataBuffer).getData();
            final byte[] byteData = new byte[intData.length * 4];
            for (int i = 0; i < intData.length; i++) {
                byteData[i * 4] = (byte) ((intData[i] >> 24) & 0xFF);     // Alpha
                byteData[i * 4 + 1] = (byte) ((intData[i] >> 16) & 0xFF); // Red
                byteData[i * 4 + 2] = (byte) ((intData[i] >> 8) & 0xFF);  // Green
                byteData[i * 4 + 3] = (byte) (intData[i] & 0xFF);         // Blue
            }
            return byteData;
        } else {
            throw new IllegalArgumentException("Unsupported DataBuffer type: " + dataBuffer.getClass().getName());
        }
    }
}
