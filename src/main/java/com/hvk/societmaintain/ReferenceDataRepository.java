package com.hvk.societmaintain;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hvk.societmaintain.model.BillDetail;
import com.hvk.societmaintain.model.RefDataResponse;
import com.hvk.societmaintain.model.RegisterBiller;
import com.hvk.societmaintain.model.UserDetail;
import com.hvk.societmaintain.model.UserSummary;

@Repository
@Transactional
public class ReferenceDataRepository {

	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Inject
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	public String getDbInfo() {
		DataSource dataSource = jdbcTemplate.getDataSource();
		if (dataSource instanceof BasicDataSource) {
			return ((BasicDataSource) dataSource).getUrl();
		} else if (dataSource instanceof SimpleDriverDataSource) {
			return ((SimpleDriverDataSource) dataSource).getUrl();
		}
		return dataSource.toString();
	}

	public List<State> findAll() {
		return this.jdbcTemplate.query("select * from current_states", new RowMapper<State>() {
			public State mapRow(ResultSet rs, int rowNum) throws SQLException {
				State s = new State();
				s.setId(rs.getLong("id"));
				s.setStateCode(rs.getString("state_code"));
				s.setName(rs.getString("name"));
				return s;
			}
		});
	}

	public int registerBiller(RegisterBiller biller, UserSummary userSummary) {

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		int sid1, sid2 = 0;
		try {
			String SQL1 = "insert into usage_uitlity (customer_id, utility_type_code,apartment_id) values (?, ?,?)";
			sid1 = jdbcTemplate.update(SQL1, Integer.valueOf(userSummary.getUserId()), Integer.valueOf(biller.getUtilitytype()),
					Integer.valueOf(biller.getApartment()));

			// Get the latest student id to be used in Marks table
			String SQL2 = "insert into contact_preferences (preferred_time_start, preferred_time_end,email_id,Mobile_Number,email_preference,mobile_preference," +
					"no_of_days_before_due_date) values (?, ?,?,?,?,?,?)";
			sid2 = jdbcTemplate.update(SQL2, "12:10:00", "20:10:00", "111", "a@a.mal", biller.getEmailAlert(),
					biller.getEmailAlert(), 3);

			transactionManager.commit(status);
		} catch (DataAccessException e) {
			System.out.println("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}

		int updateStatus = 0;
		if (sid1 == 1 && sid2 == 1) {
			updateStatus = 1;
		} else {
			updateStatus = 0;
		}
		// int updateStatus =
		// jdbcTemplate.update("insert into usage (customer_id, utility_type_code,apartment_id) values (?, ?,?)",
		// new Object[] {Integer.valueOf(userSummary.getUserId()),
		// Integer.valueOf(biller.getUtilitytype()),Integer.valueOf(biller.getApartment())});

		return updateStatus;
	}

	public int updateBill(BillDetail biller, UserSummary userSummary) {

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		int sid1, sid2 = 0;
		try {
			String SQL1 = "UPDATE financial_transactions SET account_id = ?, transaction_type_code = ?";
			// CASH or ECS
			if (biller.getTransactionType().equals(1) || biller.getTransactionType().equals(3)) {
				SQL1 += ", amount = ? WHERE bill_id= ?";
				sid1 = jdbcTemplate.update(SQL1, biller.getAccountId(), biller.getTransactionType(), biller.getPaidAmount(),
						biller.getBillId());
			}
			// CHEQUE
			if (biller.getTransactionType().equals(2)) {
				SQL1 += ", amount = ? , cheque_no=? WHERE bill_id= ?";
				sid1 = jdbcTemplate.update(SQL1, biller.getAccountId(), biller.getTransactionType(), biller.getPaidAmount(),
						biller.getChequeNumber(), biller.getBillId());
			}

			// Pay from Account Balance
			if (biller.getTransactionType().equals(4)) {
				SQL1 += ", amount = ? WHERE bill_id= ?";
				sid1 = jdbcTemplate.update(SQL1, biller.getAccountId(), biller.getTransactionType(), biller.getPaidAmount(),
						biller.getBillId());
			}

			/*
			 * // Get the latest student id to be used in Marks table String
			 * SQL2 =
			 * "insert into contact_preferences (preferred_time_start, preferred_time_end,email_id,Mobile_Number,email_preference,mobile_preference,
			 * no_of_days_before_due_date) values (?, ?,?,?,?,?,?)"
			 * ; sid2 = jdbcTemplate.update(
			 * SQL2,"12:10:00","20:10:00","111","a@a.mal"
			 * ,biller.getEmailAlert(),biller.getEmailAlert(),3 );
			 */

			transactionManager.commit(status);
		} catch (DataAccessException e) {
			System.out.println("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}

		int updateStatus = 0;
		/*
		 * if(sid1 ==1 && sid2==1){ updateStatus=1; }else{ updateStatus=0; }
		 */

		return updateStatus;
	}

	public List<RefDataResponse> findSocieties() {
		return getRefData("SELECT complex_id as abbreviation , complex_description as name FROM mydb.ref_apartment_complex_types",
				false);
	}

	public List<RefDataResponse> findApartmentBuildings() {
		return getRefData(
				"SELECT building_id as abbreviation , building_name as name, complex_id as filter FROM mydb.apartment_buildings",
				true);
	}

	public List<RefDataResponse> findApartments() {
		return getRefData("SELECT apartment_id as abbreviation , apt_number as name, building_id as filter FROM mydb.apartments",
				true);
	}

	public List<RefDataResponse> findOwnershipType() {
		return getRefData(
				"SELECT ownership_code as abbreviation , ownership_code_description as name FROM mydb.ref_ownership_status Union SELECT lease_holder_code " +
				"as abbreviation , lease_holder_description as name FROM mydb.ref_lease_holder",
				false);
	}

	public List<RefDataResponse> findUtilityType() {
		return getRefData(
				"SELECT utility_type_code as abbreviation , utility_type_description as name FROM mydb.ref_utility_types", false);
	}

	public List<RefDataResponse> getRefData(String query, final boolean filter) {
		return this.jdbcTemplate.query(query, new RowMapper<RefDataResponse>() {
			public RefDataResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
				RefDataResponse s = new RefDataResponse();
				s.setAbbreviation(rs.getString("abbreviation"));
				s.setName(rs.getString("name"));
				if (filter) {
					s.setFilter(rs.getString("filter"));
				}
				return s;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Object executeProc(final String procName, final String customerId) {
		Object returnObj = this.jdbcTemplate.execute(new CallableStatementCreator() {

			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				System.out.println("customerId" + customerId + "procName" + procName);
				CallableStatement cs = con.prepareCall("call " + procName + "(?)");
				cs.setInt(1, Integer.valueOf(customerId));

				return cs;
			}
		}, new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs) throws SQLException {
				cs.execute();
				ResultSet rs = cs.getResultSet();
				Object obj = null;
				if (rs != null) {
					int nF = rs.getMetaData().getColumnCount();
					rs.last();
					String[][] out = new String[rs.getRow()][nF];

					for (int i = 0; i < nF; i++) {
						rs.beforeFirst();
						int n = 0;
						while (rs.next()) {
							out[n][i] = rs.getString(i + 1);
							n++;
						}
					}
					obj = out;
					cs.close();
				}
				return obj; // Whatever is returned here is returned from the
							// jdbcTemplate.execute method
			}
		});
		return returnObj;
	}

	public List<UserSummary> getUserSummaryDetails(User user) {
		return this.jdbcTemplate
				.query("select customers.customer_id as user_id, \"customer\" as user_type, customers.first_name as first_name from customers , " +
						"users where customers.customer_id = users.USER_ID and users.username=? union select guests.guest_id as user_id , \"guest\" " +
						"as user_type, guest_first_name as first_name from guests , users  where guests.guest_id = users.USER_ID and users.username=?",

				new Object[] { user.getUsername(), user.getUsername() }, new RowMapper<UserSummary>() {

					public UserSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserSummary s = new UserSummary();

						s.setUserId(rs.getString("user_id"));
						s.setFirstName(rs.getString("first_name"));
						s.setUserType(rs.getString("user_type"));
						return s;
					}
				});
	}
}
