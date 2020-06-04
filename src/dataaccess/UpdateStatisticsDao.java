package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import config.MysqlConfig;
import dataaccess.parent.MySqlDao;

public class UpdateStatisticsDao extends MySqlDao{
	public void saveStatistics(List<String> list) throws SQLException {

		String query = buildQuery(list);// SQL文を作成

		Connection conn = null;
		PreparedStatement ps = null;

		this.init();
		this.setSQL(query);
		try {
			conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
			conn.setAutoCommit(false); // 自動コミットしない
			ps = conn.prepareStatement(this.getSQL());
			ps.executeUpdate();
			conn.commit();
			System.out.println("データ追加完了！");
		} catch (Exception e) {
			conn.rollback(); // 失敗したらロールバック
			System.out.println("***データの更新に失敗しました***");
			e.printStackTrace();
		} finally {
			new Closer().closeConnection(conn, ps); // クローズ処理
		}
	}

	// データ追加用SQL生成処理
	private String buildQuery(List<String> list) {
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