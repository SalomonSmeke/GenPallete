import java.util.LinkedList;
import java.util.Random;

public class LWGenBridge {
	private LinkedList<String[]> previousColors; //LL Storing all previous color arrays.
	private LinkedList<Byte> previousSteps;//LL Storing all previous step amounts.

	private String [] currentColors;//AL Storing current scheme.

	private String currentBaseColor1;//STR Storing the current HEX1 base.
	private String currentBaseColor2;//STR Storing the current HEX2 base.
	private byte currentSteps;//BYTE Containing current step amount;

	public LWGenBridge(){reset();}

	@Override 
	public String toString(){ //Converted
		if (currentColors==null)return null;
		String cat = "";
		for (int i = 0; i < currentSteps; i++)cat+=currentColors[i]+'\n';
		
		return cat;
	}

	public void reset(){ //Converted
		previousColors = new LinkedList<String[]>();
		previousSteps = new LinkedList<Byte>();
		currentColors = null;
		currentBaseColor1 = "ffffff";
		currentBaseColor2 = "000000";
		currentSteps = 5;
	}

	public String[] nextColors(){
		if (currentColors != null)previousColors.add(currentColors);
		
		String tempColor = reorderHex(currentBaseColor,changeableRGB);
		short [] intervals = calcIntervals(hexToDec(tempColor.substring(0, 2)));

		currentColors = new String [currentSteps];

		for(int i = 0; i < currentSteps; i++){
			currentColors[i]= decToHex(intervals[i])+tempColor.substring(2, 6);
			currentColors[i] = returnHex(currentColors[i],changeableRGB);
		}
		
		previousSteps.add(currentSteps);
		return currentColors;
	}

	public String[] nextPurification(){ 
		if (currentColors != null)previousColors.add(currentColors);
		
		currentColors = new String[currentSteps];

		String tempColor = reorderHex(currentBaseColor,changeableRGB);
		short [] intervals1 = calcIntervals(hexToDec(tempColor.substring(2, 4)));
		short [] intervals2 = calcIntervals(hexToDec(tempColor.substring(4, 6)));

		for(int i = 0; i < currentSteps; i++){
			currentColors[i] = tempColor.substring(0, 2) + decToHex(intervals1[i]) + decToHex(intervals2[i]);
			currentColors[i] = returnHex(currentColors[i],changeableRGB);
		}

		previousSteps.add(currentSteps);
		return currentColors;
	}
	
	public String[] nextShades(){ 
		if (currentColors != null)previousColors.add(currentColors);
		
		currentColors = new String[currentSteps];

		short [] intervals0 = calcIntervals(hexToDec(currentBaseColor.substring(0, 2)));
		short [] intervals1 = calcIntervals(hexToDec(currentBaseColor.substring(2, 4)));
		short [] intervals2 = calcIntervals(hexToDec(currentBaseColor.substring(4, 6)));

		for(int i = 0; i < currentSteps; i++)currentColors[i] =  decToHex(intervals0[i]) + decToHex(intervals1[i]) + decToHex(intervals2[i]);

		previousSteps.add(currentSteps);
		return currentColors;
	}
	
	private short[] calcIntervals(short input){
		short [] out = new short[currentSteps];

		for (int i = 0; i < currentSteps; i++) out[i]=(short)(input-((input*harsh)/(currentSteps-1)*i));

		return out;
	}
	
	public boolean setSteps(byte steps){ //Converted
		if (steps<2)return false;
		currentSteps = (byte) (steps+1);
		return true;
	}
	
	public boolean setBases(String[] HEX){ //Converted
		if (HEX.length!=2)return false;
		HEX[0] = HEX[0].trim();
		HEX[1] = HEX[1].trim();
		if (HEX[1].length()!=6 || HEX[2].length()!=6)return false;
		
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
	
	public String[] getBase(){ //Converted
		String[] out = new String[2];
		out[0]=currentBaseColor1;
		out[1]=currentBaseColor2;
		return out;
	}
	
	public byte getSteps(){return currentSteps;} //Converted
	public String[] getColorsAt(int index){ //Converted
		if (previousColors.size()<index || index < 0)return null;
		return previousColors.get(index);
	}
	public String[] getColors(){return currentColors;} //Converted

	private String reorderHex(String in, int pullFront){
		LinkedList<String> rgb = new LinkedList<String>();

		for (short i = 0; i < 3; i++){
			if(i==pullFront-1){
				rgb.add(0, in.substring(i*2, (i+1)*2));
			} else {
				rgb.add(in.substring(i*2, (i+1)*2));
			}
		}
		return rgb.get(0)+rgb.get(1)+rgb.get(2);
	}

	private String returnHex(String in, int returnPos){
		LinkedList<String> rgb = new LinkedList<String>();
		String val = in.substring(0,2);

		for (short i = 1; i < 3; i++) rgb.add(in.substring(i*2, (i+1)*2));

		rgb.add(returnPos-1, val);
		return rgb.get(0)+rgb.get(1)+rgb.get(2);
	}

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