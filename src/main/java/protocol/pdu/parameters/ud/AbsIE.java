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

package protocol.pdu.parameters.ud;

import protocol.pdu.exceptions.BadOctetsLength;

/**
 * Abstract class of Information Elements.
 */
public abstract class AbsIE {
    public static final int CONCATENATED_SHORT_MESSAGES = 0;
    public static final int SPECIAL_SMS_MESSAGE_INDICATION = 1;
    public static final int APPLICATION_PORT_ADDRESSING_SCHEME_8BIT = 4;
    public static final int APPLICATION_PORT_ADDRESSING_SCHEME_16BIT = 5;
    public static final int SMSC_CONTROL_PARAMETERS = 6;
    public static final int UDH_SOURCE_INDICATOR = 7;

    private int id;
    private byte length;

    /**
     * @param id        ID of the Information Element
     * @param length    number of octets that Information Elements has
     * @param data      octets of Information Element
     * @throws BadOctetsLength when length of data is less than length given.
     */
    protected AbsIE(int id, byte length, byte [] data) throws BadOctetsLength {
        if (data.length < length) {
            throw new BadOctetsLength("Not enough data to decode Information Element (%d). Expected (%d), actual(%d)",
                    id, length, data.length);
        }
        this.id = id;
        this.length = length;
    }

    /**
     * @return ID of Information Element
     */
    public int getId() {
        return id;
    }

    /**
     * @return number of octets that Information Element has.
     */
    public byte getLength() {
        return length;
    }
}
