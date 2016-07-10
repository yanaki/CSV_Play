package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSV_Data_Manipulator implements Iterable<ArrayList<String>> {
	private static final String INCORRECT_INDEX_ERROR = "Incorrect index!";
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

	public ArrayList<ArrayList<String>> getData() {
		return data; // TODO: Check why this works without this?
	}

	public void addLine(String line) {
		ArrayList<String> lineToAdd = new ArrayList<String>(Arrays.asList(line.split("\\p{Blank}+")));
		data.add(lineToAdd);
	}

	public ArrayList<String> getLine(int line) {
		return data.get(line);
	}

	public void createValue(int numberLineIndex, int numberPositionIndex, String value) {
		if (numberLineIndex < data.size()) {
			if (numberPositionIndex < data.get(numberLineIndex).size()) {
				data.get(numberLineIndex).add(numberPositionIndex, value);
			}
		} else {
			data.addAll(Stream.generate(() -> {
				return new ArrayList<String>(Arrays.asList(""));
			}).limit(numberLineIndex - data.size() + 1).collect(Collectors.toList()));
			data.get(numberLineIndex)
					.addAll(Stream.generate(() -> " ").limit(numberPositionIndex).collect(Collectors.toList()));
			data.get(numberLineIndex).add(value);
		}
	}

	public void updateValue(int numberLineIndex, int numberPositionIndex, String value) {
		try {
			data.get(numberLineIndex).set(numberPositionIndex, value);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
		}
	}

	public String getValue(int numberLineIndex, int numberPositionIndex) {
		try {
			return data.get(numberLineIndex).get(numberPositionIndex);
		} catch (IndexOutOfBoundsException e) {
			return INCORRECT_INDEX_ERROR;
		}
	}

	public void removeValue(int numberLineIndex, int numberPositionIndex) {
		try {
			data.get(numberLineIndex).remove(numberPositionIndex);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
		}
	}

	public void switchLines(int numberLineIndex1, int numberLineIndex2) {
		try {
			data.set(numberLineIndex2, data.set(numberLineIndex1, data.get(numberLineIndex2)));
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
		}
	}

	public void switchNumber(int numberLineIndex1, int numberPositionIndex1, int numberLineIndex2,
			int numberPositionIndex2) {
		String numberBuffer = "";
		try {
			numberBuffer = data.get(numberLineIndex1).set(numberLineIndex1,
					data.get(numberLineIndex2).get(numberLineIndex2));
			data.get(numberLineIndex2).set(numberLineIndex2, numberBuffer);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
		}
	}

	public int linesCount() {
		return data.size();
	}

	public ArrayList<String> getLinesForSaving() { // TODO: add parameter to
													// conserve original
													// whitespace symbol?
		ArrayList<String> result = new ArrayList<String>(this.linesCount());

		for (ArrayList<String> line : this.data) {
			result.add(String.join(" ", line)); // TODO: Use some java const
												// Character. ?
		}

		return result;
	}

	@Override
	public Iterator<ArrayList<String>> iterator() {
		return data.iterator();
	}

	@Override
	public String toString() {
		return String.join(System.lineSeparator(), this.getLinesForSaving());
	}
}
