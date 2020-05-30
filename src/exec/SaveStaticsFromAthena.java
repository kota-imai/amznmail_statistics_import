package exec;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataaccess.AthenaDao;
import dataaccess.UpdateStatisticsDao;

public class SaveStaticsFromAthena {
	public static void main(String[] args) {

		// 検索結果にメモリ確保
		List<String> list = new ArrayList<String>();
		// Athena接続
		AthenaDao athdao = new AthenaDao();
		list = athdao.search();

		// データがあればMySQLに書き込み
		if (list.isEmpty()) {
			System.out.println("取り込むデータはありませんでした");
		} else {
			try {
				// 当日分のデータがある場合のみMySQLにデータを移動する
				// Mysql接続
				UpdateStatisticsDao dao = new UpdateStatisticsDao();
				dao.saveStatistics(list);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}