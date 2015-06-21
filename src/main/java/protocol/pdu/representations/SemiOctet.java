/*
 * Copyright (c) 2015. Harun Emektar
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package protocol.pdu.representations;

import java.util.TimeZone;

/**
 * Semi-octet representation.
 */
public class SemiOctet {
    /**
     * @param semiOctets    array of semi-octets.
     * @return number string.
     */
    public static String decode(byte [] semiOctets) {
        StringBuilder sb = new StringBuilder(semiOctets.length * 2);
        boolean firstSemiOctet = true;
        byte so;
out:
        for (byte b : semiOctets) {
            for (int i = 0; i < 2; i++) {
                if (firstSemiOctet)
                    so = (byte) (b & 0x0f);
                else
                    so = (byte) (b >> 4 & 0xf);
                firstSemiOctet = !firstSemiOctet;
                switch (so) {
                    case 0xa:
                        sb.append('*');
                        break;
                    case 0xb:
                        sb.append('#');
                        break;
                    case 0xc:
                        sb.append('a');
                        break;
                    case 0xd:
                        sb.append('b');
                        break;
                    case 0xe:
                        sb.append('c');
                        break;
                    case 0xf:
                        break out;
                    default:
                        sb.append(so);
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param semiOctets    2 semi-octets
     * @return number string
     */
    public static String decode(byte semiOctets) {
        StringBuilder sb = new StringBuilder(2);
        boolean firstSemiOctet = true;
        byte so;
        for (int i = 0; i < 2; i++) {
            if (firstSemiOctet)
                so = (byte) (semiOctets & 0x0f);
            else
                so = (byte) (semiOctets >> 4 & 0xf);
            firstSemiOctet = !firstSemiOctet;
            switch (so) {
                case 0xa:
                    sb.append('*');
                    break;
                case 0xb:
                    sb.append('#');
                    break;
                case 0xc:
                    sb.append('a');
                    break;
                case 0xd:
                    sb.append('b');
                    break;
                case 0xe:
                    sb.append('c');
                    break;
                case 0xf:
                    break;
                default:
                    sb.append(so);
            }
        }
        return sb.toString();
    }

    /**
     * @param semiOctets    time-zone offset in semi octets.
     * @return decoded {@link TimeZone}.
     */
    public static TimeZone decodeTZ(byte semiOctets) {
        int sign = semiOctets & 0x8;
        semiOctets &= 0xF7;
        int tzoff = Integer.parseInt(decode(semiOctets));
        StringBuilder tz = new StringBuilder("GMT");
        if (sign > 0) // then negative
            tz.append("-");
        else
            tz.append("+");
        tz.append(tzoff / 4);// hours
        if (tzoff % 4 > 0)
            tz.append(':').append(tzoff % 4 * 15);
        return TimeZone.getTimeZone(tz.toString());
    }
}
