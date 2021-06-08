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
 */package no.priv.bang.osgi.service.mocks.logservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.osgi.service.log.Logger;

class MockLoggerTest {

    @Test
    void testTraceOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        String name = "TestLogger";
        Logger logger = mockLogService.getLogger(name);
        assertTrue(logger.isTraceEnabled());
        assertEquals(name, logger.getName());
        String message1 = "Message 1";
        logger.trace(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[DEBUG] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        logger.trace("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[DEBUG] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        logger.trace("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[DEBUG] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        logger.trace("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[DEBUG] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setTraceEnabled(false);
        assertFalse(logger.isTraceEnabled());
        logger.trace("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        logger.trace("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        logger.trace("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        logger.trace("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testDebugOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        String name = "TestLogger";
        Logger logger = mockLogService.getLogger(name);
        assertTrue(logger.isDebugEnabled());
        assertEquals(name, logger.getName());
        String message1 = "Message 1";
        logger.debug(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[DEBUG] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        logger.debug("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[DEBUG] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        logger.debug("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[DEBUG] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        logger.debug("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[DEBUG] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setDebugEnabled(false);
        assertFalse(logger.isDebugEnabled());
        logger.debug("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        logger.debug("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        logger.debug("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        logger.debug("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testInfoOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        String name = "TestLogger";
        Logger logger = mockLogService.getLogger(name);
        assertTrue(logger.isInfoEnabled());
        assertEquals(name, logger.getName());
        String message1 = "Message 1";
        logger.info(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[INFO] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        logger.info("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[INFO] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        logger.info("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[INFO] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        logger.info("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[INFO] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setInfoEnabled(false);
        assertFalse(logger.isInfoEnabled());
        logger.info("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        logger.info("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        logger.info("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        logger.info("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testWarningOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        String name = "TestLogger";
        Logger logger = mockLogService.getLogger(name);
        assertTrue(logger.isWarnEnabled());
        assertEquals(name, logger.getName());
        String message1 = "Message 1";
        logger.warn(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[WARNING] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        logger.warn("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[WARNING] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        logger.warn("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[WARNING] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        logger.warn("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[WARNING] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setWarnEnabled(false);
        assertFalse(logger.isWarnEnabled());
        logger.warn("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        logger.warn("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        logger.warn("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        logger.warn("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testErrorOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        String name = "TestLogger";
        Logger logger = mockLogService.getLogger(name);
        assertTrue(logger.isErrorEnabled());
        assertEquals(name, logger.getName());
        String message1 = "Message 1";
        logger.error(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[ERROR] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        logger.error("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[ERROR] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        logger.error("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[ERROR] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        logger.error("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[ERROR] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setErrorEnabled(false);
        assertFalse(logger.isErrorEnabled());
        logger.error("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        logger.error("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        logger.error("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        logger.error("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testAuditOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        String name = "TestLogger";
        Logger logger = mockLogService.getLogger(name);
        assertEquals(name, logger.getName());
        String message1 = "Message 1";
        logger.audit(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[ERROR] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        logger.audit("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[ERROR] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        logger.audit("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[ERROR] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        logger.audit("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[ERROR] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
    }

    @Test
    void testLoggerWithClass() {
        MockLogService mockLogService = new MockLogService();
        Logger logger = mockLogService.getLogger(MockLoggerTest.class);
        assertEquals(MockLoggerTest.class.getCanonicalName(), logger.getName());
    }

    @Test
    void testLoggerWithLoggertypeAndName() {
        String name = "Testlogger";
        MockLogService mockLogService = new MockLogService();
        Logger logger = mockLogService.getLogger(name, Logger.class);
        assertEquals(name, logger.getName());
    }

    @Test
    void testLoggerWithLoggertypeAndClass() {
        MockLogService mockLogService = new MockLogService();
        Logger logger = mockLogService.getLogger(MockLoggerTest.class, Logger.class);
        assertEquals(MockLoggerTest.class.getCanonicalName(), logger.getName());
    }

    @Test
    void testLoggerWithUnknownLoggertypeAndName() {
        String name = "Testlogger";
        MockLogService mockLogService = new MockLogService();
        Logger logger = mockLogService.getLogger(name, MockLogger.class);
        assertNull(logger);
    }

    @Test
    void testLoggerWithUnknownLoggertypeAndClass() {
        MockLogService mockLogService = new MockLogService();
        Logger logger = mockLogService.getLogger(MockLoggerTest.class, MockLogger.class);
        assertNull(logger);
    }

}
