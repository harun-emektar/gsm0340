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
 * TP-Validity-Period-Format is a 2 bit field, located within bit no 3 and 4 of the first octet of SMS-SUBMIT.
 */
public class VPF {
    /**
     * TP-VP field not present.
     */
    public static final int NOT_PRESENT = 0;
    /**
     * TP-VP field present - enhanced format.
     */
    public static final int ENHANCED_FORMAT = 1;
    /**
     * TP-VP field present - relative format.
     */
    public static final int RELATIVE_FORMAT = 2;
    /**
     * TP-VP field present - absolute format.
     */
    public static final int ABSOLUTE_FORMAT = 3;

    private int value;

    public VPF(int value) {
        this.value = value;
    }

    /**
     * @param octet    first octet of SMS-SUBMIT
     * @return whether {@link #NOT_PRESENT}, {@link #ENHANCED_FORMAT}, {@link #RELATIVE_FORMAT}, {@link #ABSOLUTE_FORMAT}.
     */
    public static int decode(byte octet) {
        return octet >> 3 & 0x3;
    }

    public String toString() {
        switch (value){
            case NOT_PRESENT:
                return "TP-VP field not present";
            case ENHANCED_FORMAT:
                return "TP-VP field present - enhanced format";
            case RELATIVE_FORMAT:
                return "TP-VP field present - relative format";
            case ABSOLUTE_FORMAT:
                default:
                return "TP-VP field present - absolute format";
        }
    }
}
