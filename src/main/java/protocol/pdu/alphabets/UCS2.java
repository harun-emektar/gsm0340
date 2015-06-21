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

package protocol.pdu.alphabets;

/**
 * Decode UCS2 encoded SM.
 */
public class UCS2 {
    /**
     * @param octets UCS2 encoded SM.
     * @return Short Message.
     */
    public static String decodeSM(byte[] octets) {
        StringBuilder sb = new StringBuilder(octets.length / 2);
        for (int i = 0; i < octets.length / 2; ++i) {
            char c = (char) ((octets[i * 2 + 1] & 0xff) | (octets[i * 2] << 8 & 0xff00));
            sb.append(c);
        }
        return sb.toString();
    }
}
