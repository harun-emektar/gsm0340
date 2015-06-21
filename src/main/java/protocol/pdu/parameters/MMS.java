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
 * TP-More-Messages-to-Send is a 1bit field, located within bit no 2 of the first
 * octet of SMS-DELIVER and SMS-STATUS-REPORT.
 */
public class MMS {
    /**
     * More messages are waiting for the MS in this SC
     */
    public static final int MORE_MESSAGES = 0;
    /**
     * No more messages are waiting for the MS in this SC
     */
    public static final int NO_MORE_MESSAGES = 1;

    private int value;

    public MMS(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (value) {
            case MORE_MESSAGES:
                return "More messages are waiting for the MS in this SC";
            case NO_MORE_MESSAGES:
                return "No more messates are wiating for the MS in this SC";
        }
        return "MMS{}";
    }

    /**
     * @param octet    first octet of SMS-DELIVER or SMS-STATUS-REPORT.
     * @return either {@link #MORE_MESSAGES} or {@link #NO_MORE_MESSAGES}.
     */
    public static int decode(byte octet) {
        return octet >> 2 & 0x01;
    }
}
