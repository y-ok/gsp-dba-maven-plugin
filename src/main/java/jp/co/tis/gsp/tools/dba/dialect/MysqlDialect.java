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

package jp.co.tis.gsp.tools.dba.dialect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.seasar.extension.jdbc.gen.dialect.GenDialectRegistry;
import org.seasar.extension.jdbc.gen.meta.DbTableMeta;
import org.seasar.extension.jdbc.util.ConnectionUtil;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.DriverManagerUtil;
import org.seasar.framework.util.FileOutputStreamUtil;
import org.seasar.framework.util.ResultSetUtil;
import org.seasar.framework.util.StatementUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.Maps;

import jp.co.tis.gsp.tools.db.AbstractDbObjectParser;
import jp.co.tis.gsp.tools.db.AlternativeGenerator;
import jp.co.tis.gsp.tools.db.TypeMapper;
import jp.co.tis.gsp.tools.dba.util.ProcessUtil;

public class MysqlDialect extends Dialect {	
	
    public MysqlDialect() {
        GenDialectRegistry.deregister(
                org.seasar.extension.jdbc.dialect.MysqlDialect.class
        );
        GenDialectRegistry.register(
                org.seasar.extension.jdbc.dialect.MysqlDialect.class,
                new ExtendedMysqlGenDialect()
        );
    }

	private Map<Integer, String> typeToNameMap = Maps
		.map(Types.BIGINT, "BIGINT")
		.$(Types.BLOB, "BLOB")
		.$(Types.BOOLEAN, "BOOLEAN")
		.$(Types.CHAR, "CHAR")
		.$(Types.CLOB, "TEXT")
		.$(Types.DATE, "DATE")
		.$(Types.DECIMAL, "NUMBER")
		.$(Types.DOUBLE, "DOUBLE")
		.$(Types.FLOAT, "FLOAT")
		.$(Types.INTEGER, "INT")
		.$(Types.TIMESTAMP, "TIMESTAMP")
		.$(Types.VARCHAR, "VARCHAR")
		.$();



	@Override
	public void exportSchema(String user, String password, String schema, File dumpFile) throws MojoExecutionException {
		BufferedInputStream in = null;
		FileOutputStream out = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(
					"mysqldump",
					schema,
					"-u", user,
					"--password="+password);
			Process process = pb.start();
			in = new BufferedInputStream(process.getInputStream());

			out = FileOutputStreamUtil.create(dumpFile);
			byte[] buf = new byte[4096];
			while(true) {
				int res = in.read(buf);
				if (res <= 0) break;
				out.write(buf, 0, res);
			}

		} catch (IOException e) {
			throw new MojoExecutionException("mysqldump", e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 指定スキーマ内のテーブル、ビュー、シーケンスを全て削除します。
	 * 
	 * MySQLでは予めスキーマ（＝ＤＢ）が存在している前提のため、スキーマ存在確認・生成処理は行いません。
	 * 
	 * @param user
	 * @param password
	 * @param adminUser
	 * @param adminPassword
	 * @param schema
	 * @throws MojoExecutionException
	 */
	public void dropAll(String user, String password,
			String adminUser, String adminPassword,
			String schema) throws MojoExecutionException {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DriverManager.getConnection(url, adminUser, adminPassword);
			stmt = conn.createStatement();

			// スキーマ内のテーブル、ビュー削除
			String nmzschema = normalizeSchemaName(schema);
			String dropListSql = "SELECT TABLE_NAME, CONSTRAINT_NAME FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_SCHEMA='" + nmzschema + "'";
			dropObjectsInSchema(conn, dropListSql, nmzschema, OBJECT_TYPE.FK);
			
			dropListSql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA='" + nmzschema + "'";
			dropObjectsInSchema(conn, dropListSql, nmzschema, OBJECT_TYPE.VIEW);
			
			dropListSql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='" + nmzschema + "'";
	        dropObjectsInSchema(conn, dropListSql, nmzschema, OBJECT_TYPE.TABLE);

		} catch (SQLException e) {
			throw new MojoExecutionException("データ削除中にエラー", e);
		} finally {
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
	}
	
	@Override
    protected void dropObjectsInSchema(Connection conn, String dropListSql, String schema, OBJECT_TYPE objType) throws SQLException {
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	try {
    	  stmt = conn.createStatement();
    	  rs = stmt.executeQuery(dropListSql);
    	  String dropSql = "";
    	  
    	  while (rs.next()) {
      	      switch (objType) {
		        case FK:
  		        	dropSql = "ALTER TABLE " + schema + "." + rs.getString(1) + " DROP FOREIGN KEY " + rs.getString(2);
          		  break;
  		        case TABLE:
  		        	dropSql = "DROP TABLE "  + schema + "." + rs.getString(1);
    		      break;
  		        case VIEW:
  		        	dropSql = "DROP VIEW "  + schema + "." + rs.getString(1);
  			      break;
         		default: // シーケンスは未サポート
  			      break;
  		      }
      	    
      	    stmt = conn.createStatement();
      	    System.err.println(dropSql);
      	    stmt.execute(dropSql);
    	  }
        } finally {
        	rs.close();
            StatementUtil.close(stmt);
        }
    }

	@Override
	public void createUser(String user, String password, String adminUser, String adminPassword) throws MojoExecutionException{
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(url, adminUser, adminPassword);
			stmt = conn.createStatement();
			if(existsUser(conn, user)) {
				return;
			}
			stmt.execute("CREATE USER '"+ user + "' IDENTIFIED BY '"+ password +"'");
		} catch (SQLException e) {
			throw new MojoExecutionException("CREATE USER実行中にエラー", e);
		} finally {
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
	}
	
	@Override
	public void grantAllToUser(String schema, String user, String password, String admin, String adminPassword) throws MojoExecutionException {
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, admin, adminPassword);
			
			String nmzschema = normalizeSchemaName(schema);
			
			String grantListSql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA='" + nmzschema + "'";
			grantSchemaObjToUser(conn, grantListSql, nmzschema, user, OBJECT_TYPE.VIEW);
			
			grantListSql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='" + nmzschema + "'";
			grantSchemaObjToUser(conn, grantListSql, nmzschema, user, OBJECT_TYPE.TABLE);
			
		} catch (SQLException e) {
			throw new MojoExecutionException("スキーマ権限付与実行エラー", e);
		} finally {
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		 
 	}

	private boolean existsUser(Connection conn, String user) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(
					"SELECT count(*) AS num FROM mysql.user WHERE User=?");
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return (rs.getInt("num") > 0);
		} finally {
			StatementUtil.close(stmt);
		}
	}
	
	@Override
	public void importSchema(String user, String password, String schema,
			File dumpFile) throws MojoExecutionException {
		try {
			ProcessUtil.execWithInput(dumpFile,
					"mysql",
					"-u", user,
					"--password="+ password,
					schema
					);
		} catch (IOException e) {
			throw new MojoExecutionException("スキーマインポート実行中にエラー", e);
		}

	}

	@Override
	public TypeMapper getTypeMapper() {
		return new TypeMapper(typeToNameMap);
	}

	@Override
	public List<AlternativeGenerator> getAlternativeGenerators() {
		List<AlternativeGenerator> generators = CollectionsUtil.newArrayList(10);
		generators.add(new AlternativeGenerator() {
			@Override public String getName() { return "sequence"; }
			@Override public void generate(List<Map<String, String>> objectDef, AbstractDbObjectParser context) {
				if (CollectionUtils.isEmpty(objectDef))
					return;

				try {
					FileUtils.write(new File(context.getOutputDirectory(), "01_CREATE_SEQUENCES.sql"),
							"create TABLE sequences("
							+ "name varchar(255) primary key,"
							+ "seq BIGINT default 1);");
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
		});
		return generators;
	}

    /**
     * ビュー定義を検索するSQLを返却する。
     * @return ビュー定義を検索するSQL文
     */
	@Override
	public String getViewDefinitionSql() {
		return "SELECT view_definition FROM information_schema.views WHERE table_name=? AND table_schema=?";
	}
	

	/**
	 * ViewのDDL定義を取得する（MySQL用）.
	 * 
	 * <p>
	 *   MySQLのJDBC実装ではDbTableMetaよりスキーマ名が取得出来ないためカタログ名を用いる。
	 * </p>
	 * 
	 * @param conn {@inheritDoc} 
	 * @param viewName {@inheritDoc}
	 * @param tableMeta {@inheritDoc}
     * @return {@inheritDoc}
     * @throws SQLException {@inheritDoc}
	 */
    public String getViewDefinition(Connection conn, String viewName, DbTableMeta tableMeta) throws SQLException {
        String sql = getViewDefinitionSql();
        if (sql == null) {
            return null;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idx = 1;
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(idx++, viewName);
            stmt.setString(idx++, tableMeta.getCatalogName());	
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                return rs.getString(1);
            }
        } finally {
            ResultSetUtil.close(rs);
            StatementUtil.close(stmt);
        }
        return null;
    }
}
