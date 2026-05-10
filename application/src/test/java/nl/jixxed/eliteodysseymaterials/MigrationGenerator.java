/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials;

import io.ebean.annotation.Platform;
import io.ebean.config.DatabaseConfig;
import io.ebean.dbmigration.DbMigration;
import nl.jixxed.eliteodysseymaterials.persistence.commander.model.CommunityGoalModel;
import nl.jixxed.eliteodysseymaterials.persistence.common.model.StarSystemModel;

import java.io.IOException;

public class MigrationGenerator {

//    /**
//     * Generate the next "DB schema DIFF" migration.
//     */
//    public static void main(String[] args) throws IOException {
//        DatabaseConfig config1 = new DatabaseConfig();
//        config1.setName("commander");
//        config1.setDefaultServer(true);
//        config1.setRegister(false);
//        config1.loadModuleInfo(false);
////        config1.addPackage("nl.jixxed.eliteodysseymaterials.persistence.commander.model");
//        config1.addClass(CommunityGoalModel.class);
//        config1.setDisableClasspathSearch(true);
////Database database1 =DatabaseFactory.create(config1);
//        DbMigration dbMigration = DbMigration.create();
////        dbMigration.setServer(database1);
//        dbMigration.setName("commander");
//        dbMigration.setServerConfig(config1);
//        dbMigration.setPlatform(Platform.SQLITE);
//        dbMigration.setPathToResources("application/src/main/resources");
//        dbMigration.setMigrationPath("database/commander");
//        dbMigration.generateMigration();
//
//        DatabaseConfig config2 = new DatabaseConfig();
//        config2.setName("common");
//        config2.setDefaultServer(false);
//        config2.setRegister(false);
//        config2.loadModuleInfo(false);
//        config2.setDisableClasspathSearch(true);
////        config2.addPackage("nl.jixxed.eliteodysseymaterials.persistence.common.model");
//        config2.addClass(StarSystemModel.class);
////        Database database2 = DatabaseFactory.create(config2);
//        DbMigration dbMigration2 = DbMigration.create();
////        dbMigration.setServer(database2);
//        dbMigration2.setName("common");
//        dbMigration2.setServerConfig(config2);
//        dbMigration2.setPlatform(Platform.SQLITE);
//        dbMigration2.setPathToResources("application/src/main/resources");
//        dbMigration2.setMigrationPath("database/common");
//        dbMigration2.generateMigration();
//    }
}