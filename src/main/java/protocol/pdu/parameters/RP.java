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
 * TP-Reply-Path is 1-bit field, located within bit no 7 of the first octet of both
 * SMS-DELIVER and SMS-SUBMIT.
 */
public class RP {
    // TP-Reply-Path parameter is not set in this SMS-SUBMIT/DELIVER
    public static final int IS_NOT_SET = 0;
    // TP-Reply-Path parameter is set in this SMS-SUBMIT/DELIVER
    public static final int IS_SET = 1;

    /**
     * @param octet    first octet of SMS-DELIVER or SMS-SUBMIT
     * @return whether {@link #IS_NOT_SET} or {@link #IS_SET}
     */
    public static int decode(byte octet) {
        return octet >> 7 & 0x01;
    }
}
