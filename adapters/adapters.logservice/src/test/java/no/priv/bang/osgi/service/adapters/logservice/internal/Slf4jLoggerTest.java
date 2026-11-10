package no.priv.bang.osgi.service.adapters.logservice.internal;
/*
 * Copyright 2026 Steinar Bang
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class Slf4jLoggerTest {
    private static Logger logger;
    private ListAppender<ILoggingEvent> logappender;

    @BeforeEach
    void setup() {
        logger = (Logger) LoggerFactory.getLogger(Slf4jLoggerTest.class);
        logappender = new ListAppender<>();
        logappender.start();
        logger.addAppender(logappender);
    }

    @AfterEach
    void teardown() {
        logger.detachAppender(logappender);
    }

    @Test
    void testTrace() {
        logger.setLevel(Level.TRACE);
        var slf4jLogger = new Slf4jLogger(getClass());
        assertThat(slf4jLogger.getName()).isEqualTo(getClass().getCanonicalName());
        assertThat(logappender.list).isEmpty();
        slf4jLogger.trace("message");
        assertThat(logappender.list).hasSize(1);
        slf4jLogger.trace("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        slf4jLogger.trace("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        slf4jLogger.trace("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        slf4jLogger.trace(l -> l.trace("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testDebug() {
        logger.setLevel(Level.DEBUG);
        var slf4jLogger = new Slf4jLogger(getClass());

        // First verify trace not logging at this level
        assertThat(logappender.list).isEmpty();
        slf4jLogger.trace("message");
        slf4jLogger.trace("message {}", "arrgh");
        slf4jLogger.trace("message {} {}", "arg", 1);
        slf4jLogger.trace("message {} {} {}", "arg", 1, 3.14);
        slf4jLogger.trace(l -> l.debug("message in lambda")); // Lambda won't be executed in log level debug
        assertThat(logappender.list).isEmpty();

        // Check that debug logs log at that level
        slf4jLogger.debug("message");
        assertThat(logappender.list).hasSize(1);
        slf4jLogger.debug("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        slf4jLogger.debug("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        slf4jLogger.debug("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        slf4jLogger.debug(l -> l.debug("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testInfo() {
        logger.setLevel(Level.INFO);
        var slf4jLogger = new Slf4jLogger(getClass());

        // First verify debug not logging at this level
        assertThat(logappender.list).isEmpty();
        slf4jLogger.debug("message");
        slf4jLogger.debug("message {}", "arrgh");
        slf4jLogger.debug("message {} {}", "arg", 1);
        slf4jLogger.debug("message {} {} {}", "arg", 1, 3.14);
        slf4jLogger.debug(l -> l.info("message in lambda")); // Lambda won't be executed in log level info
        assertThat(logappender.list).isEmpty();

        // Check that info logs log at that level
        slf4jLogger.info("message");
        assertThat(logappender.list).hasSize(1);
        slf4jLogger.info("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        slf4jLogger.info("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        slf4jLogger.info("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        slf4jLogger.info(l -> l.info("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testWarn() {
        logger.setLevel(Level.WARN);
        var slf4jLogger = new Slf4jLogger(getClass());

        // First verify info not logging at this level
        assertThat(logappender.list).isEmpty();
        slf4jLogger.info("message");
        slf4jLogger.info("message {}", "arrgh");
        slf4jLogger.info("message {} {}", "arg", 1);
        slf4jLogger.info("message {} {} {}", "arg", 1, 3.14);
        slf4jLogger.info(l -> l.warn("message in lambda")); // Lambda won't be executed in log level warn
        assertThat(logappender.list).isEmpty();

        // Check that warn logging log at this level
        slf4jLogger.warn("message");
        assertThat(logappender.list).hasSize(1);
        slf4jLogger.warn("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        slf4jLogger.warn("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        slf4jLogger.warn("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        slf4jLogger.warn(l -> l.warn("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testError() {
        logger.setLevel(Level.ERROR);
        var slf4jLogger = new Slf4jLogger(getClass());

        // First verify warn not logging at this level
        assertThat(logappender.list).isEmpty();
        slf4jLogger.warn("message");
        slf4jLogger.warn("message {}", "arrgh");
        slf4jLogger.warn("message {} {}", "arg", 1);
        slf4jLogger.warn("message {} {} {}", "arg", 1, 3.14);
        slf4jLogger.warn(l -> l.error("message in lambda")); // Lambda won't be executed in log level warn
        assertThat(logappender.list).isEmpty();

        // Check that error logging log at this level
        slf4jLogger.error("message");
        assertThat(logappender.list).hasSize(1);
        slf4jLogger.error("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        slf4jLogger.error("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        slf4jLogger.error("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        slf4jLogger.error(l -> l.error("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testLoggingOff() {
        logger.setLevel(Level.OFF);
        var slf4jLogger = new Slf4jLogger(getClass());

        // First verify error not logging at this level
        assertThat(logappender.list).isEmpty();
        slf4jLogger.error("message");
        slf4jLogger.error("message {}", "arrgh");
        slf4jLogger.error("message {} {}", "arg", 1);
        slf4jLogger.error("message {} {} {}", "arg", 1, 3.14);
        slf4jLogger.error(l -> fail()); // Lambda won't be executed in log level warn
        assertThat(logappender.list).isEmpty();
    }

    @Test
    void testAudit() {
        logger.setLevel(Level.INFO);
        var slf4jLogger = new Slf4jLogger(getClass());

        // Check that info logs log at that level
        slf4jLogger.audit("message");
        assertThat(logappender.list).hasSize(1);
        slf4jLogger.audit("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        slf4jLogger.audit("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        slf4jLogger.audit("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
    }

}
