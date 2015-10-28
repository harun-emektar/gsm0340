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

package protocol.pdu.parameters;

import protocol.pdu.exceptions.BadOctetsLength;
import protocol.pdu.representations.SemiOctet;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * TP-Service-Centre-Time-Stamp field is  given in semi-octet representation, and represents
 * the local time.
 */
public class SCTS {

    /**
     * @param value    Time stamp in semi octets.
     * @return local time in Gregorian Calendar.
     * @throws BadOctetsLength when length of octets is less than the specification.
     */
    public static GregorianCalendar getTimeStamp(byte[] value) throws BadOctetsLength {
        if (value.length != 7)
            throw new BadOctetsLength("Bad length of octets for SCTS. Given " + Integer.toString(value.length) + " octets.");
        int year = Integer.parseInt(SemiOctet.decode(value[0]));
        int month = Integer.parseInt(SemiOctet.decode(value[1]));
        int day = Integer.parseInt(SemiOctet.decode(value[2]));
        int hour = Integer.parseInt(SemiOctet.decode(value[3]));
        int minute = Integer.parseInt(SemiOctet.decode(value[4]));
        int second = Integer.parseInt(SemiOctet.decode(value[5]));
        TimeZone tz = SemiOctet.decodeTZ(value[6]);
        GregorianCalendar calVal = new GregorianCalendar(2000 + year, month - 1, day, hour, minute, second);
        calVal.setTimeZone(tz);
        return calVal;
    }
}
