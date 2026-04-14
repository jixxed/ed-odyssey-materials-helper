/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
