var previousColors; //LL Storing all previous color arrays.

var currentColors; //AL Storing current scheme.

var currentNeighbor1; //STR Storing the current HEX1 base.
var currentNeighbor2; //STR Storing the current HEX2 base.

function LWGenNeighbors() {
	previousColors = [];
	currentColors = null;
	currentNeighbor1 = "aa56ff";
	currentNeighbor2 = "ba59dd";
}
LWGenNeighbors.prototype.toString = function() {
	if (currentColors == null) return null;
	var cat = "";
	for (var i = 0; i < currentColors.length; i++) {
		cat += currentColors[i] + '\n';
	}
	return cat;
};

LWGenNeighbors.prototype.setBases = function(HEX) {
	if (HEX.length != 2) return false;
	HEX[0] = HEX[0].trim();
	HEX[1] = HEX[1].trim();
	if (HEX[0].length != 6 || HEX[1].length != 6) return false;
	currentNeighbor1 = HEX[0].toLowerCase();
	currentNeighbor2 = HEX[1].toLowerCase();
	return true;
};

LWGenNeighbors.prototype.getBase = function() {
	var out = [];
	out[0] = currentNeighbor1;
	out[1] = currentNeighbor2;
	return out;
};

LWGenNeighbors.prototype.getColorsAt = function(index) {
	if (previousColors.size() < index || index < 0) return null;
	return previousColors.get(index);
};

LWGenNeighbors.prototype.getSteps = function() {
	return currentColors.length;
};
LWGenNeighbors.prototype.getColors = function() {
	return currentColors;
};

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

function isValid(check) {
	for (var i = 0; i < 3; i++)
		if (check[i] > 255 || check[i] < 0) return false;
	return true;
}

LWGenNeighbors.prototype.nextColors = function() {
	var indx = previousColors.length;
	if (currentColors != null) previousColors[indx] = currentColors;

	if (currentNeighbor1 == currentNeighbor2) return null;

	var stepsR = (getValuePerRGB(1, currentNeighbor1) - getValuePerRGB(1, currentNeighbor2));
	var stepsG = (getValuePerRGB(2, currentNeighbor1) - getValuePerRGB(2, currentNeighbor2));
	var stepsB = (getValuePerRGB(3, currentNeighbor1) - getValuePerRGB(3, currentNeighbor2));

	var baseR = getValuePerRGB(1, currentNeighbor1);
	var baseG = getValuePerRGB(2, currentNeighbor1);
	var baseB = getValuePerRGB(3, currentNeighbor1);

	var testValues = [];

	var colors = [];

	var temp = "";

	var i = 0;
	while (true) {
		testValues[0] = Math.round(baseR - (stepsR * i));
		testValues[1] = Math.round(baseG - (stepsG * i));
		testValues[2] = Math.round(baseB - (stepsB * i));
		if (isValid(testValues)) {
			for (var s = 0; s < 3; s++) {
				temp = temp + decToHex(testValues[s]);
			}

			colors[colors.length] = temp;
			i++;
			temp = "";
		}
		else {
			break;
		}
	}

	i = 1;
	while (true) {
		testValues[0] = Math.round(baseR + (stepsR * i));
		testValues[1] = Math.round(baseG + (stepsG * i));
		testValues[2] = Math.round(baseB + (stepsB * i));
		if (isValid(testValues)) {
			for (var t = 0; t < 3; t++) {
				temp = temp + decToHex(testValues[t]);
			}
			colors[colors.length] = temp;
			i++;
			temp = "";
		}
		else {
			break;
		}
	}

	currentColors = colors;
	return currentColors;
};