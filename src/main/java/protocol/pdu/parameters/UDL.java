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
 * If the TP-User-Data is coded using the GSM 7 bit default alphabet, the TP-User-Data-Length
 * field gives an integer representation of the number of characters (septets) within the
 * TP-User-Data field to follow.
 * If the TP-User-Data is coded using UCS2 data, the TP-User-Data-Length field gives an integer
 * representation of the number of octets within the TP-User-Data field to follow.
 */
public class UDL {
    /**
     * @param octet    octet of User Data Length.
     * @return value of User Data Length.
     */
    public static int decode(byte octet) {
        return octet & 0xff;
    }
}
