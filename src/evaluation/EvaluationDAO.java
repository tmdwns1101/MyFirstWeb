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
		//ResultSet rs = null;
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
		
			pstmt.setString(1, evaluationDTO.getUserID());
			pstmt.setString(2, evaluationDTO.getLectureName());
			pstmt.setString(3, evaluationDTO.getProfessorName());
			pstmt.setInt(4, evaluationDTO.getLectureYear());
			pstmt.setString(5, evaluationDTO.getSemesterDivide());
			pstmt.setString(6, evaluationDTO.getLectureDivide());
			pstmt.setString(7, evaluationDTO.getEvaluationTitle());
			pstmt.setString(8, evaluationDTO.getEvaluationContent());
			pstmt.setString(9, evaluationDTO.getTotalScore());
			pstmt.setString(10, evaluationDTO.getComfortableScore());
			pstmt.setString(11, evaluationDTO.getLectureScore());
			
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
		
		return -1; //������ ���̽� ����
	}
	
	
	public int  EvaluationRowCount(String filter) {
		String SQL = "";
		if(filter.equals("��ü")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION";
		}else if(filter.equals("����")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION WHERE lectureDivide = '����'";
		}else if(filter.equals("����")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION WHERE lectureDivide = '����'";
		}else if(filter.equals("��Ÿ")) {
			SQL = "SELECT COUNT(*) FROM EVALUATION WHERE lectureDivide = '��Ÿ'";
		}else {
			return -2; // ���ڿ� ����
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
		
		return -1;  //������ ���̽� ����
	}
	
	
	public int getEvaluationInfo(String filter, ArrayList<EvaluationDTO> evaluationInfo){
		
		
		String SQL = "";
		if(filter.equals("��ü")) {
			SQL = "SELECT evaluationID FROM EVALUATION";
		}else if(filter.equals("����")) {
			SQL = "SELECT evaluationID FROM EVALUATION WHERE lectureDivide = '����'";
		}else if(filter.equals("����")) {
			SQL = "SELECT evaluationID FROM EVALUATION WHERE lectureDivide = '����'";
		}else if(filter.equals("��Ÿ")) {
			SQL = "SELECT evaluationID FROM EVALUATION WHERE lectureDivide = '��Ÿ'";
		}else {
			return -2; // ���ڿ� ����
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
			
		
			
			while(rs.next()) {
				//EvaluationDTO evaluationDTO = new EvaluationDTO();
				//sql.append(rs.getNString(1));
				int cur = rs.getInt("evaluationID");
				//String testStr = getLectureName(cur);
				String userID = this.getUserID(cur);
				String lectureName = this.getLectureName(cur);
				String professorName = this.getProfessorName(cur);
				int lectureYear = this.getLectureYear(cur);
				String semesterDivide = this.getSemesterDivide(cur);
				String lectureDivide = this.getLectureDivide(cur);
				String evaluationTitle = this.getEvaluationTitle(cur);
				String evaluationContent = this.getEvaluationContent(cur);
				String totalScore = this.getTotalScore(cur);
				String comfortableScore = this.getComfortableScore(cur);
				String lectureScore = this.getLectureScore(cur);
				int likeCount = this.getLikeCount(cur);
				EvaluationDTO evaluationDTO = new EvaluationDTO(cur, userID, lectureName, professorName, lectureYear,
						semesterDivide, lectureDivide, evaluationTitle, evaluationContent,
						totalScore, comfortableScore, lectureScore, likeCount);
				
				evaluationInfo.add(evaluationDTO);

				//System.out.println("�׽�Ʈ ���ڿ� : "+ testStr);
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
		
		return -1;  //������ ���̽� ����
	}
	
	public String  getUserID(int evaluationID) {
		String SQL = "SELECT userID FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getLectureName(int evaluationID) {
		String SQL = "SELECT lectureName FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getProfessorName(int evaluationID) {
		String SQL = "SELECT professorName FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public int  getLectureYear(int evaluationID) {
		String SQL = "SELECT lectureYear FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return -1;  //������ ���̽� ����
	}
	
	public String  getSemesterDivide(int evaluationID) {
		String SQL = "SELECT semesterDivide FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getLectureDivide(int evaluationID) {
		String SQL = "SELECT lectureDivide FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getEvaluationTitle(int evaluationID) {
		String SQL = "SELECT evaluationTitle FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getEvaluationContent(int evaluationID) {
		String SQL = "SELECT evaluationContent FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getTotalScore(int evaluationID) {
		String SQL = "SELECT totalScore FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getComfortableScore(int evaluationID) {
		String SQL = "SELECT comfortableScore FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if( rs.getString(1) == "A") {
					result = "�ſ� ����";
				}else if( rs.getString(1) == "B") {
					result = "����";
				}else if( rs.getString(1) == "C") {
					result = "�����";
				}else {
					result = "�ſ� �����";
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
		
		return null;  //������ ���̽� ����
	}
	
	public String  getLectureScore(int evaluationID) {
		String SQL = "SELECT lectureScore FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return null;  //������ ���̽� ����
	}
	
	public int  getLikeCount(int evaluationID) {
		String SQL = "SELECT likeCount FROM EVALUATION WHERE  evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, evaluationID);
	
			
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
		
		return -1;  //������ ���̽� ����
	}
	
	
}
