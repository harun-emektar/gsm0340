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

/**
 * TP-Protocol-Identifier parameter indicates protocol used at higher levels.
 */
public class PID {
    /**
     * implicit - device type is specific to this SC, or can be concluded on the bases of the address
     */
    // telematic interworking types
    public static final int IMPLICIT = 0;
    public static final int TELEX = 1;
    public static final int GROUP_3_TELEFAX = 2;
    public static final int GROUP_4_TELEFAX = 3;
    public static final int VOICE_TELEPHONE = 4;
    public static final int ERMES = 5;
    public static final int NATIONAL_PAGING_SYSTEM = 6;
    public static final int VIDEOTEX = 7;
    public static final int TELETEXT_CARRIER_UNSPECIFIED = 8;
    public static final int TELETEXT_IN_PSPDN = 9;
    public static final int TELETEXT_IN_CSPDN = 10;
    public static final int TELETEXT_IN_ANALOG_PSTN = 11;
    public static final int TELETEXT_IN_DIGITAL_ISDN = 12;
    public static final int UCI = 13;
    public static final int RESERVED_1 = 14;
    public static final int RESERVED_2 = 15;
    public static final int A_MESSAGE_HANDLING_FACILITY = 16;
    public static final int ANY_PUBLIC_X400_BASED_MESSAGE_HANDLING_SYSTEM = 17;
    public static final int INTERNET_ELECTRONIC_MAIL = 18;
    public static final int RESERVED_3 = 19;
    public static final int RESERVED_4 = 20;
    public static final int RESERVED_5 = 21;
    public static final int RESERVED_6 = 22;
    public static final int RESERVED_7 = 23;
    public static final int SC_SPECIFIC_1 = 24;
    public static final int SC_SPECIFIC_2 = 25;
    public static final int SC_SPECIFIC_3 = 26;
    public static final int SC_SPECIFIC_4 = 27;
    public static final int SC_SPECIFIC_5 = 28;
    public static final int SC_SPECIFIC_6 = 29;
    public static final int SC_SPECIFIC_7 = 30;
    public static final int A_GSM_MOBILE_STATION = 31;

    // replace short message types
    public static final int SHORT_MESSAGE_TYPE_0 = 0;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_1 = 1;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_2 = 2;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_3 = 3;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_4 = 4;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_5 = 5;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_6 = 6;
    public static final int REPLACE_SHORT_MESSAGE_TYPE_7 = 7;
    public static final int RETURN_CALL_MESSAGE = 0x1f;
    public static final int ME_DEPERSONALIZATION_SHORT_MESSAGE = 0x3e;
    public static final int SIM_DATA_DOWNLOAD = 0x3f;

    private byte value;

    /**
     * @param value    Protocol Identifier octet
     * @return Protocol Identifier
     */
    public static PID decode(byte value) {
        return new PID(value);
    }

    private PID(byte value) {
        this.value = value;
    }

    /**
     * @return whether TP-PID indicates telematic interworking.
     */
    public boolean isTelematic() {
        return ((value | 0x3f) == 0x3f) && ((value & 0x20) != 0);
    }

    /**
     * @return whether TP-PID indicates no interworking.
     */
    public boolean isNoInterworking() {
        return ((value | 0x3f) == 0x3f) && ((value | 0xdf) == 0xdf);
    }

    /**
     * @return types of telematic devices.
     */
    public int getTelematicType() {
        return value & 0x01f;
    }

    /**
     * @return whether replace short message type is defined.
     */
    public boolean isReplaceShortMessage() {
        return (value & 0x40) != 0;
    }

    /**
     * @return type of replace short message type.
     */
    public int getReplaceShortMessageType() {
        return value & 0x3f;
    }
}
