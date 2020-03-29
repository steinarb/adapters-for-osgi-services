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

import no.priv.bang.osgi.service.adapters.logservice.internal.SavedLevelAndMessage;
import no.priv.bang.osgi.service.adapters.logservice.internal.SavedLevelAndMessageAndExceptn;
import no.priv.bang.osgi.service.adapters.logservice.internal.SavedLogMessage;
import no.priv.bang.osgi.service.adapters.logservice.internal.SavedServiceReferenceAndLevelAndMessage;
import no.priv.bang.osgi.service.adapters.logservice.internal.SavedServiceReferenceAndLevelAndMessageAndExceptn;

/***
 * An adapter for {@link LogService}.  An instance of this class can be created
 * in a component and be used immediately whether the LogService has been injected
 * or not.
 *
 * Log messages that are received before a {@link LogService} has been injected
 * are saved inside the {@link LogServiceAdapter}.
 *
 * <p>When a {@link LogService} is injected it can be set in the
 * {@link #setLogService(LogService)} method.  The first thing that
 * happens, is that any saved log messages are written to the {@link LogService}.
 *
 * After the saved log messages have been set to the {@link LogService},
 * new log messages will be passed on to the wrapped {@link LogService}
 * without any caching taking place.
 *
 * @author Steinar Bang
 *
 */
@SuppressWarnings("rawtypes")
public class LogServiceAdapter implements LogService {

    private LogService logservice;
    private List<SavedLogMessage> savedLogMessages = new ArrayList<>();

    /***
     * Passes this log message on to {@link LogService#log(int, String)} of
     * a wrapped {@link LogService} or saves the log messages if no
     * LogService has been injected.
     */
    @Override
    public void log(int level, String message) {
        if (logservice != null) {
            logservice.log(level, message);
        } else {
            savedLogMessages.add(new SavedLevelAndMessage(level, message));
        }
    }

    /***
     * Passes this log message on to {@link LogService#log(int, String, Throwable)} of
     * a wrapped {@link LogService} or saves the log messages if no
     * LogService has been injected.
     */
    @Override
    public void log(int level, String message, Throwable exception) {
        if (logservice != null) {
            logservice.log(level, message, exception);
        } else {
            savedLogMessages.add(new SavedLevelAndMessageAndExceptn(level, message, exception));
        }
    }

    /***
     * Passes this log message on to {@link LogService#log(ServiceReference, int, String)} of
     * a wrapped {@link LogService} or saves the log messages if no
     * LogService has been injected.
     */
    @Override
    public void log(ServiceReference sr, int level, String message) {
        if (logservice != null) {
            logservice.log(sr, level, message);
        } else {
            savedLogMessages.add(new SavedServiceReferenceAndLevelAndMessage(sr, level, message));
        }
    }

    /***
     * Passes this log message on to {@link LogService#log(ServiceReference, int, String, Throwable)} of
     * a wrapped {@link LogService} or saves the log messages if no
     * LogService has been injected.
     */
    @Override
    public void log(ServiceReference sr, int level, String message, Throwable exception) {
        if (logservice != null) {
            logservice.log(sr, level, message, exception);
        } else {
            savedLogMessages.add(new SavedServiceReferenceAndLevelAndMessageAndExceptn(sr, level, message, exception));
        }
    }

    /**
     * Set a {@link LogService} that will receive all log messages sent to this class.
     *
     * <p>If this LogServiceAdapter have saved log messages, the saved log messages will
     * be sent to the logservice before it start passing on new log messages.
     *
     * @param logservice A {@code LogService} that will receive any saved log messages, and then get all new log messages logged on this class.
     */
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
