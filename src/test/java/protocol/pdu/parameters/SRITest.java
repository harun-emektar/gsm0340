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
 * Testing TP-SRI
 */
public class SRITest {

    @Test
    public void testDecode() throws Exception {
        Assert.assertEquals(SRI.decode((byte) 0x20), SRI.WILL_BE_RETURNED);
        Assert.assertEquals(SRI.decode((byte) 0x0), SRI.WONT_BE_RETURNED);
    }
}