package utill;

public class Rules {
	
	
	public int PassWordRule(String password) {
		int minLength = 8;
		boolean[] checker = {false, false, false};  //'0' = 알파벳 확인 '1' = 특수문자 확인 '2' = 숫자 확인
		
		if(password.length() < minLength) {
			return -2;  //최소 길이보다 작을 때
		}
		
		
		for(int i=0; i<password.length();i++) {
			char cur = password.charAt(i);
			if(checker[0] == false || checker[1] == false ||checker[1] == false) {
				if(cur > 47  && cur < 58 ) {
					checker[2] = true;
					}
				else if((cur > 32 && cur < 48) || (cur > 62 && cur < 65)) {
					checker[1] = true;
					}
				else if(cur > 64 && cur <91) {
					checker[0] = true;
				}
				else if(cur > 96 && cur < 123) {
					checker[0] = true;
				}
				else {
					continue;
				}
			}
		}
		
		if(checker[0] == true && checker[1] == true && checker[1] == true) {
			return 1;
		}
		
		return -1;
	}

}
