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

import protocol.pdu.exceptions.NotApplicable;

/**
 * TP-Data-Coding-Scheme indicates the data coding scheme of the TP-UD field, and may indicate a message class.
 */
public class DCS {
    /**
     * General Coding Groups
     */
    // Coding Groups
    public static final int GENERAL_CODING_GROUP = 0;
    /**
     * Message Wait Indication Group
     */
    public static final int MESSAGE_WAIT_INDICATION_GROUP = 1;// Message Waiting Indication Group
    /**
     * Data Coding Message Class Group
     */
    public static final int DATA_CODING_MESSAGE_CLASS_GROUP = 3;
    /**
     * Reserved Coding Group
     */
    public static final int RESERVED_CODING_GROUP = 4;

    /**
     * Default GSM Alphabet
     */
    // Alphabets
    public static final int DEFAULT_ALPHABET = 0;
    /**
     * 8-bit Data
     */
    public static final int DATA_8BIT = 1;
    /**
     * UCS2(16bit)
     */
    public static final int UCS2 = 2;

    /**
     * Class 0
     */
    // Message Classes
    public static final int CLASS_0 = 0;
    /**
     * Class 1 default meaning: ME-specific
     */
    public static final int CLASS_1 = 1; // Default Meaning: ME-specific
    /**
     * Class 2 SIM-specific message
     */
    public static final int CLASS_2 = 2;// SIM specific message
    /**
     * Class 3 default meaning: TE-specific (see GSM TS 07.05)
     */
    public static final int CLASS_3 = 3; // Default Meaning: TE specific(see GSM TS 07.05[8])

    /**
     * Voice Mail Message Waiting Indication
     */
    // Indication Type
    public static final int VOICE_MESSAGE_WAITING = 0;
    /**
     * Fax Message Waiting
     */
    public static final int FAX_MESSAGE_WAITING = 1;
    /**
     * Electronic Mail Message Waiting
     */
    public static final int ELECTRONIC_MAIL_MESSAGE_WAITING = 2;
    /**
     * Other Message Waiting
     */
    public static final int OTHER_MESSAGE_WAITING = 3;

    private byte value;

    /**
     * @param value    dcs value
     * @return decoded DCS instance.
     */
    public static DCS decode(byte value) {
        return new DCS(value);
    }

    private DCS(byte value) {
        this.value = value;
    }

    /**
     * @return Whether SM is compressed.
     * @throws NotApplicable thrown when coding group is specified other than General Coding Group
     */
    public boolean isCompressed() throws NotApplicable {
        int group = getCodingGroup();
        if (group == GENERAL_CODING_GROUP) {
            return (value & 0x20) != 0;
        }
        throw new NotApplicable("Compressed flag is only applicable on General Data Coding Group");
    }

    /**
     * @return Alphabet used for encoding Short Message
     * @throws NotApplicable thrown when coding group is specified as Reserved Coding Group
     */
    public int getAlphabet() throws NotApplicable {
        int group = getCodingGroup();
        switch (group) {
            case GENERAL_CODING_GROUP:
                return value >> 2 & 0x3;
            case MESSAGE_WAIT_INDICATION_GROUP:
                byte gb = (byte) (value >> 4 & 0xf  );
                if (gb == 0xc || gb == 0xd) {
                    return DEFAULT_ALPHABET;
                }
                return UCS2;
            case DATA_CODING_MESSAGE_CLASS_GROUP:
                return value >> 2 & 0x1;
            default:
                throw new NotApplicable("Reserved coding group doesn't have alphabet indication");
        }
    }

    /**
     * @return Specified coding group
     */
    public int getCodingGroup(){
        byte group = (byte) (value >> 4 & 0xf);
        switch (group) {
            case 0xf:// Data coding/messaging class
                return DATA_CODING_MESSAGE_CLASS_GROUP;
            case 0xe:
            case 0xd:
            case 0xc:
                return MESSAGE_WAIT_INDICATION_GROUP;
            case 0x0:
            case 0x1:
            case 0x2:
            case 0x3:
                return GENERAL_CODING_GROUP;
            default:
                return RESERVED_CODING_GROUP;
        }
    }

    /**
     * @return Message class used
     * @throws NotApplicable thrown when coding group is other than General Coding Group and Data Coding/Message Class
     */
    public int getMessageClass() throws NotApplicable {
        int group = getCodingGroup();
        if (group == GENERAL_CODING_GROUP && (value & 0x10) != 0) {
            return value & 0x03;
        }
        if (group == DATA_CODING_MESSAGE_CLASS_GROUP) {
            return value & 0x03;
        }
        throw new NotApplicable("Message Class only applicaple on General Coding Group and Data Coding/Message Class");
    }

    /**
     * @return Whether SM to be stored or discarded.
     * @throws NotApplicable thrown when Coding Group is other than Message Waiting Indication Group
     */
    public boolean isStoreMessage() throws NotApplicable {
        int group = getCodingGroup();
        if (group == MESSAGE_WAIT_INDICATION_GROUP) {
            byte gb = (byte) (value >> 4 & 0xf);
            if (gb == 0xd || gb == 0xe) {
                return true;
            }
            return false;
        }
        throw new NotApplicable("Message is not in Message Waiting Group");
    }

    /**
     * @return Whether SM to be discarded or stored.
     * @throws NotApplicable thrown when Coding Group is other than Message Waiting Indication Group
     */
    public boolean isDiscardMessage() throws NotApplicable {
        int group = getCodingGroup();
        if (group == MESSAGE_WAIT_INDICATION_GROUP) {
            byte gb = (byte) (value >> 4 & 0xf);
            if (gb == 0xc) {
                return true;
            }
            return false;
        }
        throw new NotApplicable("Message is not in Mesage Waiting Group");
    }

    /**
     * @return Whether Indication is Active or Inactive.
     * @throws NotApplicable thrown when Coding Group is other than Message Waiting Indication Group.
     */
    public boolean isIndicationActive() throws NotApplicable {
        int group = getCodingGroup();
        if (group == MESSAGE_WAIT_INDICATION_GROUP) {
            return (value & 0x8) != 0;
        }
        throw new NotApplicable("Message Waiting Indication Group only has indication flag");
    }
}
