package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHelper {
	// private StringBuilder problems = new StringBuilder();
	private String fileName;
	private CSV_Data_Manipulator csvData = new CSV_Data_Manipulator();

	public FileHelper(String fileName) throws IOException {
		this.fileName = fileName;

		Stream<String> stream = Files.lines(Paths.get(fileName));

		stream.forEach(line -> {
			this.csvData.addLine(line);
		});
		
		stream.close();
	}

	public CSV_Data_Manipulator getCSVData() {
		return this.csvData;
	}

	public void SaveFile(CSV_Data_Manipulator data) throws IOException {
		Files.write(Paths.get(this.fileName), data.getLinesForSaving());
	}
}
