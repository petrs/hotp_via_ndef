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
 * Interface for classes providing authentication.
 * Only useful method is check. Typical usage: card locking using OTP or password.
 * @author zelitomas
 */
public interface AuthProvider {

    /**
     * Checks if provided code in ASCII is valid.
     * 
     * @return True if auth was successful, false otherwise
     */
    public boolean check(byte[] data, short offset, short maxLen);
}
