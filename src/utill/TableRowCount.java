package utill;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TableRowCount {
	
	public TableRowCount(){
		
	}
	
	public int  Count(String tableName) {
		String SQL = "SELECT COUNT(*) from ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(SQL);
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, tableName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();	
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}try {
				if(rs != null) {
					rs.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return -1;  //데이터 베이스 오류
	}

}
