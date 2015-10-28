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

/**
 * Testing SemiOctet representation.
 */
public class SemiOctetTest {

    @Test
    public void testDecode() throws Exception {
        byte [] so = new byte[] {0x0};
        Assert.assertEquals(SemiOctet.decode(so), "00");
        so = new byte[] {0x1};
        Assert.assertEquals(SemiOctet.decode(so), "10");
        so = new byte[] {0x2};
        Assert.assertEquals(SemiOctet.decode(so), "20");
        so = new byte[] {0x3};
        Assert.assertEquals(SemiOctet.decode(so), "30");
        so = new byte[] {0x4};
        Assert.assertEquals(SemiOctet.decode(so), "40");
        so = new byte[] {0x5};
        Assert.assertEquals(SemiOctet.decode(so), "50");
        so = new byte[] {0x6};
        Assert.assertEquals(SemiOctet.decode(so), "60");
        so = new byte[] {0x7};
        Assert.assertEquals(SemiOctet.decode(so), "70");
        so = new byte[] {0x8};
        Assert.assertEquals(SemiOctet.decode(so), "80");
        so = new byte[] {0x9};
        Assert.assertEquals(SemiOctet.decode(so), "90");

        so = new byte[] {(byte)0xf0};
        Assert.assertEquals(SemiOctet.decode(so), "0");

        so = new byte[] {(byte)0xff};
        Assert.assertEquals(SemiOctet.decode(so), "");

        so = new byte[] {(byte)0xfa};
        Assert.assertEquals(SemiOctet.decode(so), "*");
        so = new byte[] {(byte)0xfb};
        Assert.assertEquals(SemiOctet.decode(so), "#");
        so = new byte[] {(byte)0xfc};
        Assert.assertEquals(SemiOctet.decode(so), "a");
        so = new byte[] {(byte)0xfd};
        Assert.assertEquals(SemiOctet.decode(so), "b");
        so = new byte[] {(byte)0xfe};
        Assert.assertEquals(SemiOctet.decode(so), "c");

    }
}