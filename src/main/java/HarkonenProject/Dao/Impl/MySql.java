package HarkonenProject.Dao.Impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;




	public class MySql {
		private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		private StringBuilder host = new StringBuilder();
		private StringBuilder port = new StringBuilder();
		private StringBuilder dbURL = new StringBuilder();
		private StringBuilder user = new StringBuilder();
		private StringBuilder password = new StringBuilder();
		private Connection conn = null;
		private Statement stmt = null;
		private ResultSet rs = null;
		
		public MySql(String user, String password, String host, String port){
			this.user.append(user);
			this.password.append(password);
			this.host.append(host);
			this.port.append(port);
			this.dbURL.append("jdbc:mysql://");
			this.dbURL.append(host);
			this.dbURL.append(":");
			this.dbURL.append(port);
			this.dbURL.append("/?useSSL=false");
			this.establishConnection();
		}
		
		private void establishConnection(){
			if (this.user.length() > 0 && this.password.length() > 0 && this.dbURL.length() > 0){
				try{
					Class.forName(JDBC_DRIVER);
					
					conn = DriverManager.getConnection(this.dbURL.toString(), this.user.toString(), this.password.toString());
					stmt = conn.createStatement();
				}catch(SQLException se){
					se.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		private void executeSqlUpdate(String sql){
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private ResultSet executeSqlQuary(String sql){
			try {
				this.rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return (this.rs);
		}
		
		public void addDataToTable(String database, String table, String[] columns, String[] data) {
			StringBuilder sql = new StringBuilder("INSERT INTO ");
			
			sql.append(database);
			sql.append(".");
			sql.append(table);
			sql.append("(");
			for (Integer i = 0; i < columns.length - 1; i++) {
				sql.append(columns[i]);
				sql.append(", ");
			}
			sql.append(columns[columns.length - 1]);
			sql.append(") VALUES (");
			for (Integer i = 0; i < data.length - 1; i++) {
				sql.append(data[i]);
				sql.append(",");
			}
			sql.append(data[data.length - 1]);
			sql.append(")");
			this.executeSqlUpdate(sql.toString());
		}
		
		public void updateRow(String database, String table, String[] columns, String[] data, String condition){
			if (columns != null && columns.length > 0 && data != null && columns.length == data.length){
				StringBuilder sql = new StringBuilder ("UPDATE ");
				
				sql.append(database);
				sql.append(".");
				sql.append(table);
				sql.append(" SET ");
				for (Integer i = 0; i < columns.length - 1; i++){
					sql.append(columns[i]);
					sql.append("=");
					sql.append(data[i]);
					sql.append(", ");
				}
				sql.append(columns[columns.length - 1]);
				sql.append("=");
				sql.append(data[columns.length - 1]);
				if (condition != null && condition.length() > 0){
					sql.append(" WHERE ");
					sql.append(condition);
				}
				this.executeSqlUpdate(sql.toString());
			}
		}
		
		private StringBuilder selectSyntax(String database, String table, String[] columns, String whereCondition, String[] groupBy, String havingCondition, String[] orderBy, String limit){
			StringBuilder sql = null;
			
			if (columns.length > 0){
				sql = new StringBuilder("SELECT ");
				sql.append(columns[0]);
				if (columns.length > 1){
					sql.append(", ");
					for (Integer i = 1; i < columns.length - 1; i++){
						sql.append(columns[i]);
						sql.append(", ");
					}
					sql.append(columns[columns.length - 1]);
				}
				sql.append(" FROM ");
				sql.append(database);
				sql.append(".");
				sql.append(table);
				if (whereCondition != null){
					sql.append(" WHERE ");
					sql.append(whereCondition);
				}
				if (groupBy != null && groupBy.length > 0){
					sql.append(" GROUP BY ");
					for (Integer i = 0; i < groupBy.length - 1; i++){
						sql.append(groupBy[i]);
						sql.append(", ");
					}
					sql.append(groupBy[groupBy.length - 1]);
				}
				if (havingCondition != null && havingCondition.length() > 0){
					sql.append(" HAVING ");
					sql.append(havingCondition);
				}
				if (orderBy != null && orderBy.length > 0){
					sql.append(" ORDER BY ");
					for (Integer i = 0; i < orderBy.length - 1; i++){
						sql.append(orderBy[i]);
						sql.append(", ");
					}
					sql.append(orderBy[orderBy.length - 1]);
				}
				if (limit != null && limit.length() > 0){
					sql.append(" LIMIT ");
					sql.append(limit);
				}
			}
			
			return (sql);
		}
		
		public ResultSet getDataFromTable(String database, String table, String[] columns){
			return (this.getDataFromTable(database, table, columns, null, null, null, null, null));
		}
		
		public ResultSet getDataFromTable(String database, String table, String[] columns, String whereCondition, String[] groupBy, String havingCondition, String[] orderBy, String limit){
			ResultSet result = null;
			StringBuilder sql = this.selectSyntax(database, table, columns, whereCondition, groupBy, havingCondition, orderBy, limit);
			
			if (sql != null && sql.length() > 0){
				result = this.executeSqlQuary(sql.toString());
				this.rs = result;
			}
			
			return (result);
		}
		
	/*	public ResultSet nestedSelectExist(String[] database, String[] table, String[][] columns,
										  String[] whereCondition, String[][] groupBy, String[] havingCondition,
										  String[][] orderBy, String[] limit){
			ResultSet result = null;
			
			if (database != null && table != null && columns != null && (whereCondition != null ||
				(groupBy != null &&	havingCondition != null)) && database.length > 0 && table.length > 0 &&
				columns.length > 0 && (whereCondition.length > 0 || (groupBy.length > 0 &&
				havingCondition.length > 0))){
				
				result = this.executeSqlQuary(sql.toString());
			}
			
			return (result);
		}
	*/	
		public void deleteRow(String database, String table, String condition) {
			StringBuilder sql = new StringBuilder("DELETE FROM ");
			
			sql.append(database);
			sql.append(".");
			sql.append(table);
			sql.append(" WHERE ");
			sql.append(condition);
			this.executeSqlUpdate(sql.toString());
		}
		
		public void deleteAllRows(String database, String table){
	StringBuilder sql = new StringBuilder("DELETE FROM ");
			
			sql.append(database);
			sql.append(".");
			sql.append(table);
			this.executeSqlUpdate(sql.toString());
		}
		
		public void deleteTable(String database, String table){
			StringBuilder sql = new StringBuilder("DROP TABLE ");
			
			sql.append(database);
			sql.append(".");
			sql.append(table);
			this.executeSqlUpdate(sql.toString());
		}
		
		public void deleteDatabase(String database){
			StringBuilder sql = new StringBuilder("DROP DATABASE ");
			
			sql.append(database);
			this.executeSqlUpdate(sql.toString());
		}
		
		public Boolean isDatabaseExist(String database){
			StringBuilder sql = new StringBuilder("SHOW DATABASES LIKE \'");
			Boolean result = false;
			
			sql.append(database);
			sql.append("\'");
			this.rs = this.executeSqlQuary(sql.toString());
			try {
				result = this.rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return (result);
		}
		
		public Boolean isTableExist(String database, String table){
			Boolean result = false;
			
			if (this.isDatabaseExist(database)){
				StringBuilder sql = new StringBuilder("SHOW TABLES FROM ");
				sql.append(database);
				sql.append(" LIKE \'");
				sql.append(table);
				sql.append("\'");
				ResultSet resultSet = this.executeSqlQuary(sql.toString());
				try {
					result = resultSet.next();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return (result);
		}
		
		public Boolean isColumnsInTable(String database, String table, String[] columnsNames){
			Boolean result = false;
			
			if (this.isTableExist(database, table)){
				result = true;
				ResultSet resultSet = this.showAllColumnsFromTable(database, table);
				Integer colIndex = 0;
				
				try {
					while (colIndex < columnsNames.length && resultSet.next()){
						if (!resultSet.getString("Field").equals(columnsNames[colIndex])){
							result = false;
						}
						colIndex++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (colIndex != columnsNames.length || resultSet.next()){
						result = false;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			return (result);
		}
		
		public void createNewDatabase(String database){
			if (!this.isDatabaseExist(database)){
				StringBuilder sql = new StringBuilder("CREATE DATABASE ");
				sql.append(database);
				this.executeSqlUpdate(sql.toString());
			}
		}
		
		public void createNewTable(String database, String table, String[] columns){
			this.createNewTable(database, table, columns, null);
		}
		
		public void createNewTable(String database, String table, String[] columns, String[] primaryKey) {
			if (!this.isDatabaseExist(database)) {
				this.createNewDatabase(database);
			}
			if (!this.isTableExist(database, table)) {
				StringBuilder sql = new StringBuilder("CREATE TABLE ");
				sql.append(database);
				sql.append(".");
				sql.append(table);
				sql.append("(");
				for (Integer i = 0; i < columns.length - 1; i++) {
					sql.append(columns[i]);
					if (columns.length > 1) {
						sql.append(", ");
					}
				}
				sql.append(columns[columns.length - 1]);
				if (primaryKey != null && primaryKey.length > 0) {
					sql.append(", PRIMARY KEY (");
					for (Integer i = 0; i < primaryKey.length - 1; i++) {
						sql.append(primaryKey[i]);
						if (primaryKey.length > 1) {
							sql.append(", ");
						}
					}
					sql.append(primaryKey[primaryKey.length - 1]);
					sql.append(")");
				}
				sql.append(")");
				this.executeSqlUpdate(sql.toString());
			}
		}
		
		public ResultSet innerJoin(String[] tablesInDatabases, String[] columnsInTablesInDatabese, String[] joinConditions, String[] groupBy, String havingCondition, String[] orderBy, String limit){
			return (this.joinTables("INNER", tablesInDatabases, columnsInTablesInDatabese, joinConditions, groupBy, havingCondition, orderBy, limit));
		}
		
		public ResultSet leftJoin(String[] tablesInDatabases, String[] columnsInTablesInDatabese, String[] joinConditions, String[] groupBy, String havingCondition, String[] orderBy, String limit){
			return (this.joinTables("LEFT", tablesInDatabases, columnsInTablesInDatabese, joinConditions, groupBy, havingCondition, orderBy, limit));
		}
		
		public ResultSet rightJoin(String[] tablesInDatabases, String[] columnsInTablesInDatabese, String[] joinConditions, String[] groupBy, String havingCondition, String[] orderBy, String limit){
			return (this.joinTables("RIGHT", tablesInDatabases, columnsInTablesInDatabese, joinConditions, groupBy, havingCondition, orderBy, limit));
		}
		
		public ResultSet fullJoin(String[] tablesInDatabases){
			String[] columnsInTablesInDatabese = {"*"};
			String[] joinConditions = new String[tablesInDatabases.length-1];
			
			for (Integer i = 0; i < joinConditions.length; i++){
				joinConditions[i] = null;
			}
			
			return (this.joinTables("FULL", tablesInDatabases, columnsInTablesInDatabese, joinConditions, null, null, null, null));
		}
		
		private ResultSet joinTables(String joinType, String[] tablesInDatabases, String[] columnsInTablesInDatabese, String[] joinConditions, String[] groupBy, String havingCondition, String[] orderBy, String limit){
			if (tablesInDatabases.length >= 2 && columnsInTablesInDatabese.length >= 1 && tablesInDatabases.length == joinConditions.length + 1){
				StringBuilder sql = new StringBuilder("SELECT ");
				
				for (Integer i = 0; i < columnsInTablesInDatabese.length - 1; i++){
					sql.append(columnsInTablesInDatabese[i]);
					sql.append(", ");
				}
				sql.append(columnsInTablesInDatabese[columnsInTablesInDatabese.length - 1]);
				sql.append(" FROM ");
				sql.append(tablesInDatabases[0]);
				for (Integer i = 1; i < tablesInDatabases.length - 1; i++){
					sql.append(" ");
					sql.append(joinType);
					sql.append(" JOIN ");
					sql.append(tablesInDatabases[i]);
					if (joinConditions[i-1] != null){
						sql.append(" ON ");
						sql.append(joinConditions[i-1]);
					}
				}
				sql.append(" ");
				sql.append(joinType);
				sql.append(" JOIN ");
				sql.append(tablesInDatabases[tablesInDatabases.length - 1]);
				if (joinConditions[joinConditions.length - 1] != null){
					sql.append(" ON ");
					sql.append(joinConditions[joinConditions.length - 1]);
				}
				if (groupBy != null && groupBy.length > 0){
					sql.append(" GROUP BY ");
					for (Integer i = 0; i < groupBy.length - 1; i++){
						sql.append(groupBy[i]);
						sql.append(", ");
					}
					sql.append(groupBy[groupBy.length - 1]);
				}
				if (havingCondition != null && havingCondition.length() > 0){
					sql.append(" HAVING ");
					sql.append(havingCondition);
				}
				if (orderBy != null && orderBy.length > 0){
					sql.append(" ORDER BY ");
					for (Integer i = 0; i < orderBy.length - 1; i++){
						sql.append(orderBy[i]);
						sql.append(", ");
					}
					sql.append(orderBy[orderBy.length - 1]);
				}
				if (limit != null && limit.length() > 0){
					sql.append(" LIMIT ");
					sql.append(limit);
				}
				this.rs = this.executeSqlQuary(sql.toString());
			}
			return (this.rs);
		}
		
		public ResultSet unionAll(String[] tablesInDatabases, String[][] columns){
			ResultSet result = null;
			
			if (tablesInDatabases != null && tablesInDatabases.length > 1 &&
				columns != null && columns.length > 1 && tablesInDatabases.length == columns.length){
				StringBuilder sql = new StringBuilder();
				
				for (Integer i = 0; i < tablesInDatabases.length; i++){
					sql.append("SELECT ");
					if (columns[i] != null && columns[i].length > 0){
						for (Integer j = 0; j < columns[i].length; j++){
							sql.append(columns[i][j]);
							if (columns[i].length - j > 1){
								sql.append(", ");
							}
						}
					}
					else{
						return (result);
					}
					sql.append(" FROM ");
					sql.append(tablesInDatabases[i]);
					if (i < tablesInDatabases.length - 1){
						sql.append(" UNION ALL ");
					}
				}
				result = this.executeSqlQuary(sql.toString());
			}
			
			return (result);
		}
		
		public ResultSet union(String[] tablesInDatabases, String[][] columns){
			ResultSet result = null;
			
			if (tablesInDatabases != null && tablesInDatabases.length > 1 &&
				columns != null && columns.length > 1 && tablesInDatabases.length == columns.length){
				StringBuilder sql = new StringBuilder();
				
				for (Integer i = 0; i < tablesInDatabases.length; i++){
					sql.append("SELECT ");
					if (columns[i] != null && columns[i].length > 0){
						for (Integer j = 0; j < columns[i].length; j++){
							sql.append(columns[i][j]);
							if (columns[i].length - j > 1){
								sql.append(", ");
							}
						}
					}
					else{
						return (result);
					}
					sql.append(" FROM ");
					sql.append(tablesInDatabases[i]);
					if (i < tablesInDatabases.length - 1){
						sql.append(" UNION ");
					}
				}
				result = this.executeSqlQuary(sql.toString());
			}
			
			return (result);
		}
		
		private ResultSet show(String show){
			StringBuilder sql = new StringBuilder("SHOW ");
			
			sql.append(show);
			this.rs = this.executeSqlQuary(sql.toString());
			return (this.rs);
		}
		
		public ResultSet showAllDatabases(){
			return (this.show("DATABASES"));
		}
		
		public ResultSet showAllTablesFromDatabase(String database){
			return (this.show("TABLES FROM " + database));
		}
		
		public ResultSet showAllColumnsFromTable(String database, String table){
			return (this.show("COLUMNS FROM " + database + "." + table));
		}
		
		public Boolean isResultSetsEquals(ResultSet a){
			return (this.isResultSetsEquals(a, this.rs));
		}
		
		public Boolean isResultSetsEquals(ResultSet a, ResultSet b){
			Boolean result = true;
			
			try {
				if (a.getMetaData().getColumnCount() != b.getMetaData().getColumnCount()){
					result = false;
				}
				else{
					for (Integer i = 0; i < a.getMetaData().getColumnCount(); i++){
						if (a.getMetaData().getColumnLabel(i) != b.getMetaData().getColumnLabel(i)){
							result = false;
							return (result);
						}
					}
					while (a.next() && b.next()){
						for (Integer i = 0; i < a.getMetaData().getColumnCount(); i++){
							if (!a.getString(i).equals(b.getString(i))){
								result = false;
								return (result);
							}
						}
					}
					if (a.next() || b.next()){
						result = false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return (result);
		}
		
		public static String toString(ResultSet rs){
			StringBuilder result = new StringBuilder();
			try {
				ResultSetMetaData md = rs.getMetaData();
				for (Integer i = 1; i <= md.getColumnCount(); i++){
					result.append(md.getColumnName(i));
					result.append("\t");
				}
				result.append("\n");
				while (rs.next()){
					for (Integer i = 1; i <= md.getColumnCount(); i++){
						result.append(rs.getString(i));
						result.append("\t");
					}
					result.append("\n");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return (result.toString());
		}
		
		public void printResultSet(){
			MySql.printResultSet(this.rs);
		}
		
		public static void printResultSet(ResultSet rs){
			System.out.println(MySql.toString(rs));
		}
		
		public void closeAllConnections(){
			try {
				if (this.rs != null){
					this.rs.close();
				}
				if(this.stmt != null){
					this.stmt.close();
				}
				if(this.conn != null){
					this.conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

