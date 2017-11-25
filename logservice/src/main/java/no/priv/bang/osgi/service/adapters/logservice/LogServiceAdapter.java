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
package no.priv.bang.osgi.service.adapters.logservice;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import no.priv.bang.osgi.service.adapters.logservice.internal.LevelAndMessage;
import no.priv.bang.osgi.service.adapters.logservice.internal.LevelAndMessageAndException;
import no.priv.bang.osgi.service.adapters.logservice.internal.SavedLogMessage;
import no.priv.bang.osgi.service.adapters.logservice.internal.ServiceReferenceAndLevelAndMessage;
import no.priv.bang.osgi.service.adapters.logservice.internal.ServiceReferenceAndLevelAndMessageAndException;

@SuppressWarnings("rawtypes")
public class LogServiceAdapter implements LogService {

    private LogService logservice;
    private List<SavedLogMessage> savedLogMessages = new ArrayList<>();

    @Override
    public void log(int level, String message) {
        if (logservice != null) {
            logservice.log(level, message);
        } else {
            savedLogMessages.add(new LevelAndMessage(level, message));
        }
    }

    @Override
    public void log(int level, String message, Throwable exception) {
        if (logservice != null) {
            logservice.log(level, message, exception);
        } else {
            savedLogMessages.add(new LevelAndMessageAndException(level, message, exception));
        }
    }

    @Override
    public void log(ServiceReference sr, int level, String message) {
        if (logservice != null) {
            logservice.log(sr, level, message);
        } else {
            savedLogMessages.add(new ServiceReferenceAndLevelAndMessage(sr, level, message));
        }
    }

    @Override
    public void log(ServiceReference sr, int level, String message, Throwable exception) {
        if (logservice != null) {
            logservice.log(sr, level, message, exception);
        } else {
            savedLogMessages.add(new ServiceReferenceAndLevelAndMessageAndException(sr, level, message, exception));
        }
    }

    public void setLogService(LogService logservice) {
        this.logservice = logservice;
        sendSavedLogMessagesToRealLogService(logservice);
    }

    private void sendSavedLogMessagesToRealLogService(LogService log) {
        if(log != null) {
            for (SavedLogMessage savedLogMessage : savedLogMessages) {
                savedLogMessage.sendToService(log);
            }

            savedLogMessages.clear();
        }
    }

}
