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

/**
 * Testing TP-PID
 */
public class PIDTest {

    @Test
    public void testIsTelematic() throws Exception {
        PID pid = PID.decode((byte) 0x31);
        Assert.assertEquals(pid.isTelematic(), true);
        Assert.assertEquals(pid.isNoInterworking(), false);
        Assert.assertEquals(pid.isReplaceShortMessage(), false);
        Assert.assertEquals(pid.getTelematicType(), PID.ANY_PUBLIC_X400_BASED_MESSAGE_HANDLING_SYSTEM);
    }

    @Test
    public void testIsNoInterworking() throws Exception {
        PID pid = PID.decode((byte) 0x08);
        Assert.assertEquals(pid.isTelematic(), false);
        Assert.assertEquals(pid.isNoInterworking(), true);
        Assert.assertEquals(pid.isReplaceShortMessage(), false);
    }

    @Test
    public void testGetTelematicType() throws Exception {
        PID pid = PID.decode((byte) 0x32);
        Assert.assertEquals(pid.isTelematic(), true);
        Assert.assertEquals(pid.isNoInterworking(), false);
        Assert.assertEquals(pid.isReplaceShortMessage(), false);
        Assert.assertEquals(pid.getTelematicType(), PID.INTERNET_ELECTRONIC_MAIL);
    }

    @Test
    public void testIsReplaceShortMessage() throws Exception {
        PID pid = PID.decode((byte) 0x5f);
        Assert.assertEquals(pid.isTelematic(), false);
        Assert.assertEquals(pid.isNoInterworking(), false);
        Assert.assertEquals(pid.isReplaceShortMessage(), true);
        Assert.assertEquals(pid.getReplaceShortMessageType(), PID.RETURN_CALL_MESSAGE);
    }

    @Test
    public void testGetReplaceShortMessageType() throws Exception {
        PID pid = PID.decode((byte) 0x47);
        Assert.assertEquals(pid.isTelematic(), false);
        Assert.assertEquals(pid.isNoInterworking(), false);
        Assert.assertEquals(pid.isReplaceShortMessage(), true);
        Assert.assertEquals(pid.getReplaceShortMessageType(), PID.REPLACE_SHORT_MESSAGE_TYPE_7);
    }
}