package getcard;

import java.util.ArrayList;

public class CheckResult {
	// ������� ���� �� Card
//	int[] cardNum = new int[Constants.CARDNUM];
	// ���� ���о��� ���� �� ī�� ���� 1~10, J, Q, K
	int[] countSameCard = new int[13];

	public String getResult(int[] card) {
//		for (int i = 0; i < Constants.CARDNUM; i++) {
//			System.out.print(card[i] + "\t");
//		}
//		System.out.println();
//		System.out.println();
		MappingCard mc = new MappingCard();

		// Straight ���� �Ǵ� Fields
		int spadeNum = 0;
		int heartNum = 0;
		int cloverNum = 0;
		int diaNum = 0;

		// ����� ���� 5���� ī�带 ���� ����
		ArrayList<Integer> selSpadeCardPattern 	= new ArrayList<Integer>();
		ArrayList<Integer> selCloverCardPattern = new ArrayList<Integer>();
		ArrayList<Integer> selHeartCardPattern 	= new ArrayList<Integer>();
		ArrayList<Integer> selDiaCardPattern 	= new ArrayList<Integer>();

		// ********************************************
		// * �ο� ��Ʈ����Ʈ �÷���(Royal Straight Flush) *
		// * 5���� ī�尡 ���� ����� �Բ� A���� ���������� *
		// * �����Ǿ� �ִ� �� *
		// ********************************************

		// ********************************************
		// * ��Ʈ����Ʈ �÷��� (Straight Flush) *
		// * 5���� ī�尡 ���� ����� �Բ� A���� ���� ������ *
		// * �����Ǿ� �ִ� �� *
		// ********************************************

		// ���� ������ �� ���ֳ� �Ǵ�.
		for (int i = 0; i < Constants.CARDNUM; i++) {
			if (mc.getCardPattern(card[i]).equals(Constants.SPADE)) {
				spadeNum++;
				selSpadeCardPattern.add((Integer) mc.getCardNum(card[i]));
			} else if (mc.getCardPattern(card[i]).equals(Constants.CLOVER)) {
				cloverNum++;
				selCloverCardPattern.add((Integer) mc.getCardNum(card[i]));
			} else if (mc.getCardPattern(card[i]).equals(Constants.HEART)) {
				heartNum++;
				selHeartCardPattern.add((Integer) mc.getCardNum(card[i]));
			} else if (mc.getCardPattern(card[i]).equals(Constants.DIAMOND)) {
				diaNum++;
				selDiaCardPattern.add((Integer) mc.getCardNum(card[i]));
			}
		}
		// ���� ���� ���ڸ� 1���� ����.
		// Straight_Flush or Royal_Straight
		if ((selCloverCardPattern.size() >= 5) && (IsContinuous(selCloverCardPattern) == true)) {
			return GetStraightResult(selCloverCardPattern, Constants.CLOVER);
		} else if ((selDiaCardPattern.size() >= 5) && (IsContinuous(selDiaCardPattern) == true)) {
			return GetStraightResult(selDiaCardPattern, Constants.DIAMOND);
		} else if ((selHeartCardPattern.size() >= 5 && (IsContinuous(selHeartCardPattern) == true))) {
			return GetStraightResult(selHeartCardPattern, Constants.HEART);
		} else if ((selSpadeCardPattern.size() >= 5) && (IsContinuous(selSpadeCardPattern) == true)) {
			return GetStraightResult(selSpadeCardPattern, Constants.SPADE);
		}

		// ********************************************
		// * �÷��� (Flush) *
		// * 5���� ī�尡 ���ڿʹ� ������� ���� �������� *
		// * �Ǿ� �ִ� �� *
		// ********************************************

		else if (selCloverCardPattern.size() >= 5) {
			return Flush(selCloverCardPattern);
		} else if (selDiaCardPattern.size() >= 5) {
			return Flush(selDiaCardPattern);
		} else if (selHeartCardPattern.size() >= 5) {
			return Flush(selHeartCardPattern);
		} else if (selSpadeCardPattern.size() >= 5) {
			return Flush(selSpadeCardPattern);
		}

		// ********************************************
		// * ��Ʈ����Ʈ (Straigh) 						  *
		// * 5���� ī�尡 ���������� �����Ǿ� �ִ� ��. 			  *
		// ********************************************
		ArrayList<Integer> straight1 = devOriginCard(card, 0, 5);
		ArrayList<Integer> straight2 = devOriginCard(card, 1, 6);
		ArrayList<Integer> straight3 = devOriginCard(card, 2, 7);
		
		if(IsContinuous2(0, 5, straight1) || IsContinuous2(1, 6, straight2) || IsContinuous2(2, 7, straight3)) 
			return "Straight"; 

		countSameCard = countSameNumCard(card);

		// Four_Card �̸��� ��� �Ǵ�
		String pairedCard = getPairCard(countSameCard);

		if (!pairedCard.equals("")) {

			// Triple or Two_pair or One_pair
			return pairedCard;
		}
		return null;
	}

	private ArrayList<Integer> devOriginCard(int[] card, int start, int end) {
		ArrayList<Integer> straight = new ArrayList<Integer>();
		for (int i = start; i < end; i++) {
			straight.add(card[i]);
		}
		return straight;
	}

	private String Flush(ArrayList<Integer> selCardPattern) {
		for (int i = selCardPattern.size(); i > selCardPattern.size() - 5; i--) {
			if ((Integer) selCardPattern.get(i - 1) == 14) {
				return "A_Flush";
			} else {
				return "" + selCardPattern.get(i - 5) + "_Flush";
			}
		}
		return null;
	}

	private String getPairCard(int[] countSameCard2) {
		MappingCard mc = new MappingCard();
		int checkThree = 0;
		int checkTwo = 0;

		// Ʈ���� or ����� ����
		int tripleNum = 0;
		int twoPairNum = 0;
		// ********************************************
		// * 4 ī�� (Four Card - Four of a Kind) 		  *
		// * 4���� ī�尡 ���� ���ڷ� �����Ǿ� �ִ� �� 				  *
		// ********************************************

		for (int i = 0; i < countSameCard.length; i++) {
			// FourCard�� ��� return
			if (countSameCard[i] == 4) {
				if (i < 9) {
					return "" + (i + 2) + "_Four_Card";
				} else
					return mc.aboveTen(i + 2) + "_Four_Card";
			} else if (countSameCard[i] == 3) {
				checkThree++;
				tripleNum = (i + 2);
			} else if (countSameCard[i] == 2) {
				checkTwo++;
				twoPairNum = (i + 2);
			}

			// ********************************************
			// * Ǯ �Ͽ콺 (Full House) 					  *
			// * 3���� ī���� ���ڰ� ����, 						  *
			// * 2���� ī�� ���ڰ� ���� �� ��. 					  *
			// ********************************************
			if ((checkThree == 1) && (checkTwo >= 1)) {
				if (tripleNum <= 10) {
					return "" + tripleNum + "_Full_House";
				} else
					return mc.aboveTen(tripleNum) + "_Full_House";
			}
		}

		// ********************************************
		// * Ʈ���� (Triple - Three of a Kind) 		  *
		// * 3���� ī�尡 ���� ������ ��, 						  *
		// ********************************************
		if (checkThree >= 1)
			if (tripleNum <= 9) {
				return "" + tripleNum + "_Triple";
			} else
				return mc.getCardNum(tripleNum-1) + "_Triple";

		// ********************************************
		// * �� ��� (Two Pair) 						  *
		// * 2���� ī�尡 ���� ���ڷ� 2 ���� ��. 				  *
		// ********************************************
		else if (checkTwo >= 2)
			if (twoPairNum <= 9) {
				return "" + twoPairNum + "_Two_Pair";
			} else
				return mc.getCardNum(twoPairNum-1) + "_Two_Pair";

		// ********************************************
		// * �� ��� (One Pair) 						  *
		// * 2���� ī�尡 ���� ���ڷ� 1���� ��. 					  *
		// ********************************************
		else if (checkTwo >= 1)
			if (tripleNum <= 9) {
				return "" + twoPairNum + "_One_Pair";
			} else
				return mc.getCardNum(twoPairNum-1) + "_One_Pair";

		return "";
	}

	// ���� �������� ī�� ���ġ
	public int[] ArrangeCard(int startIndex, int[] cardNum, int totaclCardNum) {
		int[] tempCard = new int[Constants.CARDNUM];
		for (int i = startIndex; i < Constants.CARDNUM; i++) {
			for (int j = i + 1; j < Constants.CARDNUM; j++) {
				if (cardNum[i] > cardNum[j]) {
					cardNum[i] = cardNum[i] + cardNum[j];
					cardNum[j] = cardNum[i] - cardNum[j];
					cardNum[i] = cardNum[i] - cardNum[j];
				}
			}
			tempCard[i] = cardNum[i];
		}
		return tempCard;
	}

	// ********* Straight �Ǵ� �Լ�_1 *********
	public boolean IsContinuous(ArrayList<Integer> fiveCard) {
		for (int i = 1; i < 5; i++) {
			if ((Integer) fiveCard.get(i - 1) != (Integer) fiveCard.get(i) - 1) {
				return false;
			}
			if (fiveCard.contains(14) && fiveCard.contains(2) && fiveCard.contains(3) && fiveCard.contains(4) && fiveCard.contains(5)) {
				return true;
			}
		}
		return true;
	}

	// ********* Straight �Ǵ� �Լ�_2 *********
	public String GetStraightResult(ArrayList<Integer> fiveCard, String pattern) {
		if ((fiveCard.size() >= 5) && IsContinuous(fiveCard)) {
			for (int i = fiveCard.size(); i > fiveCard.size() - 5; i--) {
				if (fiveCard.contains((Integer) 14) 
						&& fiveCard.contains((Integer) 2)) {
					return pattern + "_A" + "_Royal_Straight";
				}
				return pattern + "_" + (fiveCard.get(i - 5)-1)
						+ "_Straight_Flush";
			}
		}
		return null;
	}
	
	// ********* Straight �Ǵ� �Լ�_3 *********
	public boolean IsContinuous2(int start, int end, ArrayList<Integer> fiveCard) {
		MappingCard mc = new MappingCard();
		for (int i = start; i < end; i++) {
			if ((Integer)mc.getCardNum((Integer) fiveCard.get(i)) != (Integer)mc.getCardNum((Integer) fiveCard.get(i))-1) {
				return false;
			}
		}
		return true;
	}	

	// ********* ���� �� ī�� �ż� �ľ� *********
	public int[] countSameNumCard(int[] card) {
		MappingCard mc = new MappingCard();
		int[] countSameCard = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < card.length; i++) {
			countSameCard[(Integer) mc.getCardNum(card[i])-2]++;
		}
		return countSameCard;
	}
}

