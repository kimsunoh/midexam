package kr.ac.jejuuniv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {


	private DataSource  dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void jdbcContextWithStatementStrategyForUpdate(StatementStrategy statementStrategy)
			throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = statementStrategy.makeStatement(connection);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if(connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}


	public User jdbcContextWithStatementStrategyForQuery(
			StatementStrategy statementStrategy) {
		ResultSet resultSet = null;
		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = statementStrategy.makeStatement(connection);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				user = new User();
				user.setId(resultSet.getString("id"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(resultSet != null)
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if(preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if(connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return user;
	}

	public void update(final String query, final String[] params) throws SQLException {
		jdbcContextWithStatementStrategyForUpdate(new StatementStrategy() {
			@Override
			public PreparedStatement makeStatement(Connection connection)
					throws SQLException {
				PreparedStatement preparedStatement;
				preparedStatement = connection.prepareStatement(query);
				for(int i = 1; i<=params.length ; i++)
					preparedStatement.setString(i,params[i-1]);
				return preparedStatement;
			}
		});
	}
}
