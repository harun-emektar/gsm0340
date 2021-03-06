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

import protocol.pdu.exceptions.InvalidParameter;
import protocol.pdu.representations.AddressFields;
import protocol.pdu.exceptions.BadOctetsLength;

/**
 * TP-Recipient-Address field indicates the address of the SME that was the destination of
 * the previously submitted mobile originated short message being subject to the status report.
 */
public class RA {
    /**
     * @param octets    Address field octets
     * @return Address field.
     * @throws BadOctetsLength thrown when length of octets is less than defined in the specification.
     * @throws InvalidParameter thrown when octets doesn't conform the specification.
     */
    public static AddressFields decode(byte[] octets) throws BadOctetsLength, InvalidParameter {
        return AddressFields.decode(octets);
    }
}
