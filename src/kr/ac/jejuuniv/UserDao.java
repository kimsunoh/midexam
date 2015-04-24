package kr.ac.jejuuniv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


public class UserDao {

	private DataSource  dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public UserDao(){
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = dataSource.getConnection();
			StatementStrategy statementStrategy = new GetUserStatementStrategy();
			preparedStatement = statementStrategy.makeStatement(id, connection);

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

	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			StatementStrategy statementStrategy = new AddUserStatementStrategy();
			preparedStatement = statementStrategy.makeStatement(user, connection);

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

	public void delete(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			StatementStrategy statementStrategy = new DeleteUserStatementStrategy();
			preparedStatement = statementStrategy.makeStatement(id, connection);
			
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

		
}
