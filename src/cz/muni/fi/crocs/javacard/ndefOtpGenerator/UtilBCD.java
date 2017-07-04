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

import javacard.framework.Util;

/**
 * 
 * @author zelitomas
 */
public class UtilBCD {
    private static final byte bcdValues[] = {
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x01,   //2^0
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x02,   //2^1
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x04,   //2^2
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x08,   //2^3
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x01,  0x06,   //2^4
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x03,  0x02,   //2^5
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x06,  0x04,   //2^6
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x01,  0x02,  0x08,   //2^7
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x02,  0x05,  0x06,   //2^8
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x05,  0x01,  0x02,   //2^9
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x01,  0x00,  0x02,  0x04,   //2^10
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x02,  0x00,  0x04,  0x08,   //2^11
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x04,  0x00,  0x09,  0x06,   //2^12
        0x00,  0x00,  0x00,  0x00,  0x00,  0x00,  0x08,  0x01,  0x09,  0x02,   //2^13
        0x00,  0x00,  0x00,  0x00,  0x00,  0x01,  0x06,  0x03,  0x08,  0x04,   //2^14
        0x00,  0x00,  0x00,  0x00,  0x00,  0x03,  0x02,  0x07,  0x06,  0x08,   //2^15
        0x00,  0x00,  0x00,  0x00,  0x00,  0x06,  0x05,  0x05,  0x03,  0x06,   //2^16
        0x00,  0x00,  0x00,  0x00,  0x01,  0x03,  0x01,  0x00,  0x07,  0x02,   //2^17
        0x00,  0x00,  0x00,  0x00,  0x02,  0x06,  0x02,  0x01,  0x04,  0x04,   //2^18
        0x00,  0x00,  0x00,  0x00,  0x05,  0x02,  0x04,  0x02,  0x08,  0x08,   //2^19
        0x00,  0x00,  0x00,  0x01,  0x00,  0x04,  0x08,  0x05,  0x07,  0x06,   //2^20
        0x00,  0x00,  0x00,  0x02,  0x00,  0x09,  0x07,  0x01,  0x05,  0x02,   //2^21
        0x00,  0x00,  0x00,  0x04,  0x01,  0x09,  0x04,  0x03,  0x00,  0x04,   //2^22
        0x00,  0x00,  0x00,  0x08,  0x03,  0x08,  0x08,  0x06,  0x00,  0x08,   //2^23
        0x00,  0x00,  0x01,  0x06,  0x07,  0x07,  0x07,  0x02,  0x01,  0x06,   //2^24
        0x00,  0x00,  0x03,  0x03,  0x05,  0x05,  0x04,  0x04,  0x03,  0x02,   //2^25
        0x00,  0x00,  0x06,  0x07,  0x01,  0x00,  0x08,  0x08,  0x06,  0x04,   //2^26
        0x00,  0x01,  0x03,  0x04,  0x02,  0x01,  0x07,  0x07,  0x02,  0x08,   //2^27
        0x00,  0x02,  0x06,  0x08,  0x04,  0x03,  0x05,  0x04,  0x05,  0x06,   //2^28
        0x00,  0x05,  0x03,  0x06,  0x08,  0x07,  0x00,  0x09,  0x01,  0x02,   //2^29
        0x01,  0x00,  0x07,  0x03,  0x07,  0x04,  0x01,  0x08,  0x02,  0x04,   //2^30
        0x02,  0x01,  0x04,  0x07,  0x04,  0x08,  0x03,  0x06,  0x04,  0x08,   //2^31
    };
    
    static private byte buffer[] = new byte[10];
    
    private static void add(byte[] dest, byte[] a, short aOffset){
        short destIndex = (short) 0;
        short aIndex;
        byte carry = (byte) 0;
        for(short i = (short) 0; i < (short) 10; i++){
            destIndex = (short)(dest.length - 1 - i);
            aIndex = (short)(aOffset + 9 - i);
            dest[destIndex] = (byte) (dest[destIndex] + a[aIndex] + carry);
            if(dest[destIndex] > 0x09){
                dest[destIndex] = (byte) (dest[destIndex] - 10);
                carry = (byte)1;
            }else{
                carry = (byte)0;
            }
        }
        if(carry == 1){
            dest[(short) (destIndex - 1)]++;
        }
    }
    
    /**
     * Converts up to 8 byte value to BCD array.
     * Lenght of array + outOffset should be always >= 10.
     * Number is considered to be big-endian.
     * Output array can be same as input array.
     * 
     * @param input Byte array containing number ot convert
     * @param inOffset Start index of number in input array
     * @param inLenght Length of number in bytes
     * @param out Byte array that will be filled with BCD values. 
     * @param outOffset Start index of output array.
     * @return Number of digits. Curretly always 10.
     */
    public static short toBCD(byte[] input, short inOffset, short inLenght, byte[] out, short outOffset){
        Util.arrayFillNonAtomic(buffer, (short) 0, (short) buffer.length, (byte) 0x00);
        for(short index = inOffset; index < (short)(inOffset + inLenght); index++){
            short bytePosition = (short)(index - inOffset);
            short bytePositionFromLeft = (short)(inLenght - bytePosition - 1);
            for(short bitPosition = 0; bitPosition < 8; bitPosition++){
                if(((input[index] >> (7 - bitPosition)) & 1) > 0){
                    add(buffer, bcdValues, (short)((bytePositionFromLeft * 8 + (7 - bitPosition)) * 10));
                }
                
            }
        }
        Util.arrayCopyNonAtomic(buffer, (short) 0, out, outOffset, (short) 10);
        Util.arrayFillNonAtomic(buffer, (short) 0, (short) buffer.length, (byte) 0); // Cleaning buffer, as static variables are not protected by firewall
        return 10; //TODO
    }
    
    /**
     * Converts one byte from ASCII to BCD 
     * @param input BCD value
     * @return ASCII value
     */
    public static byte asciiToBcd(byte input){
        if(input >= '0' && input <= '9'){
            return (byte) (input - '0');
        } else if(input >= 'a' && input <= 'f'){ // propably a hex value??
            return (byte) (input - 'a' + 10);
        }
        throw new ArithmeticException();
        
    }
    
    /**
     * Converts one byte from BCD to ASCII
     * @param input BCD value
     * @return ASCII value
     */
    public static byte bcdToAscii(byte input){
        if(input <= 0x09){
            return (byte) (input + '0');
        } else { // propably a hex value??
            return (byte) (input + 'a' - 10);
        }
        
    }
    
    /**
     * Converts BCD array to ASCII array.
     * Output is saved into the same array. (input array is changed)
     * @param input Byte array containing number ot convert in BCD
     * @param inOffset Start index of number in input array
     * @param inLenght Length of number in bytes
     */
    public static void bcdToAscii(byte[] input, short inOffset, short inLenght){
        for(short index = inOffset; index < (short)(inOffset + inLenght); index++){
            input[index] = bcdToAscii(input[index]);
        }
    }
    
    /**
     * Converts array containing binary values to ASCII.
     * Output length should be at least twice the length of input.
     * @param input Byte array
     * @param inOffset Starting index of input
     * @param inLenght Length of input
     * @param output Array to store ASCII values
     * @param outOffset Offset in output array
     */
    public static void hexToAscii(byte[] input, short inOffset, short inLenght, byte[] output, short outOffset){
        for(short i = (short) 0; i < inLenght; i++){
            output[(byte) (i*2 + outOffset)] = bcdToAscii((byte) ((input[(short) (i + inOffset)] & 0xF0) >> 4));
            output[(byte) (i*2+1 + outOffset)] = bcdToAscii((byte) ((input[(short)(i + inOffset)] & 0x0F)));
        }
    }
    
    public static void hexToAscii(byte[] input, short inOffset, short inLenght, byte[] output){
        hexToAscii(input, inOffset, inLenght, output, (short) 0);
    }
    
    /**
     * Converts array containing ASCII values binary array.
     * Output length should be at least half the length of input.
     * @param input ASCII array
     * @param inOffset Starting index of input
     * @param inLenght Length of input
     * @param output Array to store binary values. Starts at 0
     */
    public static void asciiToHex(byte[] input, short inOffset, short inLenght, byte[] output){
        Util.arrayFillNonAtomic(output, (short) 0, (short) (inLenght/2 + inLenght % 2), (byte) 0);
        byte alignment = (byte) (inLenght % 2);
        byte shift, ialign;
        byte in;
        for(short i = (short) 0; i < inLenght; i++){
            ialign = (byte) ((byte)(i + alignment)/2);
            in = output[(byte)((byte)(i + alignment)/2)];
            if((short)((short)(i + alignment) % (short)2) == (short) 0){
                shift = (byte) 4;
            }else{
                shift = (byte) 0;
            }
            output[ialign] = (byte) (in | (byte)(asciiToBcd(input[(short)(inOffset + i)]) << shift));
        }
    }
    
}