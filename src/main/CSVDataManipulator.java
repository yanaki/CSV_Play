package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVDataManipulator implements Iterable<ArrayList<String>> {
	private static final String INCORRECT_INDEX_ERROR = "Incorrect index!";
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

	public ArrayList<ArrayList<String>> getData() {
		return data;
	}

	public void addLine(String line) {
		ArrayList<String> lineToAdd = new ArrayList<String>(Arrays.asList(line.split("\\p{Blank}+")));
		data.add(lineToAdd);
	}

	public void createNumber(int numberLineIndex, int numberPositionIndex, String value) {
		if (numberLineIndex >= data.size()) {
			data.addAll(Stream.generate(() -> {
				return new ArrayList<String>(Arrays.asList(" "));
			}).limit(numberLineIndex - data.size()).collect(Collectors.toList()));
			data.add(new ArrayList<String>());
		} 
		if (numberPositionIndex >= data.get(numberLineIndex).size()) {
			data.get(numberLineIndex)
					.addAll(Stream.generate(() -> " ").limit(numberPositionIndex - data.get(numberLineIndex).size()).collect(Collectors.toList()));
		}
		data.get(numberLineIndex).add(numberPositionIndex, value);
	}

	public String readNumber(int numberLineIndex, int numberPositionIndex) {
		try {
			return data.get(numberLineIndex).get(numberPositionIndex);
		} catch (IndexOutOfBoundsException e) {
			return INCORRECT_INDEX_ERROR;
		}
	}

	public String updateNumber(int numberLineIndex, int numberPositionIndex, String value) {
		try {
			return data.get(numberLineIndex).set(numberPositionIndex, value);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
			throw e;
		}
	}

	public String deleteNumber(int numberLineIndex, int numberPositionIndex) {
		try {
			return data.get(numberLineIndex).remove(numberPositionIndex);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
			throw e;
		}
	}

	public void switchLines(int numberLineIndex1, int numberLineIndex2) {
		try {
			data.set(numberLineIndex2, data.set(numberLineIndex1, data.get(numberLineIndex2)));
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
			throw e;
		}
	}

	public void switchNumber(int numberLineIndex1, int numberPositionIndex1, int numberLineIndex2,
			int numberPositionIndex2) {
		String numberBuffer = "";
		try {
			numberBuffer = data.get(numberLineIndex1).set(numberPositionIndex1,
					data.get(numberLineIndex2).get(numberPositionIndex2));
			data.get(numberLineIndex2).set(numberPositionIndex2, numberBuffer);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(INCORRECT_INDEX_ERROR);
			throw e;
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
