package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.simba.athena.commons.codec.digest.DigestUtils;

import config.AthenaConfig;
 
public class AthenaDao {
	public List<String> search() {
		// Athena database情報
		final String athenaUrl = "jdbc:awsathena://athena.ap-northeast-1.amazonaws.com:443";
		final String stagingDirKeyName = DigestUtils.md5Hex(String.valueOf(System.nanoTime()));

		// 検索用SQLを作成
		// 過去1日のデータを検索する
		final String sql =
				"SELECT eventtype AS Event, mail.timestamp AS Timestamp, mail.commonheaders.\"from\" AS FromAddress, mail.commonheaders.\"to\" AS ToAddress, mail.commonheaders.to AS ToAddress, mail.tags.ses_operation AS Operation, mail.tags.ses_configurationset AS ConfigSet, mail.tags.ses_source_ip AS SourceIP, mail.tags.ses_caller_identity AS AuthenticatedBy, open.timestamp AS UserOpenTimeStamp, open.userAgent AS UserAgent, open.ipAddress AS IpAddress, delivery.timestamp AS TimestampDelivery, delivery.\"recipients\" AS Recipients, delivery.smtpResponse AS SmtpResponse, delivery.reportingMTA AS ReportingMTA , click.timestamp AS ClickTimestamp, click.userAgent AS ClickUserAgent, click.link AS ClickLink From  maidoari_statics.tab1 "
				+ "WHERE mail.timestamp >= substr(cast(current_date - interval '1' day as VARCHAR), 1,13) and mail.timestamp < substr(cast(current_date as VARCHAR), 1,13)";
		
		Properties param = new Properties();
		AthenaConfig athena = new AthenaConfig();
		param.put("user", athena.getUSER());
		param.put("password", athena.getPASSWORD());
		param.put("s3_staging_dir", "s3://mybucket-athena-querylogs/" + stagingDirKeyName + "/");

		//　戻り値用
		List<String> list = new ArrayList<String>();
		try {
			final String comma = ",";			
			Connection conn = DriverManager.getConnection(athenaUrl, param);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql); // 検索実行
				while (rs.next()) {
					StringBuilder sb = new StringBuilder();
					sb.append(withSingleQuote(rs.getString("Event")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("Timestamp")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("FromAddress")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("ToAddress")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("ConfigSet")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("SourceIP")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("AuthenticatedBy")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("UserOpenTimeStamp")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("UserAgent")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("TimestampDelivery")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("Recipients")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("SmtpResponse")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("ClickTimestamp")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("ClickUserAgent")));sb.append(comma);
					sb.append(withSingleQuote(rs.getString("ClickLink")));
					list.add(sb.toString());
					sb = null; // メモリ解放
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
	
	//シングルクォーテーションで囲むだけ
	private String withSingleQuote(String str) {
		return "'" + str + "'";
	}
}