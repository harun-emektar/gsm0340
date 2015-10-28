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

import java.util.Map;

/**
 * Testing Information Elements.
 */
public class UDHTest {
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPointerException() throws Exception {
        UDH.decode(null);
    }

    @Test
    public void testOneConcatIE() throws Exception {
        byte[] data = new byte[] {0x5 /*UDHL*/, 0x0/*IE ID*/, 0x3/*IE Len*/, 0x22/*REF*/, (byte) 0xff/*MAX*/, 0x12/*SEQ*/};
        Map<Integer, AbsIE> IEs = UDH.decode(data);
        Assert.assertEquals(IEs.size(), 1);
        AbsIE ie = IEs.get(AbsIE.CONCATENATED_SHORT_MESSAGES);
        Assert.assertEquals(ie.getId(), AbsIE.CONCATENATED_SHORT_MESSAGES);
        Assert.assertEquals(ie.getLength(), 3);
        ConcatenatedSM csm = (ConcatenatedSM)ie;
        Assert.assertEquals(csm.getReferenceNumber(), 0x22);
        Assert.assertEquals(csm.getMaxShortMessages(), (byte)0xff);
        Assert.assertEquals(csm.getSeqNumber(), 0x12);
    }

    @Test
    public void testTwoConcatsFirstOneOmitted() throws Exception {
        byte[] data = new byte[] {
                0xa /*UDHL*/, 0x0/*IE ID*/, 0x3/*IE Len*/, 0x30/*REF*/, (byte) 0x40/*MAX*/, 0x8/*SEQ*/,
                              0x0/*IE ID*/, 0x3/*IE Len*/, 0x22/*REF*/, (byte) 0xff/*MAX*/, 0x12/*SEQ*/
        };
        Map<Integer, AbsIE> IEs = UDH.decode(data);
        Assert.assertEquals(IEs.size(), 1);
        AbsIE ie = IEs.get(AbsIE.CONCATENATED_SHORT_MESSAGES);
        Assert.assertEquals(ie.getId(), AbsIE.CONCATENATED_SHORT_MESSAGES);
        Assert.assertEquals(ie.getLength(), 3);
        ConcatenatedSM csm = (ConcatenatedSM)ie;
        Assert.assertEquals(csm.getReferenceNumber(), 0x22);
        Assert.assertEquals(csm.getMaxShortMessages(), (byte)0xff);
        Assert.assertEquals(csm.getSeqNumber(), 0x12);

    }

    @Test
    public void testConcatAndAppPort() throws Exception {
        byte[] data = new byte[] {
                0x9 /*UDHL*/, 0x0/*IE ID*/, 0x3/*IE Len*/, (byte) 0xd0/*REF*/, (byte) 0xcc/*MAX*/, (byte) 0xbb/*SEQ*/,
                              0x4/*IE ID*/, 0x2/*IE Len*/, (byte) 0xab/*SRC*/, (byte) 0xbc/*DST*/
        };
        Map<Integer, AbsIE> IEs = UDH.decode(data);
        Assert.assertEquals(IEs.size(), 2);
        AbsIE ie = IEs.get(AbsIE.CONCATENATED_SHORT_MESSAGES);
        Assert.assertEquals(ie.getId(), AbsIE.CONCATENATED_SHORT_MESSAGES);
        Assert.assertEquals(ie.getLength(), 3);
        ConcatenatedSM csm = (ConcatenatedSM)ie;
        Assert.assertEquals(csm.getReferenceNumber(), (byte)0xd0);
        Assert.assertEquals(csm.getMaxShortMessages(), (byte)0xcc);
        Assert.assertEquals(csm.getSeqNumber(), (byte)0xbb);

        ie = IEs.get(AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT);
        Assert.assertEquals(ie.getId(), AbsIE.APPLICATION_PORT_ADDRESSING_SCHEME_8BIT);
        Assert.assertEquals(ie.getLength(), 2);
        ApplicationPort ap = (ApplicationPort)ie;
        Assert.assertEquals(ap.getSrcPort(), (short) 0xab);
        Assert.assertEquals(ap.getDstPort(), (short) 0xbc);
    }
}