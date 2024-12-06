/*
 * Copyright 2021-2022 Steinar Bang
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
package no.priv.bang.osgi.service.adapters.tests;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;

import java.util.stream.Stream;

import org.apache.karaf.itests.KarafTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class AdaptersIntegrationTest extends KarafTestSupport {

    @Configuration
    @Override
    public Option[] config() {
        final var logserviceAdapterFeatureRepo = maven()
            .groupId("no.priv.bang.osgi.service.adapters")
            .artifactId("adapters.logservice")
            .version("LATEST")
            .type("xml")
            .classifier("features");
        final var jdbcAdapterFeatureRepo = maven()
            .groupId("no.priv.bang.osgi.service.adapters")
            .artifactId("adapters.jdbc")
            .version("LATEST")
            .type("xml")
            .classifier("features");
        var options = new Option[] {
            features(logserviceAdapterFeatureRepo),
            features(jdbcAdapterFeatureRepo)
        };
        return Stream.of(super.config(), options).flatMap(Stream::of).toArray(Option[]::new);
    }

    @Test
    public void testLoadOsgiLogServiceAdapterFeature() throws Exception { // NOSONAR this test has an assert, just not an assert sonar recognizes
        installAndAssertFeature("adapter-for-osgi-logservice");
    }

    @Test
    public void testLoadOsgiJdbcAdaptersFeature() throws Exception { // NOSONAR this test has an assert, just not an assert sonar recognizes
        installAndAssertFeature("adapters-for-osgi-jdbc-services");
    }

}
