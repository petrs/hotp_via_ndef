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
    
    /**
     * Compares first n bytes of array two arrays.
     * @param a array a
     * @param aOffset start index of array a
     * @param b array b
     * @param bOffset start index of array b
     * @param n number of bytes to comare
     * @return true if first n bytes of a and b are same, false there is a different byte or end of array is reached
     */
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
    
    /**
     * Finds first occurence of byte n in byte array.
     * @param a Byte array ot search in
     * @param aOffset Offset in byte array
     * @param toFind Searched byte
     * @return -1 if not found, index of first occurence otherway
     */
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
