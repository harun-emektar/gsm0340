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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User Data header which includes Information Elements
 */
public class UDH {
    // max number of user data header size in octets
    public static final int MAX_UDH_SIZE = 140;

    /**
     * @param octets    User Header octets
     * @return Map of Information Elements with ID as key
     * @throws BadOctetsLength
     */
    public static Map<Integer, AbsIE> decode(byte[] octets) throws BadOctetsLength {
        int len = octets[0];
        if (octets.length < len + 1) {
            throw new BadOctetsLength("Not enough octets to decode User Data Header. Expected (%d), Actual (%d)",
                    len + 1, octets.length);
        }
        Map<Integer, AbsIE> header = new HashMap<>();
        int ieLen;
        for (int i = 1; i < len; i += ieLen) {
            int ieId = octets[i++];
            ieLen = octets[i++];
            byte[] data = Arrays.copyOfRange(octets, i, i + ieLen);
            Class c = getIEClass(ieId);
            if (c != null) {
                try {
                    Constructor cstr = c.getConstructor(int.class, byte.class, data.getClass());
                    AbsIE ie = (AbsIE) cstr.newInstance(ieId, (byte) ieLen, data);
                    header.put(ieId, ie);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    // TODO log error
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            // TODO log skipped IE
        }
        return header;
    }

    /**
     * @param ieId    Information Element ID
     * @return Class of Information Element
     */
    private static Class getIEClass(int ieId) {
        switch (ieId) {
            case AbsIE.CONCATENATED_SHORT_MESSAGES:
                return ConcatenatedSM.class;
            case AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT:
            case AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_16BIT:
                return ApplicationPort.class;
        }
        return null;// unsupported IE
    }
}
