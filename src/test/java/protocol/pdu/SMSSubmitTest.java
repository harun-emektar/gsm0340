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

import org.testng.Assert;
import org.testng.annotations.Test;
import protocol.pdu.parameters.DCS;
import protocol.pdu.parameters.MTI;
import protocol.pdu.parameters.PID;
import protocol.pdu.parameters.VPF;
import protocol.pdu.parameters.ud.UD;
import protocol.pdu.representations.AddressFields;

import javax.xml.bind.DatatypeConverter;

/**
 * Testing SMS-SUBMIT PDU
 */
public class SMSSubmitTest {

    @Test
    public void testDecode() throws Exception {
        String str = "0100049134120000A054741914AFA7C76B9058FEBEBB41E6371EA4AEB7E16532E86D2FCB41ECB03E0F22BFCF2E2ABA0C8AD7D3E335482C7FDFDD20F31B0F52D7DBF03219F4B697E52076589F0791DF6717155D06C5EBE9F11A2496BFEF6E90F98D07A9EB6D78990C7ADBCB72103BACCF83C8EFB38B8A2E83E2F5F4780D12CBDF7737C8FCC683D4F536BC4C06BDED6539881DD6E741";
        byte[] octets = DatatypeConverter.parseHexBinary(str);
        SMSSubmit sub = SMSSubmit.decode(octets);
        Assert.assertEquals(sub.getMti(), MTI.SMS_SUBMIT);
        Assert.assertEquals(sub.isRd(), false);
        Assert.assertEquals(sub.getVpf(), VPF.NOT_PRESENT);
        Assert.assertEquals(sub.isRp(), false);
        Assert.assertEquals(sub.isUdhi(), false);
        Assert.assertEquals(sub.isSsr(), false);
        Assert.assertEquals(sub.getMr(), 0);
        AddressFields da = sub.getDa();
        Assert.assertEquals(da.getNumberingPlan(), AddressFields.ISDN_TELEPHONE_NUMBERING_PLAN);
        Assert.assertEquals(da.getTypeOfNumber(), AddressFields.INTERNATIONAL_NUMBER);
        Assert.assertEquals(da.getTypeOfAddress(), (byte)0x91);
        Assert.assertEquals(da.getAddress(), "4321");
        PID pid = sub.getPid();
        Assert.assertEquals(pid.isTelematic(), false);
        Assert.assertEquals(pid.isNoInterworking(), true);
        Assert.assertEquals(pid.isReplaceShortMessage(), false);
        DCS dcs = sub.getDcs();
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DEFAULT_ALPHABET);
        Assert.assertEquals(dcs.isCompressed(), false);
        Assert.assertEquals(sub.getUdl(), 160);
        Assert.assertEquals(sub.getVpAbs(), null);
        Assert.assertEquals(sub.getVpRel(), null);
        UD ud = sub.getUd();
        Assert.assertEquals(ud.getIeMap().size(), 0);
        Assert.assertEquals(ud.getSm(), "The quick brown fox jumped over lazy dog.The quick brown fox jumped over lazy dog.The quick brown fox jumped over lazy dog.The quick brown fox jumped over lazy ");
    }
}