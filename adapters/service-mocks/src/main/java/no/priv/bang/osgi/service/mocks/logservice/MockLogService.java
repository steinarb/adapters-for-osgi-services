/*
 * Copyright 2017-2021 Steinar Bang
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

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.FormatterLogger;
import org.osgi.service.log.LogService;
import org.osgi.service.log.Logger;

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
    private boolean traceEnabled = true;
    private boolean debugEnabled = true;
    private boolean infoEnabled = true;
    private boolean warnEnabled = true;
    private boolean errorEnabled = true;

    /**
     * Get the list of formatted log messages that has been received by this
     * {@link LogService}.
     *
     * @return a list of strings containing formatted log messages, one string per logged message.
     */
    public List<String> getLogmessages() {
        return logmessages;
    }

    public boolean isTraceEnabled() {
        return traceEnabled;
    }

    public MockLogService setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        return this;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public MockLogService setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
        return this;
    }

    public boolean isInfoEnabled() {
        return infoEnabled;
    }

    public MockLogService setInfoEnabled(boolean infoEnabled) {
        this.infoEnabled  = infoEnabled;
        return this;
    }

    public boolean isWarnEnabled() {
        return warnEnabled;
    }

    public MockLogService setWarnEnabled(boolean warnEnabled) {
        this.warnEnabled  = warnEnabled;
        return this;
    }

    public boolean isErrorEnabled() {
        return errorEnabled;
    }

    public MockLogService setErrorEnabled(boolean errorEnabled) {
        this.errorEnabled = errorEnabled;
        return this;
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

    @Override
    public Logger getLogger(String name) {
        return new MockLogger(name, this);
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new MockLogger(clazz.getCanonicalName(), this);
    }

    @Override
    public <L extends Logger> L getLogger(String name, Class<L> loggerType) {
        if (loggerType.equals(FormatterLogger.class)) {
            return loggerType.cast(new MockFormatterLogger(name, this));
        } else if (loggerType.equals(Logger.class)) {
            return loggerType.cast(new MockLogger(name, this));
        }

        return null;
    }

    @Override
    public <L extends Logger> L getLogger(Class<?> clazz, Class<L> loggerType) {
        return getLogger(clazz.getCanonicalName(), loggerType);
    }

    @Override
    public <L extends Logger> L getLogger(Bundle bundle, String name, Class<L> loggerType) {
        return getLogger(name, loggerType);
    }

}
