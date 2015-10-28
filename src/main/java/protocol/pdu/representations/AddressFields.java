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

import protocol.pdu.exceptions.InvalidParameter;
import protocol.pdu.exceptions.BadOctetsLength;

import java.util.Arrays;

/**
 * Address fields representation.
 */
public class AddressFields {
    // type of numbers
    public static final int UNKNOWN_NUMBER_TYPE = 0;
    public static final int INTERNATIONAL_NUMBER = 1;
    public static final int NATIONAL_NUMBER = 2;
    public static final int NETWORK_SPECIFIC_NUMBER = 3;
    public static final int SUBSCRIBER_NUMBER = 4;
    // coded according to GSM TS 03.38 7-bit default alphabet
    public static final int ALPHANUMERIC = 5;
    public static final int ABBREVIATED_NUMBER = 6;
    public static final int RESERVED_NUMBER_TYPE = 7;

    // numbering plan identifications(applies for type-of-number=0,1,2
    public static final int UNKNOWN_NUMBERING_PLAN = 0;
    public static final int ISDN_TELEPHONE_NUMBERING_PLAN = 1;// E.164/E.163
    public static final int DATA_NUMBERING_PLAN = 3; // X.121
    public static final int TELEX_NUMBERING_PLAN = 4;
    public static final int NATIONAL_NUMBERING_PLAN = 8;
    public static final int PRIVATE_NUMBERING_PLAN = 9;
    public static final int ERMES_NUMBERING_PLAN = 10; // ETSI DE/PS 3 01-3
    public static final int RESERVED_NUMBERING_PLAN = 15;

    public static final int MAX_NUMBER_OF_OCTETS = 12; // address length + type of address + address

    /**
     * @param octets    octets contain address fields
     * @return decoded address fields.
     * @throws BadOctetsLength thrown when length of octets is less than minimum value specified.
     * @throws InvalidParameter thrown when fields doesn't conform specification.
     */
    public static AddressFields decode(byte [] octets) throws BadOctetsLength, InvalidParameter {
        // validate values
        if (octets.length < 2) {
            throw new BadOctetsLength("Bad number of octets for Address Fields. It should be at least 2 octets.");
        }
        if (octets[0] > (MAX_NUMBER_OF_OCTETS - 2) * 2) {
            String msg = "Address Length shouldn't be more then %d.";
            throw new InvalidParameter(msg, (MAX_NUMBER_OF_OCTETS - 2) * 2);
        }
        if (octets[0] > (octets.length - 2) * 2) {
            String msg = "Address Length (%d) is greater than number of Address-Value octets (%d) passed.";
            throw new InvalidParameter(msg, octets[0], octets.length - 2);
        }
        AddressFields af = new AddressFields();
        af.addressLength = octets[0];
        af.typeOfAddress = octets[1];
        af.typeOfNumber = (af.typeOfAddress >> 4) & 0x07;
        af.numberingPlan = af.typeOfAddress & 0x0f;
        af.octetsLength = af.addressLength / 2 + (af.addressLength % 2 > 0 ? 1 : 0);
        af.address = SemiOctet.decode(
                Arrays.copyOfRange(octets, 2, 2 + af.octetsLength)).substring(0, af.addressLength);
        return af;
    }

    /**
     * @return Type of Address octet.
     */
    public byte getTypeOfAddress() {
        return typeOfAddress;
    }

    /**
     * @return whether {@link #UNKNOWN_NUMBER_TYPE}, {@link #INTERNATIONAL_NUMBER}, {@link #NATIONAL_NUMBER},
     * {@link #NETWORK_SPECIFIC_NUMBER}, {@link #SUBSCRIBER_NUMBER}, {@link #ALPHANUMERIC}, {@link #ABBREVIATED_NUMBER},
     * or {@link #RESERVED_NUMBER_TYPE}
     */
    public int getTypeOfNumber() {
        return typeOfNumber;
    }

    /**
     * @return whether {@link #UNKNOWN_NUMBERING_PLAN}, {@link #ISDN_TELEPHONE_NUMBERING_PLAN}, {@link #DATA_NUMBERING_PLAN},
     * {@link #TELEX_NUMBERING_PLAN}, {@link #NATIONAL_NUMBERING_PLAN}, {@link #PRIVATE_NUMBERING_PLAN}, {@link #ERMES_NUMBERING_PLAN}
     * or {@link #RESERVED_NUMBERING_PLAN}
     */
    public int getNumberingPlan() {
        return numberingPlan;
    }

    /**
     * @return Address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return Address Length octet.
     */
    public int getOctetsLength() {
        return octetsLength;
    }

    protected AddressFields(){}

    private byte addressLength; // number of useful semi-octets excluding fill bits
    private int octetsLength;
    private byte typeOfAddress;
    private int typeOfNumber;
    private int numberingPlan;
    private String address;
}
