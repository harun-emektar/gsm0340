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

/**
 * TP-Command-Type is an 8-bit field specifiying the type of operation that SC is to perform.
 */
public class CT {
    // Enquiry relating to previously submitted short message
    public static final int ENQUIRY = 0;
    // Cancel Status Report Request relating to previously submitted short message
    public static final int CANCEL_STATUS_REPORT_REQUEST = 1;
    // Delete previously submitted Short Message
    public static final int DELETE = 2;
    // Enable Status Report Request relating to previously submitted short message
    public static final int ENABLE_STATUS_REPORT_REQUEST = 3;
    // Reserved start
    public static final int RESERVED_BEGIN = 4;
    // Reserved end
    public static final int RESERVED_END = 31;// 0x1f
    // SC specific begin
    public static final int SC_SPECIFIC_BEGIN = 0xE0;
    // SC specific end
    public static final int SC_SPECIFIC_END = 0xFF;
}
