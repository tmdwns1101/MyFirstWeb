package evaluation;

import java.util.ArrayList;

public class Test {

	public static void main() {
		
		System.out.println("메인 들어갔다 왔어!");
		EvaluationDAO testDAO = new EvaluationDAO();
		ArrayList<EvaluationDTO> test = new ArrayList<EvaluationDTO>();
		int result = testDAO.getEvaluationInfo("전체", "최신순", "학점", test);
	}
}
