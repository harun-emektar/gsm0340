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
 * The TP-User-Data-Header-Indicator is a 1 bit field within bit 6 of the first of an
 * SMS-SUBMIT and SMS-DELIVER PDU.
 */
public class UDHI {

    /**
     * The TP-UD field contains only the short message.
     */
    public static final int ONLY_SHORT_MESSAGE = 0;
    /**
     * The beginning of the TP-UD field contains a Header in addition to the short message.
     */
    public static final int CONTAINS_HEADER = 1;

    /**
     * @param octet    first octet of SMS-SUBMIT or SMS-DELIVER
     * @return whether {@link #CONTAINS_HEADER} or {@link #ONLY_SHORT_MESSAGE}.
     */
    public static int decode(byte octet) {
        return octet >> 6 & 0x01;
    }
}
