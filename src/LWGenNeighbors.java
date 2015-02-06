import java.util.LinkedList;

//NOT CHANGED FROM BRIDGE

public class LWGenNeighbors {
	private LinkedList<String[]> previousColors; //LL Storing all previous color arrays.

	private String [] currentColors;//AL Storing current scheme.

	private String currentNeighbor1;//STR Storing the current HEX1 base.
	private String currentNeighbor2;//STR Storing the current HEX2 base.

	public LWGenNeighbors(){reset();}

	@Override 
	public String toString(){
		if (currentColors==null)return null;
		String cat = "";
		for (int i = 0; i < currentColors.length; i++)cat+=currentColors[i]+'\n';

		return cat;
	}

	public void reset(){
		previousColors = new LinkedList<String[]>();
		currentColors = null;
		currentNeighbor1 = "aa56ff";
		currentNeighbor2 = "ba59dd";
	}

	public String[] nextColors(){ 
		if (currentColors != null)previousColors.add(currentColors);

		if(currentNeighbor1.equals(currentNeighbor2))return null;
		
		float stepsR = (getValuePerRGB(1,currentNeighbor1)-getValuePerRGB(1,currentNeighbor2));
		float stepsG = (getValuePerRGB(2,currentNeighbor1)-getValuePerRGB(2,currentNeighbor2));
		float stepsB = (getValuePerRGB(3,currentNeighbor1)-getValuePerRGB(3,currentNeighbor2));
		
		int baseR = getValuePerRGB(1,currentNeighbor1);
		int baseG = getValuePerRGB(2,currentNeighbor1);
		int baseB = getValuePerRGB(3,currentNeighbor1);
		
		short [] testValues = new short [3];
		
		LinkedList<String> colors = new LinkedList<String>();
		
		String temp = "";
		
		int i = 0;
		while (true) {
			testValues[0] = (short) (baseR-(stepsR*i));
			testValues[1] = (short) (baseG-(stepsG*i));
			testValues[2] = (short) (baseB-(stepsB*i));
			if (isValid(testValues)){
				for(int s = 0; s < 3; s++){
					temp = temp+decToHex(testValues[s]);
				}
				colors.add(0, temp);
				i++;
				temp = "";
			} else {
				break;
			}
		}
		
		i = 1;
		while (true) {
			testValues[0] = (short) (baseR+(stepsR*i));
			testValues[1] = (short) (baseG+(stepsG*i));
			testValues[2] = (short) (baseB+(stepsB*i));
			if (isValid(testValues)){
				for(int s = 0; s < 3; s++){
					temp = temp+decToHex(testValues[s]);
				}
				colors.add(temp);
				i++;
				temp = "";
			} else {
				break;
			}
		}

		currentColors = colors.toArray(new String[colors.size()]);
		
		return currentColors;
	}

	public boolean setNeighbors(String[] HEX){ 
		if (HEX.length!=2)return false;
		HEX[0] = HEX[0].trim();
		HEX[1] = HEX[1].trim();
		if (HEX[0].length()!=6 || HEX[1].length()!=6)return false;

		try
		{
			@SuppressWarnings("unused")
			int value1 = Integer.parseInt(HEX[0], 16); 
			@SuppressWarnings("unused")
			int value2 = Integer.parseInt(HEX[1], 16);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		currentNeighbor1 = HEX[0].toLowerCase();
		currentNeighbor2 = HEX[1].toLowerCase();
		return true;
	}

	public String[] getBase(){ 
		String[] out = new String[2];
		out[0]=currentNeighbor1;
		out[1]=currentNeighbor2;
		return out;
	}
	public byte getSteps(){return (byte) currentColors.length;} 
	public String[] getColorsAt(int index){ 
		if (previousColors.size()<index || index < 0)return null;
		return previousColors.get(index);
	}
	public String[] getColors(){return currentColors;} 
	private short getValuePerRGB(int index, String inColorHex){
		String perRGBStr = inColorHex.substring((index-1)*2, (index)*2);
		return (short)hexToDec(perRGBStr);
	}

	private short hexToDec(String hexadecimal){
		int d = Integer.valueOf(hexadecimal, 16);
		return (short)d;
	}
	private String decToHex(int input){
		String out = Integer.toHexString(input);
		while (out.length()<2) out = "0"+out;
		return out;
	}

	private boolean isValid(short [] check){
		for (int i = 0; i<3; i++) if (check[i]>255 || check[i]<0) return false;
		return true;
	}
}