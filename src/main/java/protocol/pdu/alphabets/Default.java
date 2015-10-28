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
 * Class for decoding Default Alphabet to String(unicode).
 */
public class Default {
    private static final char[] charSet = new char[] {
            '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'Ç', '\012', 'Ø', 'ø', '\015', 'Å', 'å',
            '∆', '_', 'Φ', 'Γ', 'Λ', 'Ω', 'Π', 'Ψ', 'Σ', 'Θ', 'Ξ', ' ', 'Æ', 'æ', 'ß', 'É',
            ' ', '!', '"', '#', '¤', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
            '¡', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§',
            '¿', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à',
    };

    private static final byte escape = 0x1b;

    private static final char[] extension = new char[]{
            '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'Ç', '\u21a1', 'Ø', 'ø', '\015', 'Å', 'å',
            '∆', '_', 'Φ', 'Γ', '^', 'Ω', 'Π', 'Ψ', 'Σ', 'Θ', 'Ξ', ' ', 'Æ', 'æ', 'ß', 'É',
            ' ', '!', '"', '#', '¤', '%', '&', '\'', '{', '}', '*', '+', ',', '-', '.', '\\',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '[', '~', ']', '?',
            '|', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§',
            '¿', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à',
    };

    /**
     * @param octets decodes octets encoded with default alphabet into String
     * @return decoded Short Message
     */
    public static String decodeSM(byte[] octets) {
        StringBuilder sb = new StringBuilder(octets.length);
        for (int i = 0; i < octets.length; ++i) {
            switch (octets[i]) {
                case Default.escape:
                    if (i + 1 < octets.length) {
                        sb.append(extension[octets[i + 1]]);
                        i += 1;
                    }
                    break;
                default:
                    sb.append(charSet[octets[i]]);
            }
        }
        return sb.toString();
    }
}

