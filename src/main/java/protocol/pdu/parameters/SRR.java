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
 * The TP-Status-Report-Request is a 1-bit field, located within bit no. 5 of the
 * first octet of SMS-SUBMIT and SMS-COMMAND.
 */
public class SRR {
    /**
     * A status report is not requested.
     */
    public static final int NOT_REQUESTED = 0;
    /**
     * A status report is requested.
     */
    public static final int REQUESTED = 1;

    private int value;

    public SRR(int value) {
        this.value = value;
    }

    /**
     * Decodes TP-Status-Report-Request from an octet.
     * @param octet first octet of PDU.
     * @return whether {@link #REQUESTED} or {@link #NOT_REQUESTED}.
     */
    public static int decode(byte octet) {
        return octet >> 5 & 0x01;
    }

    @Override
    public String toString() {
        switch (value) {
            case NOT_REQUESTED:
                return "A status report is not requested";
            case REQUESTED:
                return "A status report is requested";
            default:
                return "invalid"; // TODO better throw exception
        }
    }
}
