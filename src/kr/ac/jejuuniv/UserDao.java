package kr.ac.jejuuniv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class UserDao {

	private JdbcTemplate jdbcTemplate;

	public UserDao(){
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		String sql = "select * from userinfo where id = ?";
		Object[] args = new String[] {id};
		User queryForObject = null;
		try {
			queryForObject = getJdbcTemplate().queryForObject(sql, args, new RowMapper<User>(){

				@Override
				public User mapRow(ResultSet rs, int rownum) throws SQLException {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				}
				
			});
		} catch (EmptyResultDataAccessException e) {

		}
		return queryForObject;
	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
		final String query = "insert into userinfo(id,name,password) value(?,?,?)";
		final String[] params = new String[] {user.getId(), user.getName(), user.getPassword()};
		
		jdbcTemplate.update(query,params);
	}

	public void delete(final String id) throws SQLException {
		final String query = "delete from userinfo where id = ?";
		final String[] params = new String[] {id};

		jdbcTemplate.update(query, params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
