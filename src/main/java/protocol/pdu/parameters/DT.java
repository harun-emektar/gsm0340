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

import java.util.GregorianCalendar;

/**
 * TP-Discharge Time field indicates the time at which a previous l submitted SMS-SUBMIT was successfully
 * delivered to or attempted to deliver to the recipient SME or disposed of by the SC
 */
public class DT {
    /**
     * @param value    octets
     * @return Discharge Time as absolute Gregorian Calendar Time
     * @throws BadOctetsLength thrown when octets length is less than defined in the specification.
     */
    // same representation as SCTS
    public static GregorianCalendar getTimeStamp(byte[] value) throws BadOctetsLength {
        return SCTS.getTimeStamp(value);
    }
}
