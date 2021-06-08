/*
 * Copyright 2017 Steinar Bang
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

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import no.priv.bang.osgi.service.adapters.jdbc.internal.NullDataSource;

/**
 * An adapter for the OSGi service {@link DataSourceFactory} that can be used
 * where a {@link DataSourceFactory} is expected, and will return non-null
 * results that can be used without triggering {@link NullPointerException}.
 *
 * When a real {@link DataSourceFactory} arrives, it can be added to the
 * atapter using the {@link #setFactory(DataSourceFactory)} method.
 *
 * @author Steinar Bang
 *
 */
public class DataSourceFactoryAdapter implements DataSourceFactory {
    DataSourceFactory factory = null;

    /**
     * Set a {@link DataSourceFactory} that this adapter will delegate
     * all calls to.
     *
     * @param dataSourceFactory a real OSGi service that is received from the OSGi service registry
     */
    public void setFactory(DataSourceFactory dataSourceFactory) {
        factory = dataSourceFactory;
    }

    /**
     * If a {@link DataSourceFactory} has been set using the {@link #setFactory(DataSourceFactory)}
     * method, this call is passed on to that object's {@link DataSourceFactory#createDataSource(Properties)}
     * method and the return value from that call is returned.
     *
     * <p>If no factory has been set, this call will return a {@link NullDataSource} instance.
     *
     * @param props JDBC connection properties (JDBC URL, JDBC username, JDBC password).
     * @return either a real {@link DataSource} or a {@link NullDataSource}
     */
    @Override
    public DataSource createDataSource(Properties props) throws SQLException {
        if (factory == null) {
            return NullDataSource.getInstance();
        }

        return factory.createDataSource(props);
    }

    @Override
    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
        if (factory == null) {
            return null;
        }

        return factory.createConnectionPoolDataSource(props);
    }

    @Override
    public XADataSource createXADataSource(Properties props) throws SQLException {
        if (factory == null) {
            return null;
        }

        return factory.createXADataSource(props);
    }

    @Override
    public Driver createDriver(Properties props) throws SQLException {
        if (factory == null) {
            return null;
        }

        return factory.createDriver(props);
    }

}
