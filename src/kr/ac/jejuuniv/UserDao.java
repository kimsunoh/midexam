package kr.ac.jejuuniv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


public class UserDao {

	private JdbcContext jdbcContext;

	public UserDao(){
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		return jdbcContext.jdbcContextWithStatementStrategyForQuery(new StatementStrategy() {
			
			@Override
			public PreparedStatement makeStatement(Connection connection)
					throws SQLException {
				PreparedStatement preparedStatement;
				String sql = "select id, name, password from userinfo where id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);
				return preparedStatement;
			}
		});
	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
		final String query = "insert into userinfo(id,name,password) value(?,?,?)";
		final String[] params = new String[] {user.getId(), user.getName(), user.getPassword()};
		
		jdbcContext.update(query,params);
	}

	public void delete(final String id) throws SQLException {
		final String query = "delete from userinfo where id = ?";
		final String[] params = new String[] {id};

		jdbcContext.update(query, params);
	}
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
}
