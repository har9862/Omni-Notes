/*
 * Copyright (C) 2016 Federico Iosue (federico.iosue@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundatibehaon, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.feio.android.omninotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.utils.Constants;


public class BaseAndroidTestCase extends AndroidTestCase {

    protected DbHelper dbHelper;
    protected Context testContext;
    protected SharedPreferences prefs;
    private final String DB_PATH_REGEX = ".*it\\.feio\\.android\\.omninotes.*\\/databases\\/test_omni-notes.*";


    @Override
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        prefs = testContext.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_MULTI_PROCESS);
        dbHelper = DbHelper.getInstance(testContext);
        assertTrue(dbHelper.getDatabase().getPath().matches(DB_PATH_REGEX));
        cleanDatabase();
    }


    @Override
    protected void tearDown() throws Exception {
        testContext.deleteDatabase(DbHelper.getInstance().getDatabaseName());
        super.tearDown();
    }

    protected void cleanDatabase() {
        dbHelper.getDatabase().delete(DbHelper.TABLE_NOTES, null, null);
        dbHelper.getDatabase().delete(DbHelper.TABLE_CATEGORY, null, null);
        dbHelper.getDatabase().delete(DbHelper.TABLE_ATTACHMENTS, null, null);
    }

}
