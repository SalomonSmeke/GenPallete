public class GenPallete {

	public static void main(String[] args) {

		LWGenNeighbors N = new LWGenNeighbors();
		String[] NB = new String[2];
		NB[0] = "1CA6B0";
		NB[1] = "09879E";
		N.setNeighbors(NB);
		
		N.nextColors();
		
		System.out.println(N.toString());
		
		LWGenPallete P = new LWGenPallete();
		String PB = "D91348";
		P.setBase(PB);
		
		P.nextColorsR();

		System.out.println(P.toString());
		
		LWGenBridge B = new LWGenBridge();
		String[] BB = new String[2];
		B.setSteps((byte) 3);
		BB[0] = "E5ABCD";
		BB[1] = "19E417";
		B.setBases(BB);
		
		B.nextColors();
		
		System.out.println(B.toString());
	}
}