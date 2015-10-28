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
 * testing concatenated SM information element.
 */
public class ConcatenatedSMTest {
    @Test(expectedExceptions = InvalidParameter.class)
    public void testInvalidId() throws Exception {
        byte [] data = new byte[2];
        new ConcatenatedSM((byte)2, (byte) 0, data);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPointer() throws Exception {
        new ConcatenatedSM((byte)2, (byte) 4, null);
    }

    @Test(expectedExceptions = BadOctetsLength.class)
    public void testNotEnoughData() throws Exception {
        byte[] data = new byte[2];
        new ConcatenatedSM((byte)AbsIE.CONCATENATED_SHORT_MESSAGES, (byte)data.length, data);
    }

    @Test
    public void testConcatenatedSM() throws Exception {
        byte[] data = new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255};
        ConcatenatedSM csm = new ConcatenatedSM((byte)AbsIE.CONCATENATED_SHORT_MESSAGES, (byte)data.length, data);
        Assert.assertEquals(csm.getReferenceNumber(), (byte)0xff);
        Assert.assertEquals(csm.getMaxShortMessages(), (byte)0xff);
        Assert.assertEquals(csm.getSeqNumber(), (byte)0xff);
    }
}