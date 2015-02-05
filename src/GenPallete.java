public class GenPallete {

	public static void main(String[] args) {
		LWGenPallete A = new LWGenPallete();

		A.setEditable((byte)2);
		A.nextColors();

		System.out.println(A.toString());
	}
}