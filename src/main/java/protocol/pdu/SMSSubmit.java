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

import protocol.pdu.exceptions.BadOctetsLength;
import protocol.pdu.exceptions.InvalidParameter;
import protocol.pdu.parameters.ud.UD;
import protocol.pdu.representations.AddressFields;
import protocol.pdu.exceptions.NotApplicable;
import protocol.pdu.parameters.*;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * SMS Submit used for Mobile Originated Short messages
 */
public class SMSSubmit {
    // TP-MTI: message type indicator
    private int mti;
    // TP-RD: reject duplicates
    private boolean rd;
    // TP-VPF: validity period format
    private int vpf;
    // TP-RP: reply path
    private boolean rp;
    // TP-UDHI: user data header indicator
    private boolean udhi;
    // TP-SSR: status report request
    private boolean ssr;
    // TP-MR: message reference
    private int mr;
    // TP-DA: destination address
    private AddressFields da;
    // TP-PID: protocol identifier
    private PID pid;
    // TP-DCS: data coding scheme
    private DCS dcs;
    // TP-VP: validity period
    private Date vpRel;
    private GregorianCalendar vpAbs;
    // TP-UDL: user data length
    private int udl;
    // TP-UD: user data
    private UD ud;

    /**
     * @param octets    octets of SMS-SUBMIT PDU
     * @return decoded SMS-SUBMIT.
     * @throws BadOctetsLength
     * @throws InvalidParameter
     * @throws NotApplicable
     */
    public static SMSSubmit decode(byte[] octets) throws BadOctetsLength, InvalidParameter, NotApplicable {
        SMSSubmit sub = new SMSSubmit();
        int i = 0;
        sub.mti = MTI.decode(octets[i]);
        sub.rd = RD.decode(octets[i]) == RD.REJECT;
        sub.vpf = VPF.decode(octets[i]);
        sub.rp = RP.decode(octets[i]) == RP.IS_SET;
        sub.udhi = UDHI.decode(octets[i]) == UDHI.CONTAINS_HEADER;
        sub.ssr = SRR.decode(octets[i++]) == SRR.REQUESTED;
        sub.mr = MR.decode(octets[i++]);
        sub.da = AddressFields.decode(Arrays.copyOfRange(octets, i, i + 12));
        i += sub.da.getOctetsLength() + 2;
        sub.pid = PID.decode(octets[i++]);
        sub.dcs = DCS.decode(octets[i++]);
        switch (sub.vpf) {
            case VPF.RELATIVE_FORMAT:
                sub.vpRel = VP.getRelativeTime(octets[i++]);
                break;
            case VPF.ABSOLUTE_FORMAT:
                sub.vpAbs = VP.getAbsoluteTime(Arrays.copyOfRange(octets, i, i + 7));
                i += 7;
                break;
            case VPF.ENHANCED_FORMAT:
                break;
        }
        sub.udl = UDL.decode(octets[i++]);
        sub.ud = UD.decode(Arrays.copyOfRange(octets, i, i + sub.udl), sub.udl, sub.dcs, sub.udhi);
        return sub;
    }

    /**
     * @return TP-MTI
     */
    public int getMti() {
        return mti;
    }

    /**
     * @return TP-RD
     */
    public boolean isRd() {
        return rd;
    }

    /**
     * @return TP-VPF
     */
    public int getVpf() {
        return vpf;
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
     * @return TP-SSR
     */
    public boolean isSsr() {
        return ssr;
    }

    /**
     * @return TP-MR
     */
    public int getMr() {
        return mr;
    }

    /**
     * @return TP-DA
     */
    public AddressFields getDa() {
        return da;
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
     * @return TP-VP (relative format)
     */
    public Date getVpRel() {
        return vpRel;
    }

    /**
     * @return TP-VP (absolute format)
     */
    public GregorianCalendar getVpAbs() {
        return vpAbs;
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
