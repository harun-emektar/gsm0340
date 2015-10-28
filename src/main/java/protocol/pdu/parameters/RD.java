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
 * TP-Reject-Duplicates is a 1 bit field located within bit 2 of the first octet of
 * SMS-SUBMIT.
 */
public class RD {
    // instruct the SC to accept an SMS-SUBMIT for an SM still
    // held in the SC which has the same TP-MR and the same TP-DA
    // as a previously submitted SM from the same OA
    public static int ACCEPT = 0;
    // instruct the SC to reject an SMS-SUBMIT for an SM still
    // held in the SC which has the same TP-MR and the same TP-DA
    // as  previously submitted SM from the same OA. In this case
    // an appropriate TP-FCS value will be returned in the
    // SMS-SUBMIT-REPORT
    public static int REJECT = 1;

    /**
     * @param octet    First octet of SMS-SUBMIT
     * @return whether {@link #ACCEPT} ot {@link #REJECT}
     */
    public static int decode(byte octet) {
        return octet >> 2 & 0x1;
    }
}
