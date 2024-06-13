/*
 * Copyright 2017-2024 Steinar Bang
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
package no.priv.bang.osgi.service.adapters.jdbc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.priv.bang.osgi.service.adapters.jdbc.internal.NullDataSource;

class DataSourceAdapterTest {

    @BeforeEach
    void before() throws SQLException {
        NullDataSource.getInstance().setLoginTimeout(0);
        NullDataSource.getInstance().setLogWriter(null);
    }

    @Test
    void testThatAdapterInitiallyWrapsNullDataSource() throws SQLException {
        var adapter = new DataSourceAdapter();
        assertNull(adapter.getConnection());
        assertNull(adapter.getConnection("userdoesntmatter", "passwordoesntmatter"));
        assertNull(adapter.getParentLogger());

        // Verify that setting the log is transferred to the NullDataSource
        assertNull(NullDataSource.getInstance().getLogWriter());
        var log = mock(PrintWriter.class);
        adapter.setLogWriter(log);
        assertEquals(log, NullDataSource.getInstance().getLogWriter());
        assertEquals(adapter.getLogWriter(), NullDataSource.getInstance().getLogWriter());

        // Verify that setting the timeout is transferred to the NullDataSource
        assertEquals(0, NullDataSource.getInstance().getLoginTimeout());
        var timeout = 3316;
        adapter.setLoginTimeout(timeout);
        assertEquals(timeout, NullDataSource.getInstance().getLoginTimeout());
        assertEquals(adapter.getLoginTimeout(), NullDataSource.getInstance().getLoginTimeout());

        // Verify that it doesn't matter what class is sent to the adaptive method
        assertFalse(adapter.isWrapperFor(getClass()));
        assertFalse(adapter.isWrapperFor(null));
        assertNull(adapter.unwrap(getClass()));
        assertNull(adapter.unwrap(null));
    }

    @Test
    void testSetDatasource() throws SQLException {
        var adapter = new DataSourceAdapter();

        // Set a new datasource into the adapter
        var datasource = mock(DataSource.class);
        adapter.setDatasource(datasource);

        // Verify that setting the timeout now isn't transferred to the NullDataSource
        assertEquals(0, NullDataSource.getInstance().getLoginTimeout());
        var timeout = 3316;
        adapter.setLoginTimeout(timeout);
        assertNotEquals(timeout, NullDataSource.getInstance().getLoginTimeout());

        // Verify that setting the datasource to null will cause the adapter to use the NullDataSource
        adapter.setDatasource(null);
        assertEquals(0, NullDataSource.getInstance().getLoginTimeout());
        var timeout2 = 7754;
        adapter.setLoginTimeout(timeout2);
        assertEquals(timeout2, NullDataSource.getInstance().getLoginTimeout());
    }

}
