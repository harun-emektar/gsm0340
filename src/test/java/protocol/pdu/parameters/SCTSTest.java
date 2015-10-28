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

package protocol.pdu.parameters;

import org.testng.Assert;
import org.testng.annotations.Test;
import protocol.pdu.exceptions.BadOctetsLength;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Testing TP-SCTS
 */
public class SCTSTest {
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullException() throws BadOctetsLength {
        SCTS.getTimeStamp(null);
    }

    @Test(expectedExceptions = BadOctetsLength.class)
    public void testBadOctetsLength() throws Exception {
        byte [] pdu = new byte[] {};
        SCTS.getTimeStamp(pdu);
    }

    @Test
    public void testGetTimeStampGMT0() throws Exception {
        byte[] pdu = new byte[]{0x51, 0x50, 0x71, 0x71, (byte) 0x81, 0x20, 0x00};
        GregorianCalendar expected = new GregorianCalendar(2015, 4, 17, 17, 18, 2);
        expected.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Assert.assertEquals(SCTS.getTimeStamp(pdu), expected);
    }

    @Test
    public void testGetTimeStampGMT19() throws Exception {
        byte[] pdu = new byte[]{0x51, 0x50, 0x71, 0x71, (byte) 0x81, 0x20, (byte) 0x97};
        GregorianCalendar expected = new GregorianCalendar(2015, 4, 17, 17, 18, 2);
        expected.setTimeZone(TimeZone.getTimeZone("GMT+19:45"));
        Assert.assertEquals(SCTS.getTimeStamp(pdu), expected);
    }

    @Test
    public void testGetTimeStampGMTMinus19() throws Exception {
        byte[] pdu = new byte[]{0x51, 0x50, 0x71, 0x71, (byte) 0x81, 0x20, (byte) 0x9F};
        GregorianCalendar expected = new GregorianCalendar(2015, 4, 17, 17, 18, 2);
        expected.setTimeZone(TimeZone.getTimeZone("GMT-19:45"));
        Assert.assertEquals(SCTS.getTimeStamp(pdu), expected);
    }
}