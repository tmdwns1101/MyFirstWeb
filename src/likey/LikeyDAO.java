package likey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utill.DatabaseUtill;

public class LikeyDAO {
	
	public static final int MYSQL_DUPLICATE_PK = 1062;

	public int Like(String userID, String evaluationID, String userIP) {
		String SQL = "INSERT INTO likey VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int errorCode = -1;
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			
			
			pstmt.setString(1, userID);
			pstmt.setInt(2, Integer.parseInt(evaluationID));
			pstmt.setString(3, userIP);
				
			return pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			int error = e.getErrorCode();
			System.out.println("에외 번호 " + error );
			if(error == MYSQL_DUPLICATE_PK) { //중복 key 에러처리
				errorCode = 3;
			}
		}
		catch(Exception e) {
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
			}
		}
		
		return errorCode;  //데이터 베이스 오류
	}
}
