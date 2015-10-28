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
import protocol.pdu.exceptions.NotApplicable;

/**
 * Testing TP-DCS
 */
public class DCSTest {
    @Test
    public void testIsCompressed() throws Exception {
        DCS dcs = DCS.decode((byte) 0x30);// general coding group
        Assert.assertEquals(dcs.isCompressed(), true);
        dcs = DCS.decode((byte) 0x10);
        Assert.assertEquals(dcs.isCompressed(), false);
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsCompressedNAMWIDiscard() throws Exception {
        DCS dcs = DCS.decode((byte) 0xc0);
        dcs.isCompressed();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsCompressedNAMWIStore() throws Exception {
        DCS dcs = DCS.decode((byte) 0xd0);
        dcs.isCompressed();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsCompressedNAMWIStoreUCS2() throws Exception {
        DCS dcs = DCS.decode((byte) 0xe0);
        dcs.isCompressed();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsCompressedNADataCoding() throws Exception {
        DCS dcs = DCS.decode((byte) 0xf0);
        dcs.isCompressed();
    }

    @Test
    public void testGetAlphabet() throws Exception {
        // general coding group
        DCS dcs = DCS.decode((byte) 0x0);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DEFAULT_ALPHABET);
        dcs = DCS.decode((byte) 0x04);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DATA_8BIT);
        dcs = DCS.decode((byte) 0x08);
        Assert.assertEquals(dcs.getAlphabet(), DCS.UCS2);
        // data coding/message group
        dcs = DCS.decode((byte) 0xf0);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DEFAULT_ALPHABET);
        dcs = DCS.decode((byte) 0xf4);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DATA_8BIT);

        // MWI discard message
        dcs = DCS.decode((byte) 0xc0);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DEFAULT_ALPHABET);
        // MWI store message
        dcs = DCS.decode((byte) 0xd0);
        Assert.assertEquals(dcs.getAlphabet(), DCS.DEFAULT_ALPHABET);
        // MWI store message UCS2
        dcs = DCS.decode((byte) 0xe0);
        Assert.assertEquals(dcs.getAlphabet(), DCS.UCS2);
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testGetAlphabetNAMWIReserved() throws Exception {
        DCS dcs = DCS.decode((byte) 0xb0);// reserved group
        dcs.getAlphabet();
    }

    @Test
    public void testGetCodingGroup() throws Exception {
        DCS dcs = DCS.decode((byte) 0);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        dcs = DCS.decode((byte) 0x10);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        dcs = DCS.decode((byte) 0x20);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        dcs = DCS.decode((byte) 0x30);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.GENERAL_CODING_GROUP);
        dcs = DCS.decode((byte) 0xc0);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.MESSAGE_WAIT_INDICATION_GROUP);
        dcs = DCS.decode((byte) 0xd0);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.MESSAGE_WAIT_INDICATION_GROUP);
        dcs = DCS.decode((byte) 0xe0);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.MESSAGE_WAIT_INDICATION_GROUP);
        dcs = DCS.decode((byte) 0xf0);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.DATA_CODING_MESSAGE_CLASS_GROUP);
        dcs = DCS.decode((byte) 0x60);
        Assert.assertEquals(dcs.getCodingGroup(), DCS.RESERVED_CODING_GROUP);
    }

    @Test
    public void testGetMessageClass() throws Exception {
        DCS dcs = DCS.decode((byte) 0x10);
        // general data coding group
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_0);
        dcs = DCS.decode((byte) 0x11);
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_1);
        dcs = DCS.decode((byte) 0x12);
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_2);
        dcs = DCS.decode((byte) 0x13);
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_3);

        // data coding/message class
        dcs = DCS.decode((byte) 0xF0);
        // general data coding group
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_0);
        dcs = DCS.decode((byte) 0xf1);
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_1);
        dcs = DCS.decode((byte) 0xf2);
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_2);
        dcs = DCS.decode((byte) 0xf3);
        Assert.assertEquals(dcs.getMessageClass(), DCS.CLASS_3);
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testGetMessageClassNAGeneral() throws Exception {
        DCS dcs = DCS.decode((byte) 0x03);
        // general data coding group message class not enabled
        dcs.getMessageClass();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testGetMessageClassNAMWI1() throws Exception {
        DCS dcs = DCS.decode((byte) 0xc3);// MWI discard message
        dcs.getMessageClass();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testGetMessageClassNAMWI2() throws Exception {
        DCS dcs = DCS.decode((byte) 0xd3);// MWI store message
        dcs.getMessageClass();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testGetMessageClassNAMWI3() throws Exception {
        DCS dcs = DCS.decode((byte) 0xe3);// MWI store message ucs2
        dcs.getMessageClass();
    }

    @Test
    public void testIsStoreMessage() throws Exception {
        DCS dcs = DCS.decode((byte) 0xd0);// MWI store message
        Assert.assertEquals(dcs.isStoreMessage(), true);
        dcs = DCS.decode((byte) 0xe0);// MWI store message UCS2
        Assert.assertEquals(dcs.isStoreMessage(), true);
        dcs = DCS.decode((byte) 0xc0);// MWI discard message
        Assert.assertEquals(dcs.isStoreMessage(), false);
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsStoreMessageNAGeneral() throws Exception {
        DCS dcs = DCS.decode((byte) 0x0);
        dcs.isStoreMessage();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsStoreMessageNADataCoding() throws Exception {
        DCS dcs = DCS.decode((byte) 0xf0);
        dcs.isStoreMessage();
    }

    @Test
    public void testIsDiscardMessage() throws Exception {
        DCS dcs = DCS.decode((byte) 0xd0);// MWI store message
        Assert.assertEquals(dcs.isDiscardMessage(), false);
        dcs = DCS.decode((byte) 0xe0);// MWI store message UCS2
        Assert.assertEquals(dcs.isDiscardMessage(), false);
        dcs = DCS.decode((byte) 0xc0);// MWI discard message
        Assert.assertEquals(dcs.isDiscardMessage(), true);
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsDiscardMessageNAGeneral() throws Exception {
        DCS dcs = DCS.decode((byte) 0x0);
        dcs.isDiscardMessage();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsDiscardMessageNADataCoding() throws Exception {
        DCS dcs = DCS.decode((byte) 0xf0);
        dcs.isDiscardMessage();
    }

    @Test
    public void testIsIndicationActive() throws Exception {
        DCS dcs = DCS.decode((byte) 0xc8); // MWI discard message
        Assert.assertEquals(dcs.isIndicationActive(), true);
        dcs = DCS.decode((byte) 0xd8); // MWI store message
        Assert.assertEquals(dcs.isIndicationActive(), true);
        dcs = DCS.decode((byte) 0xe8); // MWI store message UCS2
        Assert.assertEquals(dcs.isIndicationActive(), true);
        // negative
        dcs = DCS.decode((byte) 0xc7);
        Assert.assertEquals(dcs.isIndicationActive(), false);
        dcs = DCS.decode((byte) 0xd7); // MWI store message
        Assert.assertEquals(dcs.isIndicationActive(), false);
        dcs = DCS.decode((byte) 0xe7); // MWI store message UCS2
        Assert.assertEquals(dcs.isIndicationActive(), false);
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsIndicationActiveNAGeneral() throws Exception {
        DCS dcs = DCS.decode((byte) 0x0);
        dcs.isIndicationActive();
    }

    @Test(expectedExceptions = NotApplicable.class)
    public void testIsIndicationActiveNADataCoding() throws Exception {
        DCS dcs = DCS.decode((byte) 0xf0);
        dcs.isIndicationActive();
    }

}