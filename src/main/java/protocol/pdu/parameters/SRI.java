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
 * The TP-Status-Report-Indication is a 1bit field, located within bit no.5 of the
 * first octet of SMS-DELIVER.
 */
public class SRI {
    /**
     * A status report will not be returned to the SME.
     */
    public static final int WONT_BE_RETURNED = 0;
    /**
     * A status report will be returned to the SME
     */
    public static final int WILL_BE_RETURNED = 1;

    private int value;

    public SRI(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (value){
            case WONT_BE_RETURNED:
                return "A status report will not be returned to the SME";
            case WILL_BE_RETURNED:
                return "A status report will be returned to the SME";
            default:
                return "invalid";
        }
    }

    /**
     * @param octet    first octet of SMS-DELIVER.
     * @return whether {@link #WILL_BE_RETURNED} or {@link #WONT_BE_RETURNED}.
     */
    public static int decode(byte octet) {
        return octet >> 5 & 0x01;
    }
}
