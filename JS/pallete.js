	var previousColors; //LL Storing all previous color arrays.
	var previousSteps; //LL Storing all previous step amounts.

	var currentColors; //AL Storing current scheme.

	var currentBaseColor; //STR Storing the current HEX base.
	var currentSteps; //BYTE Containing current step amount;

	var changeableRGB; //BYTE Containing the color set to change r(1)g(2)b(3).
	var harsh; //FLOAT Containing the multiplier for how much currentBaseColor's valRGB will change.

	function LWGenPallete() {
	    previousColors = [];
	    previousSteps = [];
	    currentColors = null;
	    currentBaseColor = "ffffff";
	    currentSteps = 5;
	    changeableRGB = Math.round((Math.random() * 3) + 1);
	    harsh = 1;
	}

	LWGenPallete.prototype.toString = function() {
	    if (currentColors == null) return null;
	    var cat = "";
	    for (var i = 0; i < currentColors.length; i++) {
	        cat += currentColors[i] + '\n';
	    }
	    return cat;
	};

	LWGenPallete.prototype.calcIntervals = function(input) {
	    var out = [];
	    for (var i = 0; i < currentSteps; i++) {
	        out[i] = Math.round(input - ((input * harsh) / (currentSteps - 1) * i));
	    }
	    return out;
	};

	LWGenPallete.prototype.calcIntervalsR = function(input) {
	    var out = [];
	    var mod = 255 - input;

	    for (var i = 0; i < currentSteps; i++) {
	        out[i] = Math.round(input + ((mod * harsh) / (currentSteps - 1) * i));
	    }
	    return out;
	};

	LWGenPallete.prototype.setBase = function(HEX) {
	    HEX = HEX.trim();
	    if (HEX.length() != 6) return false;

	    currentBaseColor = HEX.toLowerCase();
	    changeableRGB = calcMost(currentBaseColor);
	    return true;
	};

	LWGenPallete.prototype.setBaseR = function(HEX) {
	    HEX = HEX.trim();
	    if (HEX.length() != 6) return false;

	    currentBaseColor = HEX.toLowerCase();
	    changeableRGB = calcLeast(currentBaseColor);
	    return true;
	};

	LWGenPallete.prototype.setSteps = function(steps) {
	    if (steps < 2) return false;
	    currentSteps = steps;
	    return true;
	};

	LWGenPallete.prototype.setEditable = function(RGB) {
	    if (RGB == 1 || RGB == 2 || RGB == 3) {
	        changeableRGB = RGB;
	        return true;
	    }
	    return false;
	};

	LWGenPallete.prototype.setHarshness = function(harshness) {
	    while (harshness > 1) harshness = harshness / 10;
	    harsh = harshness;
	    return true;
	};

	LWGenPallete.prototype.getBase = function() {
	    return currentBaseColor;
	};

	LWGenPallete.prototype.getSteps = function() {
	    return currentSteps;
	};

	LWGenPallete.prototype.getChangeable = function() {
	    return changeableRGB;
	};

	LWGenPallete.prototype.getHarshness = function() {
	    return harsh;
	};

	LWGenPallete.prototype.getColorsAt = function(index) {
	    if (previousColors.length < index || index < 0) return null;
	    return previousColors[index];
	};

	LWGenPallete.prototype.getColors = function() {
	    return currentColors;
	};

	function reorderHex(input, pullFront) {
	    var rgb = [];

	    for (var i = 0; i < 3; i++) {
	        if (i == pullFront - 1) {
	            rgb[0] = input.substring(i * 2, (i + 1) * 2);
	        }
	    }
	    for (var i = 0; i < 3; i++) {
	        if (i != pullFront - 1) {
	            rgb[rgb.length] = (input.substring(i * 2, (i + 1) * 2));
	        }
	    }
	    return rgb[0] + rgb[1] + rgb[2];
	}

	function returnHex(input, returnPos) {
	    if (returnPos == 1) {
	        return input;
	    }
	    var rgb = [];
	    var val = input.substring(0, 2);


	    for (var i = 1; i < 3; i++) {
	        rgb[rgb.length] = (input.substring(i * 2, (i + 1) * 2));
	    }

	    var cat = "";
	    if (returnPos == 2) {
	        cat = rgb[0] + val + rgb[1];
	    }
	    else {
	        cat = rgb[0] + rgb[1] + val;
	    }
	    return cat;
	}

	function calcMost(inColor) {
	    var rgbSplit = [];
	    var index = 0;
	    var big = 0;

	    for (var i = 0; i < 3; i++) {
	        rgbSplit[i] = getValuePerRGB(i + 1, inColor);
	        if (rgbSplit[i] > big) {
	            big = rgbSplit[i];
	            index = (i + 1);
	        }
	    }
	    return index;
	}

	function calcLeast(inColor) {
	    var rgbSplit = [];
	    var index = 0;
	    var small = 256;

	    for (var i = 0; i < 3; i++) {
	        rgbSplit[i] = getValuePerRGB(i + 1, inColor);
	        if (rgbSplit[i] < small) {
	            small = rgbSplit[i];
	            index = (i + 1);
	        }
	    }
	    return index;
	}

	function getValuePerRGB(index, inColorHex) {
	    var perRGBStr = inColorHex.substring((index - 1) * 2, (index) * 2);
	    return hexToDec(perRGBStr);
	}

	function decToHex(input) {
	    var out = input.toString(16);
	    while (out.length < 2) {
	        out = "0" + out;
	    }
	    return out;
	}

	function hexToDec(input) {
	    return parseInt(input, 16);
	}
	
	//TODO finish translation

	public class LWGenPallete {

	    public String[] nextColors() {
	        if (currentColors != null) previousColors.add(currentColors);

	        String tempColor = reorderHex(currentBaseColor, changeableRGB);
	        short[] intervals = calcIntervals(hexToDec(tempColor.substring(0, 2)));

	        currentColors = new String[currentSteps];

	        for (int i = 0; i < currentSteps; i++) {
	            currentColors[i] = decToHex(intervals[i]) + tempColor.substring(2, 6);
	            currentColors[i] = returnHex(currentColors[i], changeableRGB);
	        }

	        previousSteps.add(currentSteps);
	        return currentColors;
	    }

	    public String[] nextColorsR() {
	        if (currentColors != null) previousColors.add(currentColors);

	        String tempColor = reorderHex(currentBaseColor, changeableRGB);
	        short[] intervals = calcIntervalsR(hexToDec(tempColor.substring(0, 2)));

	        currentColors = new String[currentSteps];

	        for (int i = 0; i < currentSteps; i++) {
	            currentColors[i] = decToHex(intervals[i]) + tempColor.substring(2, 6);
	            currentColors[i] = returnHex(currentColors[i], changeableRGB);
	        }

	        previousSteps.add(currentSteps);
	        return currentColors;
	    }

	    public String[] nextPurification() {
	        if (currentColors != null) previousColors.add(currentColors);

	        currentColors = new String[currentSteps];

	        String tempColor = reorderHex(currentBaseColor, changeableRGB);
	        short[] intervals1 = calcIntervals(hexToDec(tempColor.substring(2, 4)));
	        short[] intervals2 = calcIntervals(hexToDec(tempColor.substring(4, 6)));

	        for (int i = 0; i < currentSteps; i++) {
	            currentColors[i] = tempColor.substring(0, 2) + decToHex(intervals1[i]) + decToHex(intervals2[i]);
	            currentColors[i] = returnHex(currentColors[i], changeableRGB);
	        }

	        previousSteps.add(currentSteps);
	        return currentColors;
	    }

	    public String[] nextShades() {
	        if (currentColors != null) previousColors.add(currentColors);

	        currentColors = new String[currentSteps];

	        short[] intervals0 = calcIntervals(hexToDec(currentBaseColor.substring(0, 2)));
	        short[] intervals1 = calcIntervals(hexToDec(currentBaseColor.substring(2, 4)));
	        short[] intervals2 = calcIntervals(hexToDec(currentBaseColor.substring(4, 6)));

	        for (int i = 0; i < currentSteps; i++) currentColors[i] = decToHex(intervals0[i]) + decToHex(intervals1[i]) + decToHex(intervals2[i]);

	        previousSteps.add(currentSteps);
	        return currentColors;
	    }

	}