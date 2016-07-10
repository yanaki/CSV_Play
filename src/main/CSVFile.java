package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CSVFile {
	private String fileName;
	private CSVDataManipulator csvDataManipulator = new CSVDataManipulator();

	protected CSVFile(String fileName) throws IOException {
		this.fileName = fileName;

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				this.csvDataManipulator.addLine(line);
			});
		}
	}

	public CSVDataManipulator getCSVDataManipulator() {
		return this.csvDataManipulator;
	}

	public void saveFile() throws IOException {
		Files.write(Paths.get(this.fileName), csvDataManipulator.getLinesForSaving());
	}
}
