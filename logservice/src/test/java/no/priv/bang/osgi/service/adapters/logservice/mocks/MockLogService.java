/*
 * Copyright 2017 Steinar Bang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package no.priv.bang.osgi.service.adapters.logservice.mocks;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

@SuppressWarnings("rawtypes")
public class MockLogService implements LogService {
    final String[] errorLevel = {"", "[ERROR] ", "[WARNING] ", "[INFO] ", "[DEBUG] "};
    List<String> logmessages = new ArrayList<String>();

    public List<String> getLogmessages() {
        return logmessages;
    }

    public void log(int level, String message) {
        String messageWithLevel = errorLevel[level] + message;
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

    public void log(int level, String message, Throwable exception) {
        String messageWithLevel = errorLevel[level] + message + " " + exception.toString();
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

    public void log(ServiceReference sr, int level, String message) {
        String messageWithLevel = errorLevel[level] + sr + " " + message;
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

    public void log(ServiceReference sr, int level, String message, Throwable exception) {
        String messageWithLevel = errorLevel[level] + sr + " " + message + " " + exception.toString();
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

}
