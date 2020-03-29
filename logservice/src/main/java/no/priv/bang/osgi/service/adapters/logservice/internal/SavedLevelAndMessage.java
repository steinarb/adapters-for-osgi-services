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
package no.priv.bang.osgi.service.adapters.logservice.internal;

import org.osgi.service.log.LogService;

public class LevelAndMessage implements SavedLogMessage {

    protected int level;
    protected String message;

    public LevelAndMessage(int level, String message) {
        this.level = level;
        this.message = message;
    }

    @Override
    public void sendToService(LogService logservice) {
        logservice.log(level, message);
    }

}
