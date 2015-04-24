package kr.ac.jejuuniv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetUserStatementStrategy implements StatementStrategy {

	@Override
	public PreparedStatement makeStatement(Object object, Connection connection)
			throws SQLException {
		PreparedStatement preparedStatement;
		String sql = "select id, name, password from userinfo where id = ?";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, (String)object);
		return preparedStatement;
	}

}
