/*
 * Copyright 2021 Steinar Bang
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

import org.osgi.service.log.LogService;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerConsumer;

public class LoggerAdapter implements Logger {

    private Logger logger;
    private Class<?> clazz;

    public LoggerAdapter(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setLogService(LogService logService) {
        logger = logService.getLogger(clazz);
    }

    private Logger getLogger() {
        return logger;
    }

    @Override
    public String getName() {
        return getLogger().getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return getLogger().isTraceEnabled();
    }

    @Override
    public void trace(String message) {
        getLogger().trace(message);
    }

    @Override
    public void trace(String format, Object arg) {
        getLogger().trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        getLogger().trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        getLogger().trace(format, arguments);
    }

    @Override
    public <E extends Exception> void trace(LoggerConsumer<E> consumer) throws E {
        getLogger().trace(consumer);
    }

    @Override
    public boolean isDebugEnabled() {
        return getLogger().isDebugEnabled();
    }

    @Override
    public void debug(String message) {
        getLogger().debug(message);
    }

    @Override
    public void debug(String format, Object arg) {
        getLogger().debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        getLogger().debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        getLogger().debug(format, arguments);
    }

    @Override
    public <E extends Exception> void debug(LoggerConsumer<E> consumer) throws E {
        getLogger().debug(consumer);
    }

    @Override
    public boolean isInfoEnabled() {
        return getLogger().isInfoEnabled();
    }

    @Override
    public void info(String message) {
        getLogger().info(message);
    }

    @Override
    public void info(String format, Object arg) {
        getLogger().info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        getLogger().info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        getLogger().info(format, arguments);
    }

    @Override
    public <E extends Exception> void info(LoggerConsumer<E> consumer) throws E {
        getLogger().info(consumer);
    }

    @Override
    public boolean isWarnEnabled() {
        return getLogger().isWarnEnabled();
    }

    @Override
    public void warn(String message) {
        getLogger().warn(message);
    }

    @Override
    public void warn(String format, Object arg) {
        getLogger().warn(format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        getLogger().warn(format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        getLogger().warn(format, arguments);
    }

    @Override
    public <E extends Exception> void warn(LoggerConsumer<E> consumer) throws E {
        getLogger().warn(consumer);
    }

    @Override
    public boolean isErrorEnabled() {
        return getLogger().isErrorEnabled();
    }

    @Override
    public void error(String message) {
        getLogger().error(message);
    }

    @Override
    public void error(String format, Object arg) {
        getLogger().error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        getLogger().error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        getLogger().error(format, arguments);
    }

    @Override
    public <E extends Exception> void error(LoggerConsumer<E> consumer) throws E {
        getLogger().error(consumer);
    }

    @Override
    public void audit(String message) {
        getLogger().audit(message);
    }

    @Override
    public void audit(String format, Object arg) {
        getLogger().audit(format, arg);
    }

    @Override
    public void audit(String format, Object arg1, Object arg2) {
        getLogger().audit(format, arg1, arg2);
    }

    @Override
    public void audit(String format, Object... arguments) {
        getLogger().audit(format, arguments);
    }

}
