package getcard;

import java.util.Random;

public class GetCard {
	public int[] mixCard() {
		int[] cardNum = new int[14];
		int[] retVal  = new int[14];

		Random ran = new Random();
		MappingCard mc = new MappingCard();
		for (int i = 0; i < 14; i++) {
			cardNum[i] = ran.nextInt(52) + 1;
		}
		//
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				if( (i != j) && (cardNum[i] == cardNum[j]) ){
					cardNum[j] = ran.nextInt(52) + 1;
				}
			}
		}

		//******** cardNum은오름차순으로정렬되므로원래의랜덤한순서의카드배열retVal ***********
		retVal = cardNum.clone();

		// 순서대로정렬된User Card
		int[] userCard = new int[Constants.CARDNUM];
		// 순서대로정렬된Com Card
		int[] compCard = new int[Constants.CARDNUM];
		// 문양구분없이숫자별카드개수1~10, J, Q, K

		for(int j=0;j<7;j++) {
			if(j==0) System.out.print("COM_CARD :" + "\t" + mc.getCardPattern(cardNum[j]) + "\t" + mc.getCardNum(cardNum[j]) + "\t");
			else System.out.print(mc.getCardPattern(cardNum[j]) + "\t" + mc.getCardNum(cardNum[j]) + "\t");
			if(j==6) System.out.println();
		}
		for(int k=7;k<14;k++) {
			if(k==7) System.out.print("BSH_CARD :" + "\t" + mc.getCardPattern(cardNum[k]) + "\t" + mc.getCardNum(cardNum[k]) + "\t");
			else System.out.print(mc.getCardPattern(cardNum[k]) + "\t" + mc.getCardNum(cardNum[k]) + "\t");
			if(k==13) System.out.println();
		}

		// 오름차순으로정렬된User 카드( 0 ~ 7 )
		compCard = ArrangeCard(0, 7, cardNum);
		// 오름차순으로정렬된Com 카드( 7 ~ 14 )
		userCard = ArrangeCard(7, 14, cardNum);

		//************* 결과판단*************
		CheckResult cr = new CheckResult();
		String resultComp =  cr.getResult(compCard);
		String resultUser =  cr.getResult(userCard);
		System.out.println("******************************************************************");
		System.out.println("Comp_Result : " + resultComp);
		System.out.println("User_Result : " + resultUser);
		System.out.println("******************************************************************");
		return retVal;
	}

	// 오름차순으로카드재배치
	public int[] ArrangeCard(int startIndex, int endIndex, int[] cardNum) {
		int[] tempCard = new int[Constants.CARDNUM];
		int reIndex = 0;
		for (int i = startIndex; i < endIndex; i++) {
			for (int j = i + 1; j < endIndex; j++) {
				if (cardNum[i] > cardNum[j]) {
					cardNum[i] = cardNum[i] + cardNum[j];
					cardNum[j] = cardNum[i] - cardNum[j];
					cardNum[i] = cardNum[i] - cardNum[j];
				}
			}
			tempCard[reIndex] = cardNum[i];
			reIndex++;
		}
		return tempCard;
	}

}
