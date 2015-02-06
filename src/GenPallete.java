public class GenPallete {

	public static void main(String[] args) {
		LWGenPallete A = new LWGenPallete();

		//A.setEditable((byte)1);
		A.setBase("46B6B3");
		A.nextColors();

		System.out.println(A.toString());
	}
}