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

import java.util.Date;

/**
 * Testing TP-VP
 */
public class VPTest {
    private static final long MSEC_PER_5MIN = 5 * 60 * 1000;
    private static final long MSEC_IN_12HOURS = 12 * 60 * 60 * 1000;
    private static final long MSEC_IN_30MINS = 30 * 60 * 1000;
    private static final long MSEC_IN_DAY = 2 * MSEC_IN_12HOURS;
    private static final long MSEC_IN_WEEK = 7 * MSEC_IN_DAY;

    @Test
    public void testgetRelativeTime() throws Exception {
        Assert.assertEquals(new Date(MSEC_PER_5MIN), VP.getRelativeTime((byte) 0));
    }

    @Test
    public void test5Minutes() throws Exception {
        Assert.assertEquals(VP.getRelativeTime((byte) 5), new Date(6 * MSEC_PER_5MIN));
        Assert.assertEquals(VP.getRelativeTime((byte) 100), new Date(101 * MSEC_PER_5MIN));
        Assert.assertEquals(VP.getRelativeTime((byte) 143), new Date(144 * MSEC_PER_5MIN));
    }

    @Test
    public void test12Hours() throws Exception {
        Assert.assertEquals(VP.getRelativeTime((byte) 144), new Date((144 - 143) * MSEC_IN_30MINS + MSEC_IN_12HOURS));
        Assert.assertEquals(VP.getRelativeTime((byte) 167), new Date((167 - 143) * MSEC_IN_30MINS + MSEC_IN_12HOURS));
    }

    @Test
    public void testDays() throws Exception {
        Assert.assertEquals(VP.getRelativeTime((byte) 168), new Date((168 - 166) * MSEC_IN_DAY));
        Assert.assertEquals(VP.getRelativeTime((byte) 196), new Date((196 - 166) * MSEC_IN_DAY));
    }

    @Test
    public void testWeeks() throws Exception {
        Assert.assertEquals(VP.getRelativeTime((byte) 197), new Date((197 - 192) * MSEC_IN_WEEK));
        Assert.assertEquals(VP.getRelativeTime((byte) 255), new Date((255 - 192) * MSEC_IN_WEEK));
    }
}