	var previousColors;
	var previousSteps;
    
	var currentColors;//AL Storing current scheme.

	var currentBaseColor1;//STR Storing the current HEX1 base.
	var currentBaseColor2;//STR Storing the current HEX2 base.
	var currentSteps;//BYTE Containing current step amount;

function LWGenBridge(){
	    previousColors = [];
		previousSteps = [];
		currentColors = null;
		currentBaseColor1 = "ffffff";
		currentBaseColor2 = "000000";
		currentSteps = 5;
}

LWGenBridge.prototype.toString = function(){
		if (currentColors==null)return null;
		var cat = "";
		for (var i = 0; i < currentSteps+1; i++){
			cat+=currentColors[i]+'\n';
		}
		return cat;
}

LWGenBridge.prototype.nextColors = function(){ 
	var indx = previousColors.length;
	if (currentColors != null)previousColors[indx] = currentColors;

	var out = [];
		
	var subtract1 = (getValuePerRGB(1,currentBaseColor1)-getValuePerRGB(1,currentBaseColor2))/currentSteps;
	var subtract2 = (getValuePerRGB(2,currentBaseColor1)-getValuePerRGB(2,currentBaseColor2))/currentSteps;
	var subtract3 = (getValuePerRGB(3,currentBaseColor1)-getValuePerRGB(3,currentBaseColor2))/currentSteps;
		
	var baseR = getValuePerRGB(1,currentBaseColor1);
	var baseG = getValuePerRGB(2,currentBaseColor1);
	var baseB = getValuePerRGB(3,currentBaseColor1);
		
	for (var i = 0; i <= currentSteps; i++) {
		
		out[i] = decToHex(Math.round(baseR-subtract1*i));
		out[i] = out[i] + decToHex(Math.round(baseG-subtract2*i));
		out[i] = out[i] + decToHex(Math.round(baseB-subtract3*i));
	}
	
	currentColors = out;
	previousSteps[indx] = currentSteps;
	
	return currentColors;
}

LWGenBridge.prototype.setSteps = function(steps){ 
	if (steps<1)return false;
	currentSteps = (steps+1);
	return true;
}

LWGenBridge.prototype.setBases = function(HEX){ 
		if (HEX.length!=2)return false;
		HEX[0] = HEX[0].trim();
		HEX[1] = HEX[1].trim();
		if (HEX[0].length!=6 || HEX[1].length!=6)return false;
		currentBaseColor1 = HEX[0].toLowerCase();
		currentBaseColor2 = HEX[1].toLowerCase();
		return true;
}

LWGenBridge.prototype.getBase = function(){ 
	var out = [];
	out[0]=currentBaseColor1;
	out[1]=currentBaseColor2;
	return out;
}
LWGenBridge.prototype.getColorsAt = function(index){ 
	if (previousColors.size()<index || index < 0)return null;
	return previousColors.get(index);
}
LWGenBridge.prototype.getSteps = function(){return currentSteps;}
LWGenBridge.prototype.getColors = function(){return currentColors;} 

function getValuePerRGB(index,inColorHex){
	var perRGBStr = inColorHex.substring((index-1)*2, (index)*2);
	return hexToDec(perRGBStr);
}

function decToHex(input) {
	var out = input.toString(16);
	while (out.length<2) {
		out = "0"+out;
	}
	return out;
}
function hexToDec(input) {
	return parseInt(input,16);
}