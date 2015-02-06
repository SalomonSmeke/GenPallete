import java.util.LinkedList;
import java.util.Random;

public class LWGenPallete {
	private LinkedList<String[]> previousColors; //LL Storing all previous color arrays.
	private LinkedList<Byte> previousSteps;//LL Storing all previous step amounts.

	private String [] currentColors;//AL Storing current scheme.

	private String currentBaseColor;//STR Storing the current HEX base.
	private byte currentSteps;//BYTE Containing current step amount;

	private byte changeableRGB;//BYTE Containing the color set to change r(1)g(2)b(3).
	private float harsh;//FLOAT Containing the multiplier for how much currentBaseColor's valRGB will change.

	public LWGenPallete(){reset();}

	@Override 
	public String toString(){
		if (currentColors==null)return null;
		String cat = "";
		for (int i = 0; i < currentSteps; i++)cat+=currentColors[i]+'\n';
		
		return cat;
	}

	public void reset(){
		Random R = new Random();
		previousColors = new LinkedList<String[]>();
		previousSteps = new LinkedList<Byte>();
		currentColors = null;
		currentBaseColor = "ffffff";
		currentSteps = 5;
		changeableRGB = (byte)(R.nextInt(3)+1);
		harsh = 1;
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

	public boolean setBase(String HEX){
		HEX = HEX.trim();
		if (HEX.length()!=6)return false;
		try
		{
			@SuppressWarnings("unused")
			int value = Integer.parseInt(HEX, 16); 
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		currentBaseColor = HEX.toLowerCase();
		changeableRGB = calcMost(currentBaseColor);
		return true;
	}
	public boolean setSteps(byte steps){
		if (steps<2)return false;

		currentSteps = steps;
		return true;
	}
	public boolean setEditable(byte RGB){
		if (RGB == 1 || RGB == 2 || RGB == 3){
			changeableRGB = RGB;
			return true;
		}
		return false;
	}
	public boolean setHarshness(float harshness){
		while (harshness > 1)harshness = harshness/10;
		if (harshness < .2)return false;
		
		harsh = harshness;
		return true;
	}

	public String getBase(){return currentBaseColor;}
	public byte getSteps(){return currentSteps;}
	public byte getChangeable(){return changeableRGB;}
	public float getHarshness(){return harsh;}
	public String[] getColorsAt(int index){
		if (previousColors.size()<index || index < 0)return null;
		return previousColors.get(index);
	}
	public String[] getColors(){return currentColors;}

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

	private byte calcMost(String inColor) {
		Short [] rgbSplit = new Short [3];

		byte index = 0;
		int big = 0;

		for (short i = 0; i < rgbSplit.length; i++){
			rgbSplit [i] = getValuePerRGB(i+1, inColor);
			if (rgbSplit [i]>big){
				big = rgbSplit [i];
				index = (byte)(i+1);
			}
		}
		return index;
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