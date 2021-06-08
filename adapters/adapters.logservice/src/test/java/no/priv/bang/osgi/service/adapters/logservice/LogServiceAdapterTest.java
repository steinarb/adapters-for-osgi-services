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
package no.priv.bang.osgi.service.adapters.logservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.FormatterLogger;
import org.osgi.service.log.LogService;
import org.osgi.service.log.Logger;

import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

@SuppressWarnings("rawtypes")
class LogServiceAdapterTest {

    @Test
    void testSaveAndPassOnLogMessages() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();

        // Log some messages that should be logged
        adapter.log(LogService.LOG_DEBUG, "This is a debug message");
        adapter.log(LogService.LOG_INFO, "This is an info message", new IllegalStateException());
        ServiceReference service = mock(ServiceReference.class);
        when(service.toString()).thenReturn("<service>");
        adapter.log(service, LogService.LOG_WARNING, "This is a warning message");
        adapter.log(service, LogService.LOG_ERROR, "This is an error message", new IllegalArgumentException("a"));

        // Verify that the logservice doesn't have any logmessages
        assertEquals(0, logservice.getLogmessages().size());

        // Set a logservice into the adapter
        adapter.setLogService(logservice);

        // Verify that the logservice have received the saved log messages
        assertEquals(4, logservice.getLogmessages().size());

        // Verify that if setting a new logservice, there will be no new log messages
        // (ie. saved messages were cleared, when the first logservice was set)
        MockLogService newLogservice = new MockLogService();
        adapter.setLogService(newLogservice);
        assertEquals(0, newLogservice.getLogmessages().size());

        // Log 4 messages through the adapter and verify that they are picked up
        // by the new logservice
        adapter.log(LogService.LOG_DEBUG, "This is a debug message");
        adapter.log(LogService.LOG_INFO, "This is an info message", new IllegalStateException());
        adapter.log(service, LogService.LOG_WARNING, "This is a warning message");
        adapter.log(service, LogService.LOG_ERROR, "This is an error message", new IllegalArgumentException("a"));

        assertEquals(4, newLogservice.getLogmessages().size());
    }

    /**
     * Corner case test: verify that injecting a null log serivice will not
     * clear saved log messages
     */
    @Test
    void testNullLogServiceInjection() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();

        // Log some messages that should be logged
        adapter.log(LogService.LOG_DEBUG, "This is a debug message");
        adapter.log(LogService.LOG_INFO, "This is an info message", new IllegalStateException());
        ServiceReference service = mock(ServiceReference.class);
        when(service.toString()).thenReturn("<service>");
        adapter.log(service, LogService.LOG_WARNING, "This is a warning message");
        adapter.log(service, LogService.LOG_ERROR, "This is an error message", new IllegalArgumentException("a"));

        // Verify that the logservice doesn't have any logmessages
        assertEquals(0, logservice.getLogmessages().size());

        // Inject a null logservice
        adapter.setLogService(null);

        // Set a logservice into the adapter
        adapter.setLogService(logservice);

        // Verify that the logservice have received the saved log messages
        assertEquals(4, logservice.getLogmessages().size());
    }

    @Test
    void testGetLoggerWithName() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();
        adapter.setLogService(logservice);
        String name = "TestLogger";
        Logger logger = adapter.getLogger(name);
        assertEquals(name, logger.getName());
        logger.info("Message {}", Integer.valueOf(123));
        assertEquals("[INFO] Message 123", logservice.getLogmessages().get(0));
    }

    @Test
    void testGetLoggerWithClass() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();
        adapter.setLogService(logservice);
        Logger logger = adapter.getLogger(LogServiceAdapterTest.class);
        assertEquals(LogServiceAdapterTest.class.getName(), logger.getName());
        logger.info("Message {}", Integer.valueOf(123));
        assertEquals("[INFO] Message 123", logservice.getLogmessages().get(0));
    }

    @Test
    void testGetFormatterLoggerWithName() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();
        adapter.setLogService(logservice);
        String name = "TestLogger";
        FormatterLogger logger = adapter.getLogger(name, FormatterLogger.class);
        assertEquals(name, logger.getName());
        logger.info("Message %d", 123);
        assertEquals("[INFO] Message 123", logservice.getLogmessages().get(0));
    }

    @Test
    void testGetFormatterLoggerWithBundleAndName() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();
        adapter.setLogService(logservice);
        String name = "TestLogger";
        Bundle bundle = mock(Bundle.class);
        FormatterLogger logger = adapter.getLogger(bundle, name, FormatterLogger.class);
        assertEquals(name, logger.getName());
        logger.info("Message %d", 123);
        assertEquals("[INFO] Message 123", logservice.getLogmessages().get(0));
    }

    @Test
    void testGetFormatterLoggerWithClass() {
        MockLogService logservice = new MockLogService();
        LogServiceAdapter adapter = new LogServiceAdapter();
        adapter.setLogService(logservice);
        FormatterLogger logger = adapter.getLogger(LogServiceAdapterTest.class, FormatterLogger.class);
        assertEquals(LogServiceAdapterTest.class.getName(), logger.getName());
        logger.info("Message %d", 123);
        assertEquals("[INFO] Message 123", logservice.getLogmessages().get(0));
    }

}
