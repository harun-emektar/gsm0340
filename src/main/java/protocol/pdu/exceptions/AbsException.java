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

package protocol.pdu.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Abstract exception class.
 */
class AbsException {
    /**
     * @param format format String
     * @param args arguments to form error message
     * @return Error message
     */
    static String buildMessage(String format, Object... args) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.format(format, args);
        return sw.toString();
    }
}
