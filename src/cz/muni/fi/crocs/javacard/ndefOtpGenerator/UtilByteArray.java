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
