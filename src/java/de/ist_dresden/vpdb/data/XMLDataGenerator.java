package de.ist_dresden.vpdb.data;

import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.regex.Pattern;

public class XMLDataGenerator {
	private Random zufall = new Random();
	private final Writer writer;

	public XMLDataGenerator (Writer writer) throws IOException {
		this.writer = writer;
	}

	public void closeTag(String tag, int indentLevel,
			boolean insertNewlineAfter) throws IOException {
		writeTag("/" + tag, indentLevel, insertNewlineAfter);
	}

	public void openTag(String tag, int indentLevel,
			boolean insertNewlineAfter) throws IOException {
		writeTag(tag, indentLevel, insertNewlineAfter);
	}

	private void writeTag(String tag, int indentLevel,
			boolean insertNewlineAfter) throws IOException {
		for (int i = 0; i < indentLevel; i++) {
			writer.write("\t");
		}
		writer.write("<");
		writer.write(tag);
		writer.write(">" + (insertNewlineAfter ? "\n" : ""));
	}

	public void writeTagAndContent(String tag, int indentLevel,
			String value) throws IOException {
		openTag(tag, indentLevel, false);
		value = convertSpecialChars(value);
		writer.write(value);
		closeTag(tag, 0, true);
	}

	public static String convertSpecialChars(final String value) {
		String result = new String(value);
		result = result.replaceAll(Pattern.quote("&"), "&amp;");
		result = result.replaceAll(Pattern.quote(">"), "&gt;");
		result = result.replaceAll(Pattern.quote("<"), "&lt;");
		result = result.replaceAll(Pattern.quote("\""), "&quot;");
		return result;
	}
	
	public <E> E getZufaellig(E[] werte) {
	  return werte[zufall.nextInt(werte.length)];
	}

	public void writeZufaellig(String tag, int indentLevel,
			String[] values) throws IOException {
		String zufallsWert = getZufaellig(values);
		writeTagAndContent(tag, indentLevel, zufallsWert);
	}

	public void close() throws IOException {
		writer.close();
	}

}
