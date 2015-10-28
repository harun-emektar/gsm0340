
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
 * The TP-Message-Type-Indicator is 2bit field, located within bits no 0 and 1
 * of the first octet of all PDUs.
 */
public class MTI {
    /**
     * SMS-DELIVER (in the direction SC to MS)
     */
    public static final int SMS_DELIVER = 0x00;
    /**
     * SMS-DELIVER-REPORT (in the direction MS to SC)
     */
    public static final int SMS_DELIVER_REPORT = 0x00;
    /**
     * SMS-SUBMIT (in the direction MS to SC)
     */
    public static final int SMS_SUBMIT = 0x01;
    /**
     * SMS-SUBMIT-REPORT (in the direction SC to MS)
     */
    public static final int SMS_SUBMIT_REPORT = 0x01;
    /**
     * SMS-COMMAND (in the direction MS to SC)
     */
    public static final int SMS_COMMAND = 0x02;
    /**
     * SMS-STATUS-REPORT (in the direction SC to MS)
     */
    public static final int SMS_STATUS_REPORT = 0x02;
    /**
     * Reserved
     */
    public static final int RESERVED = 0x03;

    public static final int SC_TO_MS = 0;
    public static final int MS_TO_SC = 1;

    private int value;
    private int direction;

    public MTI(int value, int direction) {
        this.value = value;
        this.direction = direction;
    }

    @Override
    public String toString() {
        switch (value) {
            case SMS_DELIVER:
                return direction == SC_TO_MS ? "SMS-DELIVER" : "SMS-DELIVER-REPORT";
            case SMS_SUBMIT:
                return direction == MS_TO_SC ? "SMS-SUBMIT" : "SMS-SUBMIT-REPORT";
            case SMS_COMMAND:
                return  direction == MS_TO_SC ? "SMS-COMMAND" : "SMS-STATUS-REPORT";
            case RESERVED:
            default:
                return "Reserved";
        }
    }

    /**
     * Decodes TP-MTI of a PDU
     * @param octet first octet of PDU
     * @return {@link #SMS_DELIVER}, {@link #SMS_DELIVER_REPORT}, {@link #SMS_STATUS_REPORT}, {@link #SMS_COMMAND},
     * {@link #SMS_SUBMIT_REPORT} or {@link #RESERVED}
     */
    public static int decode(byte octet) {
        return octet & 0x3;
    }
}
