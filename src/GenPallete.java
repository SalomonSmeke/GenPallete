
public class GenPallete {

	public static void main(String[] args) {
		LWGenPallete A = new LWGenPallete();

		A.setEditable((byte)3);
		A.nextColors();

		System.out.println(A.toString());
	}
}

