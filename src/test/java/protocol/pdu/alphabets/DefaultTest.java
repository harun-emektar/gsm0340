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

package protocol.pdu.alphabets;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testing Default GSM Alphabet decoding.
 */
public class DefaultTest {

    @Test
    public void testDecodeSM() throws Exception {
        byte[] defaultAlpha = new byte[]{
                0x54, 0x68, 0x65, 0x20, 0x71, 0x75, 0x69, 0x63, 0x6b, 0x20, 0x62, 0x72,
                0x6f, 0x77, 0x6e, 0x20, 0x66, 0x6f, 0x78, 0x20, 0x6a, 0x75, 0x6d, 0x70,
                0x65, 0x64, 0x20, 0x6f, 0x76, 0x65, 0x72, 0x20, 0x6c, 0x61, 0x7a, 0x79,
                0x20, 0x64, 0x6f, 0x67, 0x2e
        };
        Assert.assertEquals(Default.decodeSM(defaultAlpha), "The quick brown fox jumped over lazy dog.");

        // test extension table
        defaultAlpha = new byte[] {0x1b, 0x14, 0x1b, 0x28, 0x1b, 0x29, 0x1b, 0x2f,
                0x1b, 0x3c, 0x1b, 0x3d, 0x1b, 0x3e, 0x1b, 0x40, 0x1b, 0x1b
        };
        Assert.assertEquals(Default.decodeSM(defaultAlpha), "^{}\\[~]| ");
    }
}