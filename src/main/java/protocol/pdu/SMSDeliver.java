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

package protocol.pdu;

import protocol.pdu.exceptions.InvalidParameter;
import protocol.pdu.parameters.*;
import protocol.pdu.parameters.ud.UD;
import protocol.pdu.representations.AddressFields;
import protocol.pdu.exceptions.BadOctetsLength;
import protocol.pdu.exceptions.NotApplicable;

import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * SMS Deliver used for Mobile Terminated Short Messages
 */
public class SMSDeliver {
    // TP-MTI: message type indicator
    private int mti;
    // TP-MMS: more messages to send
    private boolean mms;
    // TP-RP: reply path
    private boolean rp;
    // TP-UDHI: user data header indicator
    private boolean udhi;
    // TP-SRI: status report indication
    private boolean sri;
    // TP-OA: originating address
    private AddressFields oa;
    // TP-PID: protocol identifier
    private PID pid;
    // TP-DCS: data coding scheme
    private DCS dcs;
    // TP-SCTS: service center time stamp
    private GregorianCalendar scts;
    // TP-UDL: user data lenght
    private int udl;
    // TP-UD: user data
    private UD ud;

    public SMSDeliver(byte [] octets) throws BadOctetsLength, InvalidParameter, NotApplicable {
        int i = 0;
        mti = MTI.decode(octets[i]);
        mms = MMS.decode(octets[i]) == MMS.MORE_MESSAGES;
        rp = RP.decode(octets[i]) == RP.IS_SET;
        udhi = UDHI.decode(octets[i]) == UDHI.CONTAINS_HEADER;
        sri = SRI.decode(octets[i++]) == SRI.WILL_BE_RETURNED;
        oa = OA.decode(Arrays.copyOfRange(octets, i, i + 12));
        i += 2 + oa.getOctetsLength();
        pid = PID.decode(octets[i++]);
        dcs = DCS.decode(octets[i++]);
        scts = SCTS.getTimeStamp(Arrays.copyOfRange(octets, i, i + 7));
        i += 7;
        udl = UDL.decode(octets[i++]); // depending upon encoding. number of octets/septets
        ud = UD.decode(Arrays.copyOfRange(octets, i, i + udl), udl, dcs, udhi);
    }

    /**
     * @return TP-MTI
     */
    public int getMti() {
        return mti;
    }

    /**
     * @return TP-MMS
     */
    public boolean isMms() {
        return mms;
    }

    /**
     * @return TP-RP
     */
    public boolean isRp() {
        return rp;
    }

    /**
     * @return TP-UDHI
     */
    public boolean isUdhi() {
        return udhi;
    }

    /**
     * @return TP-SRI
     */
    public boolean isSri() {
        return sri;
    }

    /**
     * @return TP-OA
     */
    public AddressFields getOa() {
        return oa;
    }

    /**
     * @return TP-PID
     */
    public PID getPid() {
        return pid;
    }

    /**
     * @return TP-DCS
     */
    public DCS getDcs() {
        return dcs;
    }

    /**
     * @return TP-SCTS
     */
    public GregorianCalendar getScts() {
        return scts;
    }

    /**
     * @return TP-UDL
     */
    public int getUdl() {
        return udl;
    }

    /**
     * @return TP-UD
     */
    public UD getUd() {
        return ud;
    }
}
