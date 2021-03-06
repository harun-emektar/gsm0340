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

/**
 * Exception class to be thrown when SM parameters doesn't conform specification.
 */
public class InvalidParameter extends Exception {
    /**
     * @param format Format string
     * @param args arguments to form error message
     */
    public InvalidParameter(String format, Object... args) {
        super(AbsException.buildMessage(format, args));
    }

    /**
     * @param msg Error message
     */
    public InvalidParameter(String msg) {
        super(msg);
    }
}
