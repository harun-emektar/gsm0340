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

import org.testng.Assert;
import org.testng.annotations.Test;
import protocol.pdu.exceptions.BadOctetsLength;
import protocol.pdu.exceptions.InvalidParameter;

/**
 * Testing application port information element.
 */
public class ApplicationPortTest {
    @Test(expectedExceptions = InvalidParameter.class)
    public void testInvalidId() throws Exception {
        byte[] data = new byte[2];
        new ApplicationPort(127, (byte) 0, data);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPointer() throws Exception {
        new ApplicationPort(128, (byte) 4, null);

    }

    @Test(expectedExceptions = BadOctetsLength.class)
    public void testNotEnoughData8Bit() throws Exception {
        byte[] data = new byte[1];
        new ApplicationPort(AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT, (byte) 1, data);
    }

    @Test(expectedExceptions = BadOctetsLength.class)
    public void testNotEnoughData16Bit() throws Exception {
        byte[] data = new byte[1];
        new ApplicationPort(AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_16BIT, (byte) 1, data);
    }

    @Test
    public void testApplicationPort8Bit() throws Exception {
        byte[] data = new byte[]{(byte) 128, (byte) 129};
        ApplicationPort ap = new ApplicationPort(AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT, (byte) data.length, data);
        Assert.assertEquals(ap.getDstPort(), (short) 129);
        Assert.assertEquals(ap.getSrcPort(), (short) 128);
    }

    @Test
    public void testApplicationPort16Bit() throws Exception {
        byte[] data = new byte[]{(byte) 0x80, (byte) 0x44, (byte) 0x33, (byte) 0xee};
        ApplicationPort ap = new ApplicationPort(AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_16BIT, (byte) data.length, data);
        Assert.assertEquals(ap.getDstPort(), (short) (0x4480));
        Assert.assertEquals(ap.getSrcPort(), (short) (0xee33));
    }
}