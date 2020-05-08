package jp.co.tis.gsp.test.util.dialect;

import jp.co.tis.gsp.tools.db.TypeMapper;
import jp.co.tis.gsp.tools.dba.dialect.Dialect;
import jp.co.tis.gsp.tools.dba.dialect.param.ExportParams;
import jp.co.tis.gsp.tools.dba.dialect.param.ImportParams;
import org.apache.maven.plugin.MojoExecutionException;



public class OptionalDialectsTestDialect extends Dialect {

 @Override
	public void dropAll(String user, String password, String adminUser, String adminPassword, String schema)
			throws MojoExecutionException {
		throw new MojoExecutionException("OptionalDialectsTestDialect");
	}

	@Override
	public void exportSchema(ExportParams expParams) throws MojoExecutionException {

	}

	@Override
	public void importSchema(ImportParams impParams) throws MojoExecutionException {
	}

	@Override
	public void createUser(String user, String password, String adminUser, String adminPassword)
			throws MojoExecutionException {
	}

	@Override
	public TypeMapper getTypeMapper() {
		return null;
	}

}