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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class LoggerAdapterTest {

    @Test
    void testTraceOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        LoggerAdapter adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isTraceEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        String message1 = "Message 1";
        adapter.trace(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[DEBUG] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        adapter.trace("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[DEBUG] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        adapter.trace("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[DEBUG] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        adapter.trace("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[DEBUG] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setTraceEnabled(false);
        assertFalse(adapter.isTraceEnabled());
        adapter.trace("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        adapter.trace("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        adapter.trace("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        adapter.trace("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testDebugOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        LoggerAdapter adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isDebugEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        String message1 = "Message 1";
        adapter.debug(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[DEBUG] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        adapter.debug("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[DEBUG] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        adapter.debug("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[DEBUG] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        adapter.debug("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[DEBUG] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setDebugEnabled(false);
        assertFalse(adapter.isDebugEnabled());
        adapter.debug("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        adapter.debug("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        adapter.debug("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        adapter.debug("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testInfoOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        LoggerAdapter adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isInfoEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        String message1 = "Message 1";
        adapter.info(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[INFO] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        adapter.info("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[INFO] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        adapter.info("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[INFO] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        adapter.info("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[INFO] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setInfoEnabled(false);
        assertFalse(adapter.isInfoEnabled());
        adapter.info("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        adapter.info("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        adapter.info("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        adapter.info("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testWarningOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        LoggerAdapter adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isWarnEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        String message1 = "Message 1";
        adapter.warn(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[WARNING] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        adapter.warn("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[WARNING] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        adapter.warn("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[WARNING] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        adapter.warn("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[WARNING] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setWarnEnabled(false);
        assertFalse(adapter.isWarnEnabled());
        adapter.warn("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        adapter.warn("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        adapter.warn("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        adapter.warn("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testErrorOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        LoggerAdapter adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertTrue(adapter.isErrorEnabled());
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        String message1 = "Message 1";
        adapter.error(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[ERROR] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        adapter.error("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[ERROR] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        adapter.error("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[ERROR] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        adapter.error("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[ERROR] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
        mockLogService.setErrorEnabled(false);
        assertFalse(adapter.isErrorEnabled());
        adapter.error("Message 5");
        int logCount5 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount5);
        adapter.error("Message 6 {}", Integer.valueOf(123));
        int logCount6 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount6);
        adapter.error("Message 7 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount7 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount7);
        adapter.error("Message 8 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount8 = mockLogService.getLogmessages().size();
        assertEquals(logCount4, logCount8);
    }

    @Test
    void testAuditOnLoggerWithName() {
        MockLogService mockLogService = new MockLogService();
        LoggerAdapter adapter = new LoggerAdapter(LoggerAdapterTest.class);
        adapter.setLogService(mockLogService);
        assertEquals(LoggerAdapterTest.class.getCanonicalName(), adapter.getName());
        String message1 = "Message 1";
        adapter.audit(message1);
        int logCount1 = mockLogService.getLogmessages().size();
        assertThat(logCount1).isPositive();
        assertEquals("[ERROR] " + message1, mockLogService.getLogmessages().get(logCount1 - 1));
        adapter.audit("Message 2 {}", Integer.valueOf(123));
        int logCount2 = mockLogService.getLogmessages().size();
        assertThat(logCount2).isGreaterThan(logCount1);
        assertEquals("[ERROR] Message 2 123", mockLogService.getLogmessages().get(logCount2 - 1));
        adapter.audit("Message 3 {} {}", Integer.valueOf(123), Integer.valueOf(456));
        int logCount3 = mockLogService.getLogmessages().size();
        assertThat(logCount3).isGreaterThan(logCount2);
        assertEquals("[ERROR] Message 3 123 456", mockLogService.getLogmessages().get(logCount3 - 1));
        adapter.audit("Message 4 {} {} {}", Integer.valueOf(123), Integer.valueOf(456), Integer.valueOf(789));
        int logCount4 = mockLogService.getLogmessages().size();
        assertThat(logCount4).isGreaterThan(logCount3);
        assertEquals("[ERROR] Message 4 123 456 789", mockLogService.getLogmessages().get(logCount4 - 1));
    }

}
