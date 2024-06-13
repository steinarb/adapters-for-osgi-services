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
package no.priv.bang.osgi.service.adapters.jdbc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.XADataSource;

import org.junit.jupiter.api.Test;
import org.osgi.service.jdbc.DataSourceFactory;

import no.priv.bang.osgi.service.adapters.jdbc.internal.NullDataSource;

class DataSourceFactoryAdapterTest {

    @Test
    void testCreateDataSourceNoInjection() throws SQLException {
        var adapter = new DataSourceFactoryAdapter();

        assertEquals(NullDataSource.getInstance(), adapter.createDataSource(null));
    }

    @Test
    void testCreateDataSource() throws SQLException {
        var factory = mock(DataSourceFactory.class);

        var adapter = new DataSourceFactoryAdapter();
        adapter.setFactory(factory);

        assertNull(adapter.createDataSource(null));
    }

    @Test
    void testCreateConnectionPoolDataSourceNoInjection() throws SQLException {
        var adapter = new DataSourceFactoryAdapter();

        assertNull(adapter.createConnectionPoolDataSource(null));
    }

    @Test
    void testCreateConnectionPoolDataSource() throws SQLException {
        var factory = mock(DataSourceFactory.class);
        var datasource = mock(ConnectionPoolDataSource.class);
        when(factory.createConnectionPoolDataSource(any())).thenReturn(datasource);

        var adapter = new DataSourceFactoryAdapter();
        adapter.setFactory(factory);

        assertNotNull(adapter.createConnectionPoolDataSource(null));
    }

    @Test
    void testCreateXADataSourceNoInjection() throws SQLException {
        var adapter = new DataSourceFactoryAdapter();

        assertNull(adapter.createXADataSource(null));
    }

    @Test
    void testCreateXADataSource() throws SQLException {
        var factory = mock(DataSourceFactory.class);
        var datasource = mock(XADataSource.class);
        when(factory.createXADataSource(any())).thenReturn(datasource);

        var adapter = new DataSourceFactoryAdapter();
        adapter.setFactory(factory);

        assertNotNull(adapter.createXADataSource(null));
    }

    @Test
    void testCreateDriverNoInjection() throws SQLException {
        var adapter = new DataSourceFactoryAdapter();

        assertNull(adapter.createDriver(null));
    }

    @Test
    void testCreateDriver() throws SQLException {
        var factory = mock(DataSourceFactory.class);
        var driver = mock(Driver.class);
        when(factory.createDriver(any())).thenReturn(driver);

        var adapter = new DataSourceFactoryAdapter();
        adapter.setFactory(factory);

        assertNotNull(adapter.createDriver(null));
    }

}
