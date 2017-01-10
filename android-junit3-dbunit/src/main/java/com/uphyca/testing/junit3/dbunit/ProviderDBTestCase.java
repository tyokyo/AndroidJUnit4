/*
 * Copyright (C) 2012 uPhyca Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uphyca.testing.junit3.dbunit;

import java.io.InputStream;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

import android.content.ContentProvider;
import android.content.Context;
import android.test.ProviderTestCase2;

/**
 * TestCase that uses a AndroidSQLiteDatabaseTester.
 */
public abstract class ProviderDBTestCase<T extends ContentProvider> extends ProviderTestCase2<T> {

    private static final class DBTestCaseDelegate<T extends ContentProvider> extends DBTestCase {

        private ProviderDBTestCase<T> owner;

        public DBTestCaseDelegate(ProviderDBTestCase<T> owner) {
            this.owner = owner;
        }

        @Override
        protected IDatabaseTester newDatabaseTester() throws Exception {
            return owner.newDatabaseTester();
        }

        @Override
        protected IDataSet getDataSet() throws Exception {
            return owner.getDataSet();
        }

        @Override
        public void setUpDatabaseConfig(DatabaseConfig config) {
            super.setUpDatabaseConfig(config);
        }

        @Override
        public IDatabaseTester getDatabaseTester() throws Exception {
            return super.getDatabaseTester();
        }

        @Override
        public DatabaseOperation getSetUpOperation() throws Exception {
            return super.getSetUpOperation();
        }

        @Override
        public DatabaseOperation getTearDownOperation() throws Exception {
            return super.getTearDownOperation();
        }

        @Override
        public void setUp() throws Exception {
            super.setUp();
        }

        @Override
        public void tearDown() throws Exception {
            super.tearDown();
        }

        @Override
        public IOperationListener getOperationListener() {
            return super.getOperationListener();
        }

        public IDatabaseConnection callGetConnection() throws Exception {
            return getConnection();
        }
    }

    private final DBTestCaseDelegate<T> mDBTestCase;

    public ProviderDBTestCase(Class<T> providerClass,
                              String providerAuthority) {
        super(providerClass, providerAuthority);
        mDBTestCase = new DBTestCaseDelegate<T>(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#newDatabaseTester()
     */
    protected IDatabaseTester newDatabaseTester() throws Exception {
        return new AndroidSQLiteDatabaseTester(getMockContext(), getDatabaseName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getDataSet()
     */
    protected abstract IDataSet getDataSet() throws Exception;

    /**
     * Returns the test database name.
     */
    protected abstract String getDatabaseName();

    /**
     * Implements the code for create database.
     * @param context
     */
    protected abstract void onCreateDatabase(Context context);

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#setUpDatabaseConfig(org.dbunit.database.
     * DatabaseConfig)
     */
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        mDBTestCase.setUpDatabaseConfig(config);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getDatabaseTester()
     */
    protected IDatabaseTester getDatabaseTester() throws Exception {
        return mDBTestCase.getDatabaseTester();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getSetUpOperation()
     */
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return mDBTestCase.getSetUpOperation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getTearDownOperation()
     */
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return mDBTestCase.getTearDownOperation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        onCreateDatabase(getMockContext());

        mDBTestCase.setUp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        mDBTestCase.tearDown();
        super.tearDown();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getOperationListener()
     */
    protected IOperationListener getOperationListener() {
        return mDBTestCase.getOperationListener();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getConnection()
     */
    public IDatabaseConnection getConnection() throws Exception {
        IDatabaseConnection conn = mDBTestCase.callGetConnection();

        //SQLite.JDBC only accepts fetch size 1.
        //@see http://www.dbunit.org/properties.html
        conn.getConfig().setProperty("http://www.dbunit.org/properties/fetchSize",
                                     1);

        return conn;
    }

    //    /**
    //     * Gets the {@link IsolatedContext} created by this class during initialization.
    //     * @return The {@link IsolatedContext} instance
    //     */
    //    public IsolatedContext getMockContext() {
    //        return mDatabaseContext;
    //    }

    protected IDataSet getFlatXmlDataSetFromRawResrouce(int id) throws DataSetException {
        InputStream in = getContext().getResources().openRawResource(id);
        FlatXmlProducer producer = new FlatXmlProducer(new InputSource(in), false);
        return new FlatXmlDataSet(producer);
    }
    
    protected IDataSet getFlatXmlDataSetFromClasspathResrouce(String file) throws DataSetException {
        InputStream in = getContext().getClassLoader().getResourceAsStream(file);
        FlatXmlProducer producer = new FlatXmlProducer(new InputSource(in), false);
        return new FlatXmlDataSet(producer);
    }
}
