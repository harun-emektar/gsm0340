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
 * Testing septet representation.
 */
public class SeptetsTest {

    @Test
    public void testSeptets2Octets() throws Exception {
        byte[] septets = new byte[] {(byte) 0xff, (byte) 0xff};
        byte[] octets = new byte[]{0x7f, 0x7f};
        Assert.assertEquals(Septets.septets2Octets(septets, 0, 2), octets);

        septets = new byte[]{(byte) 0x0, (byte) 0x0};
        octets = new byte[]{0x0, 0x0};
        Assert.assertEquals(Septets.septets2Octets(septets, 0, 2), octets);

        septets = new byte[]{(byte) 0x81, (byte) 0x0};
        octets = new byte[]{0x1, 0x1};
        Assert.assertEquals(Septets.septets2Octets(septets, 0, 2), octets);

        septets = new byte[]{(byte) 0x08, (byte) 0x4};
        octets = new byte[]{0x8, 0x8};
        Assert.assertEquals(Septets.septets2Octets(septets, 0, 2), octets);

        septets = new byte[]{(byte) 0x0, (byte) 0x4};
        octets = new byte[]{0x8};
        Assert.assertEquals(Septets.septets2Octets(septets, 7, 1), octets);
    }

    @Test
    public void testOctets2Septets() throws Exception {

    }
}