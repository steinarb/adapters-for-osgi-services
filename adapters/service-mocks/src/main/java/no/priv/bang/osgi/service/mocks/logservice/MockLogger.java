/*
 * Copyright 2021-2023 Steinar Bang
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

import org.osgi.service.log.LogService;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerConsumer;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class MockLogger implements Logger {

    private String name;
    private MockLogService mockLogService;

    MockLogger(String name, MockLogService mockLogService) {
        this.name = name;
        this.mockLogService = mockLogService;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return mockLogService.isTraceEnabled();
    }

    @Override
    public void trace(String message) {
        if (isTraceEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, message);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, messageAndThrowable(MessageFormatter.format(format, arg)));
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, messageAndThrowable(MessageFormatter.format(format, arg1, arg2)));
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, messageAndThrowable(MessageFormatter.arrayFormat(format, arguments)));
        }
    }

    @Override
    public <E extends Exception> void trace(LoggerConsumer<E> consumer) throws E {
        /* Empty implementation */

    }

    @Override
    public boolean isDebugEnabled() {
        return mockLogService.isDebugEnabled();
    }

    @Override
    public void debug(String message) {
        if (isDebugEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, message);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        if (isDebugEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, messageAndThrowable(MessageFormatter.format(format, arg)));
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, messageAndThrowable(MessageFormatter.format(format, arg1, arg2)));
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (isDebugEnabled()) {
            mockLogService.log(LogService.LOG_DEBUG, messageAndThrowable(MessageFormatter.arrayFormat(format, arguments)));
        }
    }

    @Override
    public <E extends Exception> void debug(LoggerConsumer<E> consumer) throws E {
        /* Empty implementation */

    }

    @Override
    public boolean isInfoEnabled() {
        return mockLogService.isInfoEnabled();
    }

    @Override
    public void info(String message) {
        if (isInfoEnabled()) {
            mockLogService.log(LogService.LOG_INFO, message);
        }
    }

    @Override
    public void info(String format, Object arg) {
        if (isInfoEnabled()) {
            mockLogService.log(LogService.LOG_INFO, messageAndThrowable(MessageFormatter.format(format, arg)));
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        if (isInfoEnabled()) {
            mockLogService.log(LogService.LOG_INFO, messageAndThrowable(MessageFormatter.format(format, arg1, arg2)));
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        if (isInfoEnabled()) {
            mockLogService.log(LogService.LOG_INFO, messageAndThrowable(MessageFormatter.arrayFormat(format, arguments)));
        }
    }

    @Override
    public <E extends Exception> void info(LoggerConsumer<E> consumer) throws E {
        /* Empty implementation */

    }

    @Override
    public boolean isWarnEnabled() {
        return mockLogService.isWarnEnabled();
    }

    @Override
    public void warn(String message) {
        if (isWarnEnabled()) {
            mockLogService.log(LogService.LOG_WARNING, message);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        if (isWarnEnabled()) {
            mockLogService.log(LogService.LOG_WARNING, messageAndThrowable(MessageFormatter.format(format, arg)));
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled()) {
            mockLogService.log(LogService.LOG_WARNING, messageAndThrowable(MessageFormatter.format(format, arg1, arg2)));
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (isWarnEnabled()) {
            mockLogService.log(LogService.LOG_WARNING, messageAndThrowable(MessageFormatter.arrayFormat(format, arguments)));
        }
    }

    @Override
    public <E extends Exception> void warn(LoggerConsumer<E> consumer) throws E {
        /* Empty implementation */

    }

    @Override
    public boolean isErrorEnabled() {
        return mockLogService.isErrorEnabled();
    }

    @Override
    public void error(String message) {
        if (isErrorEnabled()) {
            mockLogService.log(LogService.LOG_ERROR, message);
        }
    }

    @Override
    public void error(String format, Object arg) {
        if (isErrorEnabled()) {
            mockLogService.log(LogService.LOG_ERROR, messageAndThrowable(MessageFormatter.format(format, arg)));
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled()) {
            mockLogService.log(LogService.LOG_ERROR, messageAndThrowable(MessageFormatter.format(format, arg1, arg2)));
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        if (isErrorEnabled()) {
            mockLogService.log(LogService.LOG_ERROR, messageAndThrowable(MessageFormatter.arrayFormat(format, arguments)));
        }
    }

    @Override
    public <E extends Exception> void error(LoggerConsumer<E> consumer) throws E {
        /* Empty implementation */

    }

    @Override
    public void audit(String message) {
        mockLogService.log(LogService.LOG_ERROR, message);
    }

    @Override
    public void audit(String format, Object arg) {
        mockLogService.log(LogService.LOG_ERROR, messageAndThrowable(MessageFormatter.format(format, arg)));
    }

    @Override
    public void audit(String format, Object arg1, Object arg2) {
        mockLogService.log(LogService.LOG_ERROR, messageAndThrowable(MessageFormatter.format(format, arg1, arg2)));
    }

    @Override
    public void audit(String format, Object... arguments) {
        mockLogService.log(LogService.LOG_ERROR, messageAndThrowable(MessageFormatter.arrayFormat(format, arguments)));
    }

    private String messageAndThrowable(FormattingTuple format) {
        return format.getThrowable() != null ?
            format.getMessage() + " " + format.getThrowable() :
            format.getMessage();
    }

}
