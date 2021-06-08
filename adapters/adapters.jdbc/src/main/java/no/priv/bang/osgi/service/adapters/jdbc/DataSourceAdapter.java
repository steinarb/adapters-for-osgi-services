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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import no.priv.bang.osgi.service.adapters.jdbc.internal.NullDataSource;

/**
 * This class isn't an OSGi service adapter, but it is an adapter for an interface that
 * is returned by {@link DataSourceFactory#createDataSource(java.util.Properties)}.
 *
 * <p>This class is included in the same library as the {@link DataSourceFactoryAdapter}
 * because creating a datasource happens either after receiving a {@link DataSourceFactory}
 * service injection or when a <a href="http://enroute.osgi.org/services/org.osgi.service.component.html">Declarative Services</a>
 * component goes active, which in both cases is long after the constructor call.
 * And Sonar insists that servlets (servlets are common components when using
 * <a href="http://ops4j.github.io/pax/web/SNAPSHOT/User-Guide.html#whiteboard-extender">the
 * pax web whiteboard extender</a>) should have only final fields.
 *
 * @author Steinar Bang
 *
 */
public class DataSourceAdapter implements DataSource {

    DataSource datasource = NullDataSource.getInstance();

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return datasource.getLogWriter();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return datasource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return datasource.getParentLogger();
    }

    @Override
    public void setLogWriter(PrintWriter log) throws SQLException {
        datasource.setLogWriter(log);
    }

    @Override
    public void setLoginTimeout(int timeout) throws SQLException {
        datasource.setLoginTimeout(timeout);
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return datasource.isWrapperFor(clazz);
    }

    @Override
    public <T> T unwrap(Class<T> clazz) throws SQLException {
        return datasource.unwrap(clazz);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return datasource.getConnection(username, password);
    }

    public void setDatasource(DataSource db) {
        if (db == null) {
            datasource = NullDataSource.getInstance();
        } else {
            datasource = db;
        }
    }

}
