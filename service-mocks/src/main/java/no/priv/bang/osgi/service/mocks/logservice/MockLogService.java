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
package no.priv.bang.osgi.service.mocks.logservice;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

/**
 * This is an implementation of the {@link LogService} interface that is
 * intended for use in unit tests.
 *
 * A typical use is to create an instance of the {@link MockLogService}, use the
 * method adding the inserted service to simulate service injection, and later
 * call the {@link #getLogmessages()} method to verify whether anything has
 * been logged or not.
 *
 * <p>In addition to storing the log messages, this service will also print the
 * log messages to System.err.
 *
 * @author Steinar Bang
 *
 */
@SuppressWarnings("rawtypes")
public class MockLogService implements LogService {
    final String[] errorLevel = {"", "[ERROR] ", "[WARNING] ", "[INFO] ", "[DEBUG] "};
    List<String> logmessages = new ArrayList<String>();

    /**
     * Get the list of formatted log messages that has been received by this
     * {@link LogService}.
     *
     * @return a list of strings containing formatted log messages, one string per logged message.
     */
    public List<String> getLogmessages() {
        return logmessages;
    }

    /**
     * Receive a log message.  A formatted version of the log message is stored
     * in the list retrieved by {@link #getLogmessages()} and the message is
     * also written to standard error.
     *
     *  @param level can the value {@link LogService#LOG_DEBUG}, {@link LogService#LOG_INFO}, {@link LogService#LOG_WARNING} or {@link LogService#LOG_ERROR}
     *  @param message the message to log
     */
    public void log(int level, String message) {
        String messageWithLevel = errorLevel[level] + message;
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

    /**
     * Receive a log message.  A formatted version of the log message is stored
     * in the list retrieved by {@link #getLogmessages()} and the message is
     * also written to standard error.
     *
     * @param level can the value {@link LogService#LOG_DEBUG}, {@link LogService#LOG_INFO}, {@link LogService#LOG_WARNING} or {@link LogService#LOG_ERROR}
     * @param message the message to log
     * @param exception the exception that caused the error situation
     */
    public void log(int level, String message, Throwable exception) {
        String messageWithLevel = errorLevel[level] + message + " " + exception.toString();
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

    /**
     * Receive a log message.  A formatted version of the log message is stored
     * in the list retrieved by {@link #getLogmessages()} and the message is
     * also written to standard error.
     *
     * @param sr the OSGi service this log message relates to.
     * @param level can the value {@link LogService#LOG_DEBUG}, {@link LogService#LOG_INFO}, {@link LogService#LOG_WARNING} or {@link LogService#LOG_ERROR}
     * @param message the message to log
     */
    public void log(ServiceReference sr, int level, String message) {
        String messageWithLevel = errorLevel[level] + sr + " " + message;
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

    /**
     * Receive a log message.  A formatted version of the log message is stored
     * in the list retrieved by {@link #getLogmessages()} and the message is
     * also written to standard error.
     *
     * @param sr the OSGi service this log message relates to.
     * @param level can the value {@link LogService#LOG_DEBUG}, {@link LogService#LOG_INFO}, {@link LogService#LOG_WARNING} or {@link LogService#LOG_ERROR}
     * @param message the message to log
     * @param exception the exception that caused the error situation
     */
    public void log(ServiceReference sr, int level, String message, Throwable exception) {
        String messageWithLevel = errorLevel[level] + sr + " " + message + " " + exception.toString();
        logmessages.add(messageWithLevel);
        System.err.println(messageWithLevel);
    }

}
