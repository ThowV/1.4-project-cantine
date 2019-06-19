package nl.hanze.kantine.markdown;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class MarkdownGenerator {
    private static final String OUTPUT_PREFIX = "MarkdownGenerator - ";
    private static final String DIRECTORY_PATH = "exports/";
    private static final String FILE_NAME = "markdown.md";

    private LinkedList<String> markdownCache = new LinkedList<String>();

    /**
     * Adds markdown to the markdown cache to be written.
     * @param markdown The markdown to be added to the cache
     */
    public void add(String markdown) { markdownCache.add(markdown); }
    public void add(MarkdownString markdown) { add(markdown.toString()); }

    /**
     * Clears the markdown cache
     */
    public void clear() { markdownCache.clear(); }

    /**
     * Starts the generation process.
     * Creates the folder and file if not present.
     * Starts the writing process.
     */
    public void generateMarkdown() {
        File directory = new File(DIRECTORY_PATH);
        File file = new File(DIRECTORY_PATH + FILE_NAME);

        try {
            boolean directoryCreated = directory.mkdirs();
            boolean fileCreated = file.createNewFile();

            writeToConsole("Exports directory creation: " + directoryCreated);
            writeToConsole("Markdown file creation: " + fileCreated);
        }
        catch(Exception e) {
            writeToConsole("Encountered the following error: " + e.getStackTrace().toString(), true);
            e.printStackTrace();
        }

        wirteMarkdown(file);
    }

    /**
     * Writes markdown from the cache to the markdown file.
     * @param file The file the markdown has to be written to.
     */
    private void wirteMarkdown(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            //Turn markdownCache to a complete string
            String markdown = "";
            for (String markdownString:markdownCache) {
                markdown += markdownString + "\n\n";
            }

            //Write our string to the specified file
            for (int i = 0; i < markdown.length(); i++)
                fileWriter.write(markdown.charAt(i));

            writeToConsole("Sucessfully written markdown");

            fileWriter.close();
        }
        catch(Exception e) {
            writeToConsole("Encountered the following error: " + e.getStackTrace().toString(), true);
            e.printStackTrace();
        }
    }

    /**
     * Writes messages to the console
     * in a uniform manner with a prefix.
     * @param output Message to be sent to the console.
     * @param error Is the message an error or not.
     */
    private static void writeToConsole(String output, boolean error) {
        output = OUTPUT_PREFIX + output;

        if(error)
            System.err.println(output);
        else
            System.out.println(output);
    }
    private static void writeToConsole(String output) {
        writeToConsole(output, false);
    }
}
