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

import protocol.pdu.exceptions.BadOctetsLength;
import protocol.pdu.exceptions.NotApplicable;
import protocol.pdu.parameters.DCS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User Data which contains Information Elements and Short Message
 */
public class UD {
    private int udhl;
    private Map<Integer, AbsIE> ieMap;
    private String sm;

    /**
     * @param userData    Octets of User Data
     * @param udl         User Data Length
     * @param dcs         Data Coding Scheme
     * @param udhi        User Data Header Indicator
     * @return User Data with Information Elements and Short Message decoded.
     * @throws NotApplicable thrown when dcs doesn't have information related to alphabet used
     * @throws BadOctetsLength thrown when userData doesn't have enough octets
     */
    public static UD decode(byte[] userData, int udl, DCS dcs, boolean udhi) throws NotApplicable, BadOctetsLength {
        int i = 0;
        int udhl = 0;
        Map<Integer, AbsIE> ieMap = new HashMap<>();
        int smLen;
        int septetOffset = 0;
        int septetLength = 0;
        if (udhi) {
            udhl = userData[i] & 0xff;
            ieMap = UDH.decode(userData);
        }
        if (dcs.getAlphabet() == DCS.DEFAULT_ALPHABET) {
            // convert to octets
            smLen = udl * 7 / 8 + (udl * 7 % 8 == 0 ? 0 : 1);
            int tudhl = udhi ? udhl + 1 : 0;
            smLen -= tudhl;
            // following values are in septets
            tudhl = tudhl * 8 / 7 + (tudhl * 8 % 7 == 0 ? 0 : 1);
            septetOffset = tudhl * 7 % 8;
            septetLength = udl - tudhl;
        } else {
            smLen = udl - (udhi ? udhl - 1 : 0);
        }
        i += udhi ? udhl + 1 : 0;
        String sm = SM.decode(Arrays.copyOfRange(userData, i, i + smLen), dcs, septetOffset, septetLength);
        UD ud = new UD();
        ud.udhl = udhl;
        ud.ieMap = ieMap;
        ud.sm = sm;
        return ud;
    }

    /**
     * @return User Data Header Length
     */
    public int getUdhl() {
        return udhl;
    }

    /**
     * @return Map of Information Elements with ID as key
     */
    public Map<Integer, AbsIE> getIeMap() {
        return ieMap;
    }

    /**
     * @return Short Message
     */
    public String getSm() {
        return sm;
    }
}
