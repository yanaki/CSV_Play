package main;

import java.io.IOException;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;

public class CSVCommandLineInterface {
	private static final String LOAD_FILE_FIRST_MESSAGE = "Load file first! use lf path";
	private CSVDataValidator validator;
	private CSVFile csvFile;
	private CSVDataManipulator csvDataManipulator = new CSVDataManipulator();
	private boolean canExecute = false;

	@Command
	public String loadFile(@Param(name = "Path") String fileName) throws IOException {
		csvFile = new CSVFile(fileName);
		csvDataManipulator = csvFile.getCSVDataManipulator();
		validator = new CSVDataValidator(csvDataManipulator);
		canExecute = true;

		return "File loaded!";
	}

	@Command(description = "Provide the number of the lines to be switched(1 based)")
	public String switchLines(@Param(name = "Line 1") int line1, @Param(name = "Line 2") int line2) {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}
		csvDataManipulator.switchLines(line1 - 1, line2 - 1);
		return "Lines switched!";
	}

	@Command(description = "Provide the lines and the relevant positions in the line of the numbers to be switched(1 based)")
	public String switchNumbers(@Param(name = "Line 1") int line1, @Param(name = "Position 1") int position1,
			@Param(name = "Line 2") int line2, @Param(name = "Position 2") int position2) {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}
		csvDataManipulator.switchNumber(line1 - 1, position1 - 1, line2 - 1, position2 - 1);
		return "Numbers switched!";
	}

	@Command
	public String printCurrentData() {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}
		return "" + csvDataManipulator;
	}

	@Command
	public String showErrors() {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}

		String errors = validator.getErrors();
		return errors.isEmpty() ? "No errors found" : errors;
	}

	@Command
	public String saveFile() throws IOException {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}

		String errors = validator.getErrors();
		if (errors.isEmpty()) {
			csvFile.saveFile();
			return "File saved!";
		}
		return errors;
	}

	@Command(description = "Provide the line, the relevant position of the number and value to be added(1 based)")
	public String createNumber(@Param(name = "Line") int line, @Param(name = "Position") int position,
			@Param(name = "Value") String value) {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}
		csvDataManipulator.createNumber(line - 1, position - 1, value);
		return "Number created!";
	}

	@Command(description = "Provide the line and the relevant position of the number to be read(1 based)")
	public String readNumber(@Param(name = "Line") int line, @Param(name = "Position") int position) {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}

		return csvDataManipulator.readNumber(line - 1, position - 1);
	}

	@Command(description = "Provide the line, the relevant position of the number and the new value to be assigned(1 based)")
	public String updateNumber(@Param(name = "Line") int line, @Param(name = "Position") int position,
			@Param(name = "Value") String value) {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}

		return String.format("%s replaced by %s", csvDataManipulator.updateNumber(line - 1, position - 1, value), value);
	}

	@Command(description = "Provide the line and the relevant position of the number to be deleted(1 based)")
	public String deleteNumber(@Param(name = "Line") int line, @Param(name = "Position") int position) {
		if (!canExecute) {
			return LOAD_FILE_FIRST_MESSAGE;
		}

		return csvDataManipulator.deleteNumber(line - 1, position - 1) + " Deleted!";
	}

	public static void main(String[] args) throws IOException {
		CSVCommandLineInterface cli = new CSVCommandLineInterface();

		try {
			ShellFactory.createConsoleShell("", "?List for available options", cli).commandLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
