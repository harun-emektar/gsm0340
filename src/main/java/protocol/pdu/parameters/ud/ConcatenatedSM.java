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
 * Concatenated SM Information Element
 */
public class ConcatenatedSM extends AbsIE {
    public static final int NUMBER_OF_OCTETS = 3;
    private byte referenceNumber;
    private byte maxShortMessages;
    private byte seqNumber;

    /**
     * @param id ID of the information Element
     * @param length length of octets
     * @param data octets
     * @throws BadOctetsLength thrown when length is less than specified.
     * @throws InvalidParameter thrown when id doesn't match specified.
     */
    public ConcatenatedSM(int id, byte length, byte[] data) throws BadOctetsLength, InvalidParameter {
        super(id, length, data);
        if (id != CONCATENATED_SHORT_MESSAGES) {
            throw new InvalidParameter("Information Element ID doesn't match. Expected (%d), actual (%d)",
                    CONCATENATED_SHORT_MESSAGES, id);
        }
        if (length < NUMBER_OF_OCTETS) {
            throw new BadOctetsLength("Not enough data for decoding Concatenated SM Information Element. Expected (%d) actual (%d)",
                    NUMBER_OF_OCTETS, length);
        }
        referenceNumber = data[0];
        maxShortMessages = data[1];
        seqNumber = data[2];
    }

    /**
     * @return Reference number of concatenated SM
     */
    public byte getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * @return Max number of concatenated parts.
     */
    public byte getMaxShortMessages() {
        return maxShortMessages;
    }

    /**
     * @return Sequence number of current part.
     */
    public byte getSeqNumber() {
        return seqNumber;
    }
}
