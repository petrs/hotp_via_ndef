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

import javacard.security.MessageDigest;

/**
 * \brief Class generating HMAC and calculating HOTP on javacard
 * @author zelitomas
 */
public class HMACgenerator implements CodeGenerator{
    private byte[] k_ipad;
    private byte[] k_opad;
    private byte[] counter = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private byte[] shaBuffer;
    private byte[] outBuffer;
    private MessageDigest digest;
    private byte[] asciiBuffer;
    
    
    // TODO: Finish javadoc
    /**
     * 
     * @param key Shared secret to use when generating HMAC
     */
    public HMACgenerator(byte key[]){
        k_opad = new byte[64];
        k_ipad = new byte[64];
        shaBuffer = new byte[84];
        outBuffer = new byte[20];
        asciiBuffer = new byte[20];
        for (short i = (short) 0; i < (short) 64; i++){
            if(i < key.length){
                k_opad[i] = (byte) (key[i] ^ 0x5c);
                k_ipad[i] = (byte) (key[i] ^ 0x36);
            } else {
                k_opad[i] = (byte) (0x5c);
                k_ipad[i] = (byte) (0x36);
            }
        }
        digest = MessageDigest.getInstance(MessageDigest.ALG_SHA, true);
    }
    
    private void incrementCounter(){
        short i = 7;
        while(i >= 0){
            if(counter[i] != (byte) 0xFF){
                counter[i]++;
                return;
            }else{
                counter[i] = (byte) 0x00;
                // Keep incrementing the next number
                i--;
            }
        }
        
    }
    
    private static byte toAsciiHex(byte toAscii){
        if(toAscii <= 0x09){
            return (byte) (toAscii + '0');
        } else {
            return (byte) (toAscii + 'a' - 10);
        }
    }
    
    public byte[] generateAsciiHmac(byte[] asciiBuffer){
        byte toAscii[] = generateHmac();
        for(short i = (short) 0; i < 20; i++){
            asciiBuffer[(byte) (i*2)] = toAsciiHex((byte) ((toAscii[i] & 0xF0) >> 4));
            asciiBuffer[(byte) (i*2+1)] = toAsciiHex((byte) ((toAscii[i] & 0x0F)));
        }
        return asciiBuffer;
    }
    
    // Limitation: max 20 bytes data
    
    public byte[] generateHmac(){
        incrementCounter();
        digest.update(k_ipad, (short) 0, (short) k_ipad.length);
        digest.update(counter, (short) 0, (short) counter.length);
        digest.doFinal(shaBuffer, (short)0, (short) 0, outBuffer, (short)0);
        digest.update(k_opad, (short) 0, (short) k_opad.length);
        digest.update(outBuffer, (short) 0, (short) 20);
        digest.doFinal(shaBuffer, (short)0, (short) 0, outBuffer, (short)0);
        return outBuffer;
    }
    
    public byte[] generateHotp(byte[] asciiBuffer){
        byte toAscii[] = generateHmac();
        byte startPositionOfCode = (byte) 0;
        try{
            startPositionOfCode = (byte) (toAscii[19] & 0x0F);
        } catch (Exception e){
            asciiBuffer[0] = 'c';
            return asciiBuffer;
        }
        try{
            // Warning - outbuffer no longer contains valid hmac!!
            toAscii[startPositionOfCode] = (byte) (toAscii[startPositionOfCode] & 0x7F);
            UtilBCD.toBCD(toAscii, startPositionOfCode, (short) 4, asciiBuffer, (short) 0);
        } catch (Exception e){
            asciiBuffer[0] = 'b';
            return asciiBuffer;
        }
        try{
            UtilBCD.bcdToAscii(asciiBuffer, (short) 0, (short) 10);
        } catch (Exception e){
            asciiBuffer[0] = 'd';
            return asciiBuffer;
        }
        return asciiBuffer;
    }
    
    @Override
    public byte[] getAscii(){
        return this.generateHotp(asciiBuffer);
    }
}
