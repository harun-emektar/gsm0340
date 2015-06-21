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
 * Testing TP-MTI
 */
public class MTITest {

    @Test
    public void testDecode() throws Exception {
        Assert.assertEquals(MTI.decode((byte) 0x0), MTI.SMS_DELIVER);
        Assert.assertEquals(MTI.decode((byte) 0x0), MTI.SMS_DELIVER_REPORT);
        Assert.assertEquals(MTI.decode((byte) 0x1), MTI.SMS_SUBMIT);
        Assert.assertEquals(MTI.decode((byte) 0x1), MTI.SMS_SUBMIT_REPORT);
        Assert.assertEquals(MTI.decode((byte) 0x2), MTI.SMS_COMMAND);
        Assert.assertEquals(MTI.decode((byte) 0x2), MTI.SMS_STATUS_REPORT);
        Assert.assertEquals(MTI.decode((byte) 0x3), MTI.RESERVED);
    }
}