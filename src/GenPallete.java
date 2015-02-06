public class GenPallete {

	public static void main(String[] args) {

		LWGenNeighbors N = new LWGenNeighbors();
		String[] NB = new String[2];
		NB[0] = "";
		NB[1] = "";
		N.setNeighbors(NB);
		
		N.nextColors();
		
		System.out.println(N.toString());
		
		LWGenPallete P = new LWGenPallete();
		String PB = "";
		P.setBase(PB);
		
		P.nextColors();

		System.out.println(P.toString());
		
		LWGenBridge B = new LWGenBridge();
		String[] BB = new String[2];
		BB[0] = "";
		BB[1] = "";
		B.setBases(BB);
		
		B.nextColors();
		
		System.out.println(B.toString());
	}
}