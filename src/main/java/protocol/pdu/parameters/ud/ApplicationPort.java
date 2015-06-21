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
import protocol.pdu.exceptions.InvalidParameter;

/**
 * Application Port (8/16-bit) Information Element.
 */
public class ApplicationPort extends AbsIE {
    public static final int NUMBER_OF_OCTETS_16BIT = 4;
    public static final int NUMBER_OF_OCTETS_8BIT = 2;
    private short srcPort;
    private short dstPort;

    /**
     * @param id ID of 8 or 16 bit Application Port Information Element.
     * @param length length of the information element.
     * @param data octets of the information element.
     * @throws BadOctetsLength thrown when length is less than specified.
     * @throws InvalidParameter thrown when id is other than ID of 8/16 bit Application Port Information Element.
     */
    public ApplicationPort(int id, byte length, byte[] data) throws BadOctetsLength, InvalidParameter {
        super(id, length, data);
        if (id != AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT &&
                id != AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_16BIT) {
            throw new InvalidParameter("Information Element ID doesn't match. Expected (%d) or (%d), actual (%d)",
                    AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT,
                    AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_16BIT,
                    id);
        }
        if (id == AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT && length < NUMBER_OF_OCTETS_8BIT) {
            throw new BadOctetsLength("Not enough data for decoding Concatenated SM Information Element. Expected (%d) actual (%d)",
                    NUMBER_OF_OCTETS_8BIT, length);
        }
        if (id == AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_16BIT && length < NUMBER_OF_OCTETS_16BIT) {
            throw new BadOctetsLength("Not enough data for decoding Concatenated SM Information Element. Expected (%d) actual (%d)",
                    NUMBER_OF_OCTETS_16BIT, length);
        }
        if (id == AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT) {
            srcPort = (short) (data[0] & 0xff);
            dstPort = (short) (data[1] & 0xff);
            return;
        }
        dstPort = (short) ((short)data[1] << 8 | (short)(data[0] & 0xff));
        srcPort = (short) ((short)data[3] << 8 | (short)(data[2] & 0xff));
    }

    /**
     * @return source port of Application Port Information Element.
     */
    public short getSrcPort() {
        return srcPort;
    }

    /**
     * @return destination port of Application Port Information Element.
     */
    public short getDstPort() {
        return dstPort;
    }
}
