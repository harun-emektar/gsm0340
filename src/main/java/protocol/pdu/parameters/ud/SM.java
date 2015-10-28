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

package protocol.pdu.parameters.ud;

import protocol.pdu.alphabets.Default;
import protocol.pdu.alphabets.UCS2;
import protocol.pdu.parameters.DCS;
import protocol.pdu.representations.Septets;
import protocol.pdu.exceptions.NotApplicable;

/**
 * Class for decoding Short Message.
 */
public class SM {

    /**
     * @param octets octets of encoded SM.
     * @param dcs Data Coding Scheme used when SM encoded.
     * @param septetOffset Offset of septet where SM starts. Applicable when SM encoded with 7-bit Default alphabet.
     * @param septetLength Number of septet that SM contains. Applicable when SM encoded with 7-bit Default alphabet.
     * @return Short Message
     * @throws NotApplicable thrown when dcs doesn't have relevant alphabet information.
     */
    public static String decode(byte[] octets, DCS dcs, int septetOffset, int septetLength) throws NotApplicable {
        if (dcs.getAlphabet() == DCS.DEFAULT_ALPHABET) {
            // data is in 7 bit default alphabet
            byte[] fromSeptetsToOctets = Septets.septets2Octets(octets, septetOffset, septetLength);
            return Default.decodeSM(fromSeptetsToOctets);
        }
        else if (dcs.getAlphabet() == DCS.UCS2) {
            return UCS2.decodeSM(octets);
        }
        return "";
    }
}
