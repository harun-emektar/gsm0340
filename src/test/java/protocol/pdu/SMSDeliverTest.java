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
import protocol.pdu.parameters.ud.UD;
import protocol.pdu.representations.AddressFields;

import javax.xml.bind.DatatypeConverter;

/**
 * Testing SMS-DELIVER PDU
 */
public class SMSDeliverTest {
    @Test
    public void testDecodeDefaultSM() throws Exception {
        String str = "0004912143000051601071828100A054741914AFA7C76B9058FEBEBB41E6371EA4AEB7E16532E86D2FCB41ECB03E0F22BFCF2E2ABA0C8AD7D3E335482C7FDFDD20F31B0F52D7DBF03219F4B697E52076589F0791DF6717155D06C5EBE9F11A2496BFEF6E90F98D07A9EB6D78990C7ADBCB72103BACCF83C8EFB38B8A2E83E2F5F4780D12CBDF7737C8FCC683D4F536BC4C06BDED6539881DD6E741";
        byte[] bytes = DatatypeConverter.parseHexBinary(str);
        SMSDeliver del = new SMSDeliver(bytes);
        Assert.assertEquals(del.getMti(), MTI.SMS_DELIVER);
        Assert.assertEquals(del.isMms(), true);
        Assert.assertEquals(del.isRp(), false);
        Assert.assertEquals(del.isUdhi(), false);
        Assert.assertEquals(del.isSri(), false);
        AddressFields af = del.getOa();
        Assert.assertEquals(af.getAddress(), "1234");
        Assert.assertEquals(af.getTypeOfNumber(), AddressFields.INTERNATIONAL_NUMBER);
        Assert.assertEquals(af.getNumberingPlan(), AddressFields.ISDN_TELEPHONE_NUMBERING_PLAN);
        PID pid = del.getPid();
        Assert.assertEquals(pid.isTelematic(), false);
        Assert.assertEquals(pid.isNoInterworking(), true);
        Assert.assertEquals(pid.isReplaceShortMessage(), false);
        DCS dcs = del.getDcs();
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        Assert.assertEquals(dcs.isCompressed(), false);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DEFAULT_ALPHABET);
        UD ud = del.getUd();
        Assert.assertEquals(ud.getUdhl(), 0);
        Assert.assertEquals(ud.getIeMap().size(), 0);
        Assert.assertEquals(ud.getSm(), "The quick brown fox jumped over lazy dog.The quick brown fox jumped over lazy dog.The quick brown fox jumped over lazy dog.The quick brown fox jumped over lazy ");
    }

    @Test
    public void testDecodeUCS2() throws Exception {
        String str = "00049121430008516012022471008C00E70131011F00F6015F00FC00E200540068006500200071007500690063006B002000620072006F0077006E00200066006F00780020006A0075006D0070006500640020006F0076006500720020006C0061007A007900200064006F0067002E00E70131011F00F6015F00FC00E200540068006500200071007500690063006B002000620072006F0077006E";
        byte[] bytes =DatatypeConverter.parseHexBinary(str);
        SMSDeliver del = new SMSDeliver(bytes);
        Assert.assertEquals(del.getMti(), MTI.SMS_DELIVER);
        Assert.assertEquals(del.isMms(), true);
        Assert.assertEquals(del.isRp(), false);
        Assert.assertEquals(del.isUdhi(), false);
        Assert.assertEquals(del.isSri(), false);
        AddressFields af = del.getOa();
        Assert.assertEquals(af.getAddress(), "1234");
        Assert.assertEquals(af.getTypeOfNumber(), AddressFields.INTERNATIONAL_NUMBER);
        Assert.assertEquals(af.getNumberingPlan(), AddressFields.ISDN_TELEPHONE_NUMBERING_PLAN);
        PID pid = del.getPid();
        Assert.assertEquals(pid.isTelematic(), false);
        Assert.assertEquals(pid.isNoInterworking(), true);
        Assert.assertEquals(pid.isReplaceShortMessage(), false);
        DCS dcs = del.getDcs();
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        Assert.assertEquals(dcs.isCompressed(), false);
        Assert.assertEquals(dcs.getAlphabet(), DCS.UCS2);
        UD ud = del.getUd();
        Assert.assertEquals(ud.getUdhl(), 0);
        Assert.assertEquals(ud.getIeMap().size(), 0);
        Assert.assertEquals(ud.getSm(), "çığöşüâThe quick brown fox jumped over lazy dog.çığöşüâThe quick brown");
    }
}