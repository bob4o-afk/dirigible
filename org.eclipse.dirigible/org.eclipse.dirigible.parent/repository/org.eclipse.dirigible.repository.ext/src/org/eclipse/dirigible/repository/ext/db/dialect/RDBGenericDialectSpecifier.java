package org.eclipse.dirigible.repository.ext.db.dialect;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.dirigible.repository.ext.conf.IConfigurationStore;

@SuppressWarnings("javadoc")
public class RDBGenericDialectSpecifier implements IDialectSpecifier {

	private static final String DOT = "."; //$NON-NLS-1$
	private static final String QUOTES = "\""; //$NON-NLS-1$
	private static final String EMPTY = "";

	public static IDialectSpecifier getDialect(IConfigurationStore cfgStore) {
		return null;
	}

	@Override
	public String specify(String sql) {
		return sql;
	}

	@Override
	public String getSpecificType(String commonType) {
		return commonType;
	}

	@Override
	public String createLimitAndOffset(int limit, int offset) {
		return null;
	}

	@Override
	public String createTopAndStart(int limit, int offset) {
		return null;
	}

	@Override
	public boolean isSchemaFilterSupported() {
		return false;
	}

	@Override
	public String getSchemaFilterScript() {
		return null;
	}

	@Override
	public String getAlterAddOpen() {
		return null;
	}

	@Override
	public String getAlterAddOpenEach() {
		return null;
	}

	@Override
	public String getAlterAddClose() {
		return null;
	}

	@Override
	public String getAlterAddCloseEach() {
		return null;
	}

	@Override
	public InputStream getBinaryStream(ResultSet resultSet, String columnName) throws SQLException {
		return null;
	}

	@Override
	public boolean isCatalogForSchema() {
		return false;
	}

	@Override
	public boolean isSchemaless() {
		return false;
	}

	@Override
	public String getContentQueryScript(String catalogName, String schemaName, String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException();
		}
		String q = "SELECT * FROM ";
		String tableFqn = (schemaName != null ? QUOTES + schemaName + QUOTES + DOT : EMPTY) + QUOTES + tableName + QUOTES;
		return q + tableFqn;
	}

}
