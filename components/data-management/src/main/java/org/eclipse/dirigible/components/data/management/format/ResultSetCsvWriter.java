/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.components.data.management.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ResultSet CSV Writer.
 */
public class ResultSetCsvWriter extends AbstractResultSetWriter<String> {
	
	/**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResultSetCsvWriter.class);

	/** The limited. */
	private boolean limited = true;
	
	/** The stringify. */
	private boolean stringify = true;

	/**
	 * Checks if is limited.
	 *
	 * @return true, if is limited
	 */
	public boolean isLimited() {
		return limited;
	}

	/**
	 * Sets the limited.
	 *
	 * @param limited
	 *            the new limited
	 */
	public void setLimited(boolean limited) {
		this.limited = limited;
	}
	
	/**
	 * Checks if is stringified.
	 *
	 * @return true, if is stringified
	 */
	public boolean isStringified() {
		return stringify;
	}

	/**
	 * Sets the stringified.
	 *
	 * @param stringify
	 *            the new stringified
	 */
	public void setStringified(boolean stringify) {
		this.stringify = stringify;
	}

	/**
	 * Write.
	 *
	 * @param resultSet the result set
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.databases.processor.format.ResultSetWriter#write(java.sql.ResultSet)
	 */
	@Override
	public String write(ResultSet resultSet) throws SQLException {
		
		StringWriter sw = new StringWriter();

		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

		List<String> records = new ArrayList<String>();
		int count = 0;
		List<String> names = new ArrayList<>();
		if (resultSet.next()) {
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
				String name = resultSetMetaData.getColumnName(i);
				names.add(name);
			}
		} else {
			return "";
		}
		
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
		        .setHeader(names.stream().toArray(String[] ::new))
		        .build();
		try {
			try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
	            count = 0;
	    		do {
	    			List<Object> values = new ArrayList<>();
	    			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
	    				Object value = null;
	    				int dbt = resultSetMetaData.getColumnType(i);
	    				if (dbt == Types.BLOB
	    						|| dbt == Types.BINARY
	    						|| dbt == Types.LONGVARBINARY) {
	    					InputStream is = resultSet.getBinaryStream(i);
	    					if (is == null
		    						&& stringify) {
		    					value = "[NULL]";
		    				} else if (is != null) {
		    					byte[] ba = IOUtils.toByteArray(is);
		    					if (stringify) {
		    						value = "[BLOB]";
		    					} else {
		    						value = Base64.getEncoder().encodeToString(ba);
		    					}
		    				}
	    				} else if (dbt == Types.CLOB
	    						|| dbt == Types.LONGVARCHAR) {
	    					Clob clob = resultSet.getClob(i);
	    					if (clob == null
		    						&& stringify) {
		    					value = "[NULL]";
		    				} else if (clob != null) {
		    					byte[] ba = IOUtils.toByteArray(clob.getAsciiStream());
		    					if (stringify) {
		    						value = "[CLOB]";
		    					} else {
		    						value = Base64.getEncoder().encodeToString(ba);
		    					}
		    				}
	    				} else {
		    				value = resultSet.getObject(i);
		    				if (value == null
		    						&& stringify) {
		    					value = "[NULL]";
		    				}
		    				if (value != null 
		    						&& !ClassUtils.isPrimitiveOrWrapper(value.getClass()) 
		    						&& value.getClass() != String.class
		    						&& !java.util.Date.class.isAssignableFrom(value.getClass())) {
		    					if (stringify) {
		    						value = "[BINARY]";
		    					}
		    				}
	    				}
	    				values.add(value);
	    			}
	    			try {
	                    printer.printRecord(values);
	                } catch (Exception e) {
	                	logger.error(e.getMessage());
	                }
	
	    			if (this.isLimited() && (++count > getLimit())) {
	    				break;
	    			}
	    		} while (resultSet.next());
	            
	            
	        }
		} catch(Exception e) {
			return e.getMessage();
		}
		
		return sw.toString().trim();
	}

}
