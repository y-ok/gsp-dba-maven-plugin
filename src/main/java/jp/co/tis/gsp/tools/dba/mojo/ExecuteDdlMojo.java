/*
 * Copyright (C) 2015 coastland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.tis.gsp.tools.dba.mojo;


import java.io.File;
import java.sql.SQLException;
import jp.co.tis.gsp.tools.dba.dialect.Dialect;
import jp.co.tis.gsp.tools.dba.dialect.DialectFactory;
import jp.co.tis.gsp.tools.dba.util.SqlExecutor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.seasar.framework.util.DriverManagerUtil;



/**
 * execute-ddl.
 * 
 * DDLを実行する。
 * 
 * @author kawasima
 */
@Mojo(name = "execute-ddl")
public class ExecuteDdlMojo extends AbstractDbaMojo {
    /**
     * DDL directory.
     */
    @Parameter(defaultValue = "target/ddl")
    protected File ddlDirectory;

    @Parameter
    protected File extraDdlDirectory;

    protected String delimiter = ";";


    @Override
    protected void executeMojoSpec() throws MojoExecutionException {
        DriverManagerUtil.registerDriver(driver);
        Dialect dialect = DialectFactory.getDialect(url, driver);
        
        // ユーザの作成を行います
        dialect.createUser(user, password, adminUser, adminPassword);
        
        // 指定スキーマ内のテーブル、ビュー、シーケンスを全て削除します。
        // 指定スキーマが存在しない場合は作成します。
        dialect.dropAll(user, password, adminUser, adminPassword, schema);
        
        // DDLの実行
        SqlExecutor sqlExecutor = new SqlExecutor(url, user, password, ddlDirectory, extraDdlDirectory, delimiter, onError, getLog());
        try {
            sqlExecutor.execute();
        } catch (SQLException e) {
            throw new MojoExecutionException("DDLの実行に失敗しました:", e);
        }
    }


}
