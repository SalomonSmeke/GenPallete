import java.util.LinkedList;

public class LWGenBridge {
	private LinkedList<String[]> previousColors; //LL Storing all previous color arrays.
	private LinkedList<Byte> previousSteps;//LL Storing all previous step amounts.

	private String [] currentColors;//AL Storing current scheme.

	private String currentBaseColor1;//STR Storing the current HEX1 base.
	private String currentBaseColor2;//STR Storing the current HEX2 base.
	private byte currentSteps;//BYTE Containing current step amount;

	public LWGenBridge(){reset();}

	@Override 
	public String toString(){
		if (currentColors==null)return null;
		String cat = "";
		for (int i = 0; i < currentSteps+1; i++)cat+=currentColors[i]+'\n';
		
		return cat;
	}

	public void reset(){
		previousColors = new LinkedList<String[]>();
		previousSteps = new LinkedList<Byte>();
		currentColors = null;
		currentBaseColor1 = "ffffff";
		currentBaseColor2 = "000000";
		currentSteps = 5;
	}

	public String[] nextColors(){ 
		if (currentColors != null)previousColors.add(currentColors);
		
		String[] out = new String [currentSteps+1];
		
		float subtract1 = (getValuePerRGB(1,currentBaseColor1)-getValuePerRGB(1,currentBaseColor2))/currentSteps;
		float subtract2 = (getValuePerRGB(2,currentBaseColor1)-getValuePerRGB(2,currentBaseColor2))/currentSteps;
		float subtract3 = (getValuePerRGB(3,currentBaseColor1)-getValuePerRGB(3,currentBaseColor2))/currentSteps;
		
		int baseR = getValuePerRGB(1,currentBaseColor1);
		int baseG = getValuePerRGB(2,currentBaseColor1);
		int baseB = getValuePerRGB(3,currentBaseColor1);
		
		for (int i = 0; i < out.length; i++) {
			
			out[i] = decToHex((int)(baseR-subtract1*i));
			out[i] = out[i] + decToHex((int)(baseG-subtract2*i));
			out[i] = out[i] + decToHex((int)(baseB-subtract3*i));
		}
		
		currentColors = out;
		previousSteps.add(currentSteps);
		
		return currentColors;
	}
	
	public boolean setSteps(byte steps){ 
		if (steps<2)return false;
		currentSteps = (byte) (steps+1);
		return true;
	}
	public boolean setBases(String[] HEX){ 
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
		currentBaseColor1 = HEX[0].toLowerCase();
		currentBaseColor2 = HEX[1].toLowerCase();
		return true;
	}
	
	public String[] getBase(){ 
		String[] out = new String[2];
		out[0]=currentBaseColor1;
		out[1]=currentBaseColor2;
		return out;
	}
	public byte getSteps(){return currentSteps;} 
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
}