/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service;

import io.ebean.Database;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAllListedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseService {
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    private static Database commanderDatabase;
    private static Database commonDatabase;
    private static boolean initialized = false;
    private static boolean initializedCommon = false;

    public static void init() {
        EVENT_LISTENERS.add(EventService.addStaticListener(1, CommanderAllListedEvent.class, event -> ApplicationState.getInstance().getPreferredCommander().ifPresentOrElse(DatabaseService::linkDatabase, DatabaseService::disconnect)));

        initializedCommon = false;
        final File databaseFileDir = new File(OsConstants.getConfigDirectory());
        databaseFileDir.mkdirs();
        final File databaseFile = new File(OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + AppConstants.COMMON_DATABASE_FILE);
        String url = "jdbc:sqlite:" + databaseFile.getAbsolutePath();

        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setName("common")
                .setUrl(url)
                .setUsername("")
                .setPassword("");
//        DatabaseConfig config = new DatabaseConfig();
//        config.setDataSourceConfig(dataSourceConfig);
//        config.setDefaultServer(true);
//        config.setName("common");
        // create database instance
//        commonDatabase = DatabaseFactory.create(config);
        commonDatabase = Database.builder()
                .name("common")
                .defaultDatabase(true)
                .dataSourceBuilder(dataSourceConfig)
                .build();
        MigrationConfig cfg = new MigrationConfig();

        cfg.setDbUrl(url);
        cfg.setMigrationPath("database/common");
        cfg.setDbUsername("");
        cfg.setDbPassword("");

        MigrationRunner runner = new MigrationRunner(cfg);
        runner.run();
        initializedCommon = true;
    }

    private static void linkDatabase(Commander commander) {
        disconnect();
        final String pathname = commander.getCommanderFolder();
        final File databaseFileDir = new File(pathname);
        databaseFileDir.mkdirs();
        final File databaseFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.COMMANDER_DATABASE_FILE);
        String url = "jdbc:sqlite:" + databaseFile.getAbsolutePath();
        initialized = false;


        try {

            DataSourceConfig dataSourceConfig = new DataSourceConfig()
                    .setName("commander")
                    .setUrl(url)
                    .setUsername(commander.getFid())
                    .setPassword(commander.getFid());
//            DatabaseConfig config = new DatabaseConfig();
//            config.setDefaultServer(false);
//            config.setDataSourceConfig(dataSourceConfig);
//            config.setName("commander");

            // create database instance
//            commanderDatabase = DatabaseFactory.create(config);
            commanderDatabase = Database.builder()
                    .name("commander")
                    .defaultDatabase(false)
                    .dataSourceBuilder(dataSourceConfig)
                    .build();
            MigrationConfig cfg = new MigrationConfig();

            cfg.setDbUrl(url);
            cfg.setMigrationPath("database/commander");
            cfg.setDbUsername(commander.getFid());
            cfg.setDbPassword(commander.getFid());

            MigrationRunner runner = new MigrationRunner(cfg);
            runner.run();
            initialized = true;

        } catch (Exception e) {
            throw new DatabaseException("Database initialization error", e);
        }
    }

    private static void disconnect() {
        if (commanderDatabase != null) {
            try {
                commanderDatabase.shutdown();
                commanderDatabase = null;
                initialized = false;
            } catch (Exception e) {
                throw new DatabaseException("Database close error", e);
            }
        }
    }

    public static Database getCommanderDatabase() {
        if (commanderDatabase == null) {
            throw new DatabaseException("Database not available");
        }
        if (!initialized) {
            throw new DatabaseException("Database not available due to initialization failure");
        }
        return commanderDatabase;
    }

    public static Database getCommonDatabase() {
        if (commonDatabase == null) {
            throw new DatabaseException("Database not available");
        }
        if (!initializedCommon) {
            throw new DatabaseException("Database not available due to initialization failure");
        }
        return commonDatabase;
    }
}
