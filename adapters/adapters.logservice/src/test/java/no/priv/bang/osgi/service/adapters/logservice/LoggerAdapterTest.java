package no.priv.bang.osgi.service.adapters.logservice;
/*
 * Copyright 2021-2026 Steinar Bang
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.osgi.service.log.LogService;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerConsumer;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class LoggerAdapterTest {
    private static ch.qos.logback.classic.Logger logger;
    private ListAppender<ILoggingEvent> logappender;

    @BeforeEach
    void setup() {
        logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LoggerAdapterTest.class);
        logappender = new ListAppender<>();
        logappender.start();
        logger.addAppender(logappender);
    }

    @AfterEach
    void teardown() {
        logger.detachAppender(logappender);
        logappender.stop();
    }

    @Test
    void testTraceOnFallbackSlf4jLogger() {
        logger.setLevel(Level.TRACE);
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        assertThat(adapter.getName()).isEqualTo(getClass().getCanonicalName());
        assertThat(logappender.list).isEmpty();
        adapter.trace("message");
        assertThat(logappender.list).hasSize(1);
        adapter.trace("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        adapter.trace("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        adapter.trace("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        adapter.trace(l -> l.trace("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testTraceOnLoggerWithName() {
        var mockLogService = new MockLogService();
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isTraceEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        var message1 = "Message 1";
        adapter.trace(message1);
        assertThat(mockLogService.getLogmessages()).hasSize(1);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] " + message1);
        adapter.trace("Message 2 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(2);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] Message 2 123");
        adapter.trace("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(3);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] Message 3 123 456");
        adapter.trace("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(4);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] Message 4 123 456 789");
        var logCountBeforeSwitchingOffTrace = mockLogService.getLogmessages().size();
        mockLogService.setTraceEnabled(false);
        assertFalse(adapter.isTraceEnabled());
        adapter.trace("Message 5");
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffTrace);
        adapter.trace("Message 6 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffTrace);
        adapter.trace("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffTrace);
        adapter.trace("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffTrace);
        assertThat(logappender.list).isEmpty(); // Verify slf4j received no logs because logservice eats them
    }

    @Test
    void testTraceConsumer() throws Exception {
        var logservice = mock(LogService.class);
        var logger = mock(Logger.class);
        when(logservice.getLogger(any(Class.class))).thenReturn(logger);
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(logservice);

        var consumer = createMockLoggerConsumer();
        adapter.trace(consumer);
        verify(logger).trace(ArgumentMatchers.<LoggerConsumer<? extends Exception>>any());
    }

    @Test
    void testDebugOnFallbackSlf4jLogger() {
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        assertThat(adapter.getName()).isEqualTo(getClass().getCanonicalName());
        assertThat(logappender.list).isEmpty();
        adapter.debug("message");
        assertThat(logappender.list).hasSize(1);
        adapter.debug("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        adapter.debug("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        adapter.debug("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        adapter.debug(l -> l.debug("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testDebugOnLoggerWithName() {
        var mockLogService = new MockLogService();
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isDebugEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        var message1 = "Message 1";
        adapter.debug(message1);
        assertThat(mockLogService.getLogmessages()).hasSize(1);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] " + message1);
        adapter.debug("Message 2 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(2);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] Message 2 123");
        adapter.debug("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(3);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] Message 3 123 456");
        adapter.debug("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(4);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[DEBUG] Message 4 123 456 789");
        var logCountBeforeSwitchingOffDebug = mockLogService.getLogmessages().size();
        mockLogService.setDebugEnabled(false);
        assertFalse(adapter.isDebugEnabled());
        adapter.debug("Message 5");
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffDebug);
        adapter.debug("Message 6 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffDebug);
        adapter.debug("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffDebug);
        adapter.debug("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffDebug);
        assertThat(logappender.list).isEmpty(); // Verify slf4j received no logs because logservice eats them
    }

    @Test
    void testDebugConsumer() throws Exception {
        var logservice = mock(LogService.class);
        var logger = mock(Logger.class);
        when(logservice.getLogger(any(Class.class))).thenReturn(logger);
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(logservice);

        var consumer = createMockLoggerConsumer();
        adapter.debug(consumer);
        verify(logger).debug(ArgumentMatchers.<LoggerConsumer<? extends Exception>>any());
    }

    @Test
    void testInfoOnFallbackSlf4jLogger() {
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        assertThat(adapter.getName()).isEqualTo(getClass().getCanonicalName());
        assertThat(logappender.list).isEmpty();
        adapter.info("message");
        assertThat(logappender.list).hasSize(1);
        adapter.info("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        adapter.info("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        adapter.info("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        adapter.info(l -> l.info("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testInfoOnLoggerWithName() {
        var mockLogService = new MockLogService();
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isInfoEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        var message1 = "Message 1";
        adapter.info(message1);
        assertThat(mockLogService.getLogmessages()).hasSize(1);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[INFO] " + message1);
        adapter.info("Message 2 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(2);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[INFO] Message 2 123");
        adapter.info("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(3);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[INFO] Message 3 123 456");
        adapter.info("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(4);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[INFO] Message 4 123 456 789");
        var logCountBeforeSwitchingOffInfo = mockLogService.getLogmessages().size();
        mockLogService.setInfoEnabled(false);
        assertFalse(adapter.isInfoEnabled());
        adapter.info("Message 5");
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffInfo);
        adapter.info("Message 6 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffInfo);
        adapter.info("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffInfo);
        adapter.info("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffInfo);
        assertThat(logappender.list).isEmpty(); // Verify slf4j received no logs because logservice eats them
    }

    @Test
    void testInfoConsumer() throws Exception {
        var logservice = mock(LogService.class);
        var logger = mock(Logger.class);
        when(logservice.getLogger(any(Class.class))).thenReturn(logger);
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(logservice);

        var consumer = createMockLoggerConsumer();
        adapter.info(consumer);
        verify(logger).info(ArgumentMatchers.<LoggerConsumer<? extends Exception>>any());
    }

    @Test
    void testWarningOnFallbackSlf4jLogger() {
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        assertThat(adapter.getName()).isEqualTo(getClass().getCanonicalName());
        assertThat(logappender.list).isEmpty();
        adapter.warn("message");
        assertThat(logappender.list).hasSize(1);
        adapter.warn("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        adapter.warn("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        adapter.warn("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        adapter.warn(l -> l.warn("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testWarningOnLoggerWithName() {
        var mockLogService = new MockLogService();
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isWarnEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        var message1 = "Message 1";
        adapter.warn(message1);
        assertThat(mockLogService.getLogmessages()).hasSize(1);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[WARNING] " + message1);
        adapter.warn("Message 2 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(2);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[WARNING] Message 2 123");
        adapter.warn("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(3);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[WARNING] Message 3 123 456");
        adapter.warn("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(4);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[WARNING] Message 4 123 456 789");
        var logCountBeforeSwitchingOffWarn = mockLogService.getLogmessages().size();
        mockLogService.setWarnEnabled(false);
        assertFalse(adapter.isWarnEnabled());
        adapter.warn("Message 5");
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffWarn);
        adapter.warn("Message 6 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffWarn);
        adapter.warn("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffWarn);
        adapter.warn("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffWarn);
        assertThat(logappender.list).isEmpty(); // Verify slf4j received no logs because logservice eats them
    }

    @Test
    void testWarningConsumer() throws Exception {
        var logservice = mock(LogService.class);
        var logger = mock(Logger.class);
        when(logservice.getLogger(any(Class.class))).thenReturn(logger);
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(logservice);

        var consumer = createMockLoggerConsumer();
        adapter.warn(consumer);
        verify(logger).warn(ArgumentMatchers.<LoggerConsumer<? extends Exception>>any());
    }

    @Test
    void testErrorOnFallbackSlf4jLogger() {
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        assertThat(adapter.getName()).isEqualTo(getClass().getCanonicalName());
        assertThat(logappender.list).isEmpty();
        adapter.error("message");
        assertThat(logappender.list).hasSize(1);
        adapter.error("message {}", "arrgh");
        assertThat(logappender.list).hasSize(2);
        adapter.error("message {} {}", "arg", 1);
        assertThat(logappender.list).hasSize(3);
        adapter.error("message {} {} {}", "arg", 1, 3.14);
        assertThat(logappender.list).hasSize(4);
        adapter.error(l -> l.warn("message in lambda"));
        assertThat(logappender.list).hasSize(5);
    }

    @Test
    void testErrorOnLoggerWithName() {
        var mockLogService = new MockLogService();
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isErrorEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        var message1 = "Message 1";
        adapter.error(message1);
        assertThat(mockLogService.getLogmessages()).hasSize(1);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] " + message1);
        adapter.error("Message 2 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(2);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] Message 2 123");
        adapter.error("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(3);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] Message 3 123 456");
        adapter.error("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(4);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] Message 4 123 456 789");
        var logCountBeforeSwitchingOffError = mockLogService.getLogmessages().size();
        mockLogService.setErrorEnabled(false);
        assertFalse(adapter.isErrorEnabled());
        adapter.error("Message 5");
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffError);
        adapter.error("Message 6 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffError);
        adapter.error("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffError);
        adapter.error("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(logCountBeforeSwitchingOffError);
        assertThat(logappender.list).isEmpty(); // Verify slf4j received no logs because logservice eats them
    }

    @Test
    void testErrorConsumer() throws Exception {
        var logservice = mock(LogService.class);
        var logger = mock(Logger.class);
        when(logservice.getLogger(any(Class.class))).thenReturn(logger);
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(logservice);

        var consumer = createMockLoggerConsumer();
        adapter.error(consumer);
        verify(logger).error(ArgumentMatchers.<LoggerConsumer<? extends Exception>>any());
    }

    @Test
    void testAuditOnLoggerWithName() {
        var mockLogService = new MockLogService();
        var adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        var message1 = "Message 1";
        adapter.audit(message1);
        assertThat(mockLogService.getLogmessages()).hasSize(1);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] " + message1);
        adapter.audit("Message 2 {}", Integer.valueOf(123));
        assertThat(mockLogService.getLogmessages()).hasSize(2);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] Message 2 123");
        adapter.audit("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        assertThat(mockLogService.getLogmessages()).hasSize(3);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] Message 3 123 456");
        adapter.audit("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        assertThat(mockLogService.getLogmessages()).hasSize(4);
        assertThat(mockLogService.getLogmessages().getLast()).isEqualTo("[ERROR] Message 4 123 456 789");
        assertThat(logappender.list).isEmpty(); // Verify slf4j received no logs because logservice eats them
    }

    private LoggerConsumer<Exception> createMockLoggerConsumer() {
        return new LoggerConsumer<Exception>() {

            @Override
            public void accept(Logger l) throws Exception {
                /* Do nothing */
            }
        };
    }

}
