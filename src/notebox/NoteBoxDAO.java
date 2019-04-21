package notebox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utill.DatabaseUtill;

public class NoteBoxDAO {
	
	public int write(NoteBoxDTO noteBoxDTO) {
		String SQL = "INSERT INTO NOTEBOX VALUES(?, ?, ?, ?, NULL, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
		
			pstmt.setString(1, noteBoxDTO.getToUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, noteBoxDTO.getFromUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, noteBoxDTO.getNoteTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, noteBoxDTO.getNoteContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(5, Integer.parseInt(noteBoxDTO.getEvaluationID()));
			
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
	
	public int getNoteBoxInfo(String userID, ArrayList<NoteBoxDTO> noteBoxinfoes){
		
		String SQL = "SELECT * FROM NOTEBOX WHERE toUserID = ?";
		
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(SQL);
		
	
		
		try {
			conn = DatabaseUtill.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			
			
	
			
			rs = pstmt.executeQuery();
			
		
			
			while(rs.next()) {
				String toUserID = rs.getString(1);
				String fromUserID = rs.getString(2);
				String noteTitle = rs.getString(3);
				String noteContent = rs.getString(4);
				int noteBoxID = rs.getInt(5);
				String evaluationID = rs.getString(6);
				NoteBoxDTO noteBoxDTO = new NoteBoxDTO(toUserID, fromUserID, noteTitle, noteContent, noteBoxID, evaluationID);
				noteBoxinfoes.add(noteBoxDTO);
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
	
}
