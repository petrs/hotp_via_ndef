/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.crocs.javacard.ndefOtpGenerator;

/**
 * Util for string-like operation on byte array.
 * @author zelitomas
 */
public class UtilByteArray {
    public static boolean compareByteArrays(byte a[], short aOffset, byte b[], short bOffset, short n){
        try {
            while (n > 0) {
                if (a[(short) (n - 1 + aOffset)] != b[(short) (n - 1 + bOffset)]) {
                    return false;
                }
                n--;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    
    public static short findFirstOccurence(byte a[], short aOffset, byte toFind){
        while(aOffset < a.length){
            if(a[aOffset] == toFind){
                return aOffset;
            }
            aOffset++;
        }
        return -1;
    }
}
