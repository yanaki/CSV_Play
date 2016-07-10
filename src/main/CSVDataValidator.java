package main;

import java.util.ArrayList;

public class CSVDataValidator {
	private final static String UNEXPECTED_CHARACTER_IN_NUMBER_ERROR = "line: %d, numberIndex: %d, character is not allowed!%s"
			+ System.lineSeparator();
	private final static String UNEXPECTED_START_LINE_CHARACTER_ERROR = "line: %d, shouldn't start with whitespace!"
			+ System.lineSeparator();
	private final static String STARTS_WITH_ZERRO_ERROR = "Number starts with 0!";
	private final static String CONTAINS_CHARACTER_ERROR = "Contains character error";

	private CSVDataManipulator data;
	
	public CSVDataValidator(CSVDataManipulator data) {
		this.data = data;
	}

	public String getErrors() {
		StringBuffer result = new StringBuffer();
		int numberLine = 1;

		for (ArrayList<String> line : this.data) {
			int numberPosition = 1;
			
			for (String number : line) {

				if (number.isEmpty()) {
					result.append(String.format(UNEXPECTED_START_LINE_CHARACTER_ERROR, numberLine));
					continue;
				}
				if (number.charAt(0) == '0') {
					result.append(String.format(UNEXPECTED_CHARACTER_IN_NUMBER_ERROR, numberLine, numberPosition, STARTS_WITH_ZERRO_ERROR));
				}
				
				if (!number.matches("\\d+")) {
					result.append(String.format(UNEXPECTED_CHARACTER_IN_NUMBER_ERROR, numberLine, numberPosition, CONTAINS_CHARACTER_ERROR));
				}				
				
				numberPosition++;
			}
			numberLine++;
		}

		return result.toString();
	}
}
