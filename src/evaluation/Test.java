package evaluation;

import java.util.ArrayList;

public class Test {

	public static void main() {
		
		System.out.println("���� ���� �Ծ�!");
		EvaluationDAO testDAO = new EvaluationDAO();
		ArrayList<EvaluationDTO> test = new ArrayList<EvaluationDTO>();
		int result = testDAO.getEvaluationInfo("��ü", "�ֽż�", "����", test);
	}
}
