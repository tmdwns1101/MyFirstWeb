package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utill.DatabaseUtill;



public class EvaluationDAO {
	
	
	public EvaluationDAO() {
		
	}
	
	public int write(EvaluationDTO evaluationDTO) {
		String SQL = "INSERT INTO EVALUATION VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
		
			pstmt.setString(1, evaluationDTO.getUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, evaluationDTO.getLectureName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, evaluationDTO.getProfessorName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(4, evaluationDTO.getLectureYear());
			pstmt.setString(5, evaluationDTO.getSemesterDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, evaluationDTO.getLectureDivide().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(7, evaluationDTO.getEvaluationTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(8, evaluationDTO.getEvaluationContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(9, evaluationDTO.getTotalScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(10, evaluationDTO.getComfortableScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(11, evaluationDTO.getLectureScore().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			
			return pstmt.executeUpdate();
			
		
			//System.out.println("return value is "+ pstmt.executeUpdate());
			
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
		
		return -1; //데이터 베이스 오류
	}
	
	
	public int  EvaluationRowCount(String filter) {
		String SQL = "";
		if(filter.equals("전체")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION";
		}else if(filter.equals("전공")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION WHERE lectureDivide = '전공'";
		}else if(filter.equals("교양")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION WHERE lectureDivide = '교양'";
		}else if(filter.equals("기타")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION WHERE lectureDivide = '기타'";
		}else {
			return -2; // 문자열 오류
		}
		
		//String SQL = "SELECT COUNT(*) FROM EVALUATION";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(SQL);
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			
	
			
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
	
	
	//filter1 is 강의 구분
	//filter2 is 정렬 순서
	public int getEvaluationInfo(String filter1, String filter2, String search, ArrayList<EvaluationDTO> evaluationInfo){
		
		String SQL = "";
		if(filter1.equals("전체")) {
			filter1 = "";
		}
		
		if(filter2.equals("최신순")) {
			SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE ? "
					+ "ORDER BY evaluationID DESC";
		}else if(filter2.equals("추천순")) {
			SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE ? "
					+ "ORDER BY likeCount DESC";
		}
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(SQL);
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + filter1 + "%");
			pstmt.setString(2, "%" + search + "%");
			
	
			
			rs = pstmt.executeQuery();
			
		
			
			while(rs.next()) {
				//EvaluationDTO evaluationDTO = new EvaluationDTO();
				//sql.append(rs.getNString(1));
				int evaluationID = rs.getInt(1);
				//String testStr = getLectureName(cur);
				String userID = rs.getString(2);
				String lectureName = rs.getString(3);
				String professorName = rs.getString(4);
				int lectureYear =rs.getInt(5);
				String semesterDivide = rs.getString(6);
				String lectureDivide = rs.getString(7);
				String evaluationTitle = rs.getString(8);
				String evaluationContent = rs.getString(9);
				String totalScore = rs.getString(10);
				String comfortableScore = rs.getString(11);
				String lectureScore = rs.getString(12);
				int likeCount = rs.getInt(13);
				EvaluationDTO evaluationDTO = new EvaluationDTO(evaluationID, userID, lectureName, professorName, lectureYear,
						semesterDivide, lectureDivide, evaluationTitle, evaluationContent,
						totalScore, comfortableScore, lectureScore, likeCount);
				
				evaluationInfo.add(evaluationDTO);

				//System.out.println("테스트 문자열 : "+ testStr);
				//return null; //test
				
			}
			
			return 1;
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
	
	public String  getUserID(String evaluationID) {
		String SQL = "SELECT userID FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getLectureName(String evaluationID) {
		String SQL = "SELECT lectureName FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getProfessorName(String evaluationID) {
		String SQL = "SELECT professorName FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public int  getLectureYear(String evaluationID) {
		String SQL = "SELECT lectureYear FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
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
	
	public String  getSemesterDivide(String evaluationID) {
		String SQL = "SELECT semesterDivide FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getLectureDivide(String evaluationID) {
		String SQL = "SELECT lectureDivide FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getEvaluationTitle(String evaluationID) {
		String SQL = "SELECT evaluationTitle FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getEvaluationContent(String evaluationID) {
		String SQL = "SELECT evaluationContent FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getTotalScore(String evaluationID) {
		String SQL = "SELECT totalScore FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getComfortableScore(String evaluationID) {
		String SQL = "SELECT comfortableScore FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if( rs.getString(1) == "A") {
					result = "매우 쉬움";
				}else if( rs.getString(1) == "B") {
					result = "쉬움";
				}else if( rs.getString(1) == "C") {
					result = "어려움";
				}else {
					result = "매우 어려움";
				}
				
				return result;
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
		
		return null;  //데이터 베이스 오류
	}
	
	public String  getLectureScore(String evaluationID) {
		String SQL = "SELECT lectureScore FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
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
		
		return null;  //데이터 베이스 오류
	}
	
	public int  getLikeCount(String evaluationID) {
		String SQL = "SELECT likeCount FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
	
			
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
	
	public int Like(String evaluationID) {
		String SQL = "UPDATE evaluation SET  likeCount = likeCount + 1 WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, Integer.parseInt(evaluationID));
			
			return pstmt.executeUpdate();
			
			
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
			}
		}
		
		return -1;  //데이터 베이스 오류
	}
	
	public int Delete(String evaluationID) {
		String SQL = "Delete FROM evaluation WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, Integer.parseInt(evaluationID));
			
			
			return pstmt.executeUpdate();
			
			
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
			}
			
		}
		
		return -1;  //데이터 베이스 오류
	}
	
	
	
}
