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

import javacard.framework.ISOException;
import javacard.framework.Util;
import javacard.security.MessageDigest;

/**
 * \brief Class generating HMAC and calculating HOTP on javacard
 * @author zelitomas
 */
public class HMACgenerator implements CodeGenerator, AuthProvider{
    private static final short counterSize = 8;
    private static final short maxCheckOffset = 10;


    private byte[] k_ipad;
    private byte[] k_opad;
    private byte[] counter;
    private byte[] counterBeforeCheck;
    private boolean checking = false;
    private byte[] shaBuffer;
    private byte[] outBuffer;
    private MessageDigest digest;
    private byte[] asciiBuffer;
    private byte[] outputCodeDigits;
    private short digits;
    
    
    // TODO: Finish javadoc
    /**
     * 
     * @param key Shared secret to use when generating HMAC
     * @param keyLen Length of shared secret in bytes
     * @param digits Numbers of digits to generate
     */
    public HMACgenerator(byte key[], short keyLen, short digits){
        
        //Set counter to 0
        counter = new byte[counterSize];
        Util.arrayFillNonAtomic(counter, (short) 0, counterSize, (byte) 0);
        
        k_opad = new byte[64];
        k_ipad = new byte[64];
        shaBuffer = new byte[84];
        outBuffer = new byte[20];
        this.digits = digits;
        asciiBuffer = new byte[10];
        outputCodeDigits = new byte[digits];
        for (short i = (short) 0; i < (short) 64; i++){
            if(i < keyLen){
                k_opad[i] = (byte) (key[i] ^ 0x5c);
                k_ipad[i] = (byte) (key[i] ^ 0x36);
            } else {
                k_opad[i] = (byte) (0x5c);
                k_ipad[i] = (byte) (0x36);
            }
        }
        digest = MessageDigest.getInstance(MessageDigest.ALG_SHA, true);
        
    }
    
    public HMACgenerator(byte key[], short keyLen){
        this(key, keyLen, (short) 6);
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
    
    public byte[] generateHotp(byte[] output){
        byte toAscii[] = generateHmac();
        byte startPositionOfCode = (byte) 0;
        startPositionOfCode = (byte) (toAscii[19] & 0x0F);
        
        // Warning - outbuffer no longer contains valid hmac!!
        toAscii[startPositionOfCode] = (byte) (toAscii[startPositionOfCode] & 0x7F);
        UtilBCD.toBCD(toAscii, startPositionOfCode, (short) 4, asciiBuffer, (short) 0);
        UtilBCD.bcdToAscii(asciiBuffer, (short) 0, (short) 10);
        Util.arrayCopyNonAtomic(asciiBuffer, (short) (10 - digits), output, (short) 0, digits);
        return output;
    } 
   
    @Override
    public byte[] getAscii(){
        return this.generateHotp(outputCodeDigits);
    }
    
    @Override
    public boolean check(byte[] ascii, short offset, short maxLen){
        
        if(checking){
            resetCounter();
        }
        
        // Check length of provided data
        if(maxLen < digits){
            return false;
        }    
        // Make backup of counter
        if(counterBeforeCheck == null){
            counterBeforeCheck = new byte[counterSize];
        }
        Util.arrayCopyNonAtomic(counter, (short) 0, counterBeforeCheck, (short) 0, counterSize);
        checking = true;
        for(short i = 0; i < maxCheckOffset; i++){
            if(UtilByteArray.compareByteArrays(getAscii(), (short) 0, ascii, offset, digits)){
                checking = false;
                return true;
            }
        }
        
        resetCounter();
        return false;
    }
    
    private void resetCounter(){
        Util.arrayCopyNonAtomic(counterBeforeCheck, (short) 0, counter, (short) 0, counterSize);
        checking = false;
    }
}
