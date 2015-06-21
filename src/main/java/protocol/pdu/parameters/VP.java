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

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TP-Validity-Period (Relative Format) comprises 1 octet in integer representation, giving the lenth of the validity period,
 * counted from when SMS-SUBMIT is received by SC.
 * <p>TP-Validity-Period (Absolute Format) comprises 7 octets in semi octet representation giving the absolute time of the validity period termination.</p>
 * <p>TP-Validity-Period (Enhanced Format) comprises 7 octets. The presence of all octets is mandatory although they may not all be used. The first octet
 * indicated the way in which the following 6 octets are used. Any reserved/unused bits of octets must be set to zero.</p>
 */
public class VP {
    private static final long MSEC_IN_MINS = 60 * 1000;
    private static final long MSEC_IN_30MINS = 30 * MSEC_IN_MINS;
    private static final long MSEC_IN_HOUR = 2 * MSEC_IN_30MINS;
    private static final long MSEC_IN_12HOURS = 12 * MSEC_IN_HOUR;
    private static final long MSEC_IN_DAY = 2 * MSEC_IN_12HOURS;
    private static final long MSEC_IN_WEEK = 7 * MSEC_IN_DAY;

    /**
     * @param octet    octet
     * @return relative time.
     */
    public static Date getRelativeTime(byte octet) {
        int o = octet & 0xff;
        if (o < 144) {
            return new Date(++o * 5 * MSEC_IN_MINS);
        }
        else if (o < 168) {
            return new Date((o - 143) * MSEC_IN_30MINS + MSEC_IN_12HOURS);
        }
        else if (o < 197) {
            return new Date((o - 166) * MSEC_IN_DAY);
        }
        return new Date((o - 192) * MSEC_IN_WEEK);
    }

    /**
     * @param octets    7 semi octets
     * @return absolute time in Gregorian Calendar
     * @throws BadOctetsLength thrown when length of octets is less than defined in the specification.
     */
    public static GregorianCalendar getAbsoluteTime(byte[] octets) throws BadOctetsLength {
        return SCTS.getTimeStamp(octets);
    }

    // TODO enhanced format implementation
    public static class EnhancedFormat {
        public boolean isExtended() {
            return false;
        }

        public boolean isSingleShot() {
            return false;
        }

        public Date getRelativeTime() {
            return null;
        }
    }
}
