public class GenPallete {

	public static void main(String[] args) {
		LWGenBridge A = new LWGenBridge();

		//A.setEditable((byte)1);
		String[] test = new String[2];
		test[0] = "AA6c5F";
		test[1] = "BB2034";
		A.setBases(test);
		A.setSteps((byte)3);
		A.nextColors();

		System.out.println(A.toString());
	}
}