package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import config.MysqlConfig;

public class UpdateStatisticsDao {
    public void saveStatistics(List<String> list) throws SQLException {

		String insertSql = buildSql(list);// SQL文を作成

		Connection conn = null;
		PreparedStatement ps = null;
		MysqlConfig mySql = new MysqlConfig();
		try {
			conn = DriverManager.getConnection(mySql.getURL(), mySql.getUSER(), mySql.getPASSWORD());
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(insertSql);
			ps.executeUpdate();
			conn.commit();
			System.out.println("データ追加完了！");
		} catch (Exception e) {
			conn.rollback();
			System.out.println("***データの更新に失敗しました***");
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			} // close
		}
	}

	// データ追加用SQL生成処理
	private String buildSql(List<String> list) {
		StringBuilder sb = new StringBuilder();
		final String SQL = "insert into Statics" + "(" + "EventType" + ",StreamedTimestamp" + ",FromAddress"
				+ ",ToAddress" + ",ConfigSet" + ",SourceIP" + ",AuthenticatedBy" + ",UserOpenTimeStamp" + ",UserAgent"
				+ ",TimeStampDelivery" + ",Recipients" + ",SmtpResponse" + ",ClickTimestamp" + ",ClickUserAgent"
				+ ",ClickLink" + ") values";
		sb.append(SQL);
		int listLength = list.size();
		for (int i = 0; i < listLength; i++) {
			StringBuilder subsb = new StringBuilder();
			subsb.append("(");
			subsb.append(list.get(i));
			subsb.append(")");
			if (i != listLength - 1) {
				subsb.append(",");
			}
			sb.append(subsb.toString());
			subsb = null;
		}
		return sb.toString();
	}
}