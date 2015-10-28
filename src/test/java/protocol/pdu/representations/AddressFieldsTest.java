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

import org.testng.Assert;
import org.testng.annotations.Test;
import protocol.pdu.exceptions.BadOctetsLength;
import protocol.pdu.exceptions.InvalidParameter;

/**
 * Testing address fields representation.
 */
public class AddressFieldsTest {
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullOctets() throws Exception {
        AddressFields.decode(null);
    }

    @Test(expectedExceptions = BadOctetsLength.class)
    public void testBadOctetsLength1() throws Exception {
        byte[] octets = new byte[] {};
        AddressFields.decode(octets);
    }

    @Test(expectedExceptions = BadOctetsLength.class)
    public void testBadOctetsLength2() throws Exception {
        byte[] octets = new byte[] {0};
        AddressFields.decode(octets);
    }

    @Test(expectedExceptions = InvalidParameter.class)
    public void testInvalidLengthParam1() throws Exception {
        byte[] octets = new byte[]{1, 0};
        AddressFields.decode(octets);
    }

    @Test(expectedExceptions = InvalidParameter.class)
    public void testInvalidLengthParam2() throws Exception {
        byte[] octets = new byte[]{21, 0};
        AddressFields.decode(octets);
    }

    @Test
    public void testUnknownNumberFormat() throws Exception {
        byte[] octets = new byte[]{3, 0, 0x21, (byte) 0xf3};
        AddressFields af = AddressFields.decode(octets);
        Assert.assertEquals(af.getTypeOfAddress(), 0);
        Assert.assertEquals(af.getTypeOfNumber(), AddressFields.UNKNOWN_NUMBER_TYPE);
        Assert.assertEquals(af.getNumberingPlan(), AddressFields.UNKNOWN_NUMBERING_PLAN);
        Assert.assertEquals(af.getAddress(), "123");
    }

    @Test
    public void testIInternationalE164NumberMaxLen() throws Exception {
        byte[] octets = new byte[]{20, (byte) 0x91, 0x21, 0x43, 0x65, (byte) 0x87, 0x09, 0x21, 0x43, 0x65, (byte) 0x87, 0x09};
        AddressFields af = AddressFields.decode(octets);
        Assert.assertEquals(af.getTypeOfAddress(), (byte)0x91);
        Assert.assertEquals(af.getTypeOfNumber(), AddressFields.INTERNATIONAL_NUMBER);
        Assert.assertEquals(af.getNumberingPlan(), AddressFields.ISDN_TELEPHONE_NUMBERING_PLAN);
        Assert.assertEquals(af.getAddress(), "12345678901234567890");
    }
}