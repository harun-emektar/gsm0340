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

package protocol.pdu.representations;

/**
 * Class for Transforming septets to octets
 */
public class Septets {
    /**
     * @param septets    array includes septets.
     * @param offset     offset first septet.
     * @param length     length of resulting octets.
     * @return array of octets
     */
    public static byte[] septets2Octets(byte[] septets, int offset, int length) {
        int ol = length * 7 / 8 + (length * 7 % 8 == 0 ? 0 : 1); // octets length
        byte [] octets = new byte[length];
        for (int i = 0; i < length; ++i) {
            int si = (offset + i * 7) / 8; // septet index
            int sv = (offset + i * 7) % 8; // shift value
            short tmp = (short) (septets[si] & 0xff | (si + 1 < septets.length ? septets[si + 1] : 0) << 8 & 0xff00);
            octets[i] = (byte) (tmp >> sv & 0x7f);
        }
        return octets;
    }

    public static byte[] octets2Septets(byte[] octets, int offset) {
        return null;
    }
}
