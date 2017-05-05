/* 
 * Copyright (C) 2017 CROCS, Faculty of Informatics, Masaryk University (crocs.muni.cz)
 *           (C) 2017 Tomáš Zelina (xzelina1@fi.muni.cz)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package cz.muni.fi.crocs.javacard.ndefOtpGenerator;

/**
 * 
 * @author zelitomas
 */
public class UtilBase32 {
    public static byte base32toByte(byte input){
        if(input >= 'A' && input <= 'Z'){
            return (byte) (input - 'A');
        }else if(input >= '2' && input <= '7'){
            return (byte) (input - '2' + 26);
        }
        return 0;
    }
    
    private static byte shiftByte(byte n, short shift){
        if(shift > 0){
            return (byte) (n << shift);
        }else{
            return (byte) (n >> -shift);
        }
    }
    
    public static byte byteBuffer;
    public static short base32toByteArray(byte[] input, short inOffset, short inLength,
            byte output[], short outOffset){
        short outputBitCounter = 0, outputByteCounter, outputByteOffset;
        for(short i  = inOffset; i < (short) (inOffset + inLength); i++){
            byteBuffer = base32toByte(input[i]);
            if(input[i] == '='){
                return (short)(outputBitCounter / 8);
            }
            outputByteCounter = (short)(outputBitCounter / 8);
            outputByteOffset = (short)(outputBitCounter % 8);
            output[outputByteCounter] = (byte) 
                    (output[outputByteCounter] | 
                    shiftByte(byteBuffer, (short)(3 - outputByteOffset)));
            if((outputByteOffset) > 3){
                output[(short)((outputBitCounter / 8) + 1)] = (byte) 
                    (output[(short)(outputByteCounter + 1)] | 
                    shiftByte(byteBuffer, (short)(11 - outputByteOffset)));
            }
            outputBitCounter = (short) (outputBitCounter + 5);
        }
        byteBuffer = 0;
        return (short)(outputBitCounter / 8);
    }
}
