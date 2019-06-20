package nl.hanze.kantine.markdown;

public final class MarkdownStringGenerator {
    private static final String TABLE_PIPE = "|";
    private static final String TABLE_DASHES = "---";

    private MarkdownStringGenerator() {}

    /**
     * Generates a markdown version of a title.
     * @param text Text of the title.
     * @param size Size of the title.
     * @return Returns a string version of the markdown title.
     */
    public static String generateTitle(MarkdownString text, TitleSize size) {
        String prefix = new String(new char[size.ordinal() + 1]).replace("\0", "#");
        return prefix + " " + text;
    }

    /**
     * Generates a markdown version of a horizontal line.
     * @return Returns a string version of the markdown horizontal line.
     */
    public static String generateHorizontalLine() {
        return "------";
    }

    /**
     * Generates a markdown version of a list.
     * @param list The items presented in the list.
     * @return Returns a string version of the markdown list.
     */
    public static String generateList(MarkdownString[] list) {
        String markdown = "";

        for (int i = 0; i < list.length; i++) {
            if(list[i].getListIndent() == ListIndent.NONE)
                list[i].setListIndent(ListIndent.I1);

            //Make sure the first line does not have an extra newline
            if(i != 0)
                markdown += "\n";

            markdown += list[i];
        }

        return markdown;
    }

    /**
     * Generates a markdown version of a link.
     * @param title Title of the link.
     * @param url Url of the link.
     * @param hoverTitle Text for mouse hover of the link.
     * @return Returns a string version of the markdown link.
     */
    public static String generateLink(MarkdownString title, String url, MarkdownString hoverTitle) {
        String markdown = "[" + title + "](" + url + " \"" + hoverTitle + "\")";

        return markdown;
    }

    /**
     * Generates a markdown version of a table.
     * @param columnTitles The column titles.
     * @param content The rows of content presented.
     * @return Returns a string version of the markdown table.
     */
    public static String generateTable(MarkdownString[] columnTitles, MarkdownString[][] content) {
        return generateTableHead(columnTitles) + generateTableContent(content);
    }

    /**
     * Generates a markdown version of a table header.
     * @param columnTitles The titles of the columns.
     * @return Returns a string version of the markdown table head.
     */
    public static String generateTableHead(MarkdownString[] columnTitles) {
        String markdown = "";

        //Generate titles
        for (MarkdownString columnTitle:columnTitles) {
            markdown += TABLE_PIPE + columnTitle;
        }

        markdown += "\n";

        //Generate seperating line
        for (int i = 0; i < columnTitles.length; i++) {
            markdown += TABLE_PIPE + TABLE_DASHES;
        }

        return markdown;
    }

    /**
     * Generates a markdown version the table content.
     * @param content The content presented in the table
     * @return Returns a string version of the markdown table content.
     */
    public static String generateTableContent(MarkdownString[][] content) {
        String markdown = "";

        for(int i = 0; i < content.length; i++) {
            markdown += "\n";

            for(int j = 0; j < content[i].length; j++) {
                markdown +=  TABLE_PIPE + content[i][j];
            }
        }

        return markdown;
    }
    public static String generateTableContent(MarkdownString[] content) {
        return generateTableContent(new MarkdownString[][] { content });
    }


    /**
     * Generates a markdown version of a code block.
     * @param lines The lines presented in the code block.
     * @return Returns a string version of the markdown code block.
     */
    public static String generateCodeBlock(MarkdownString[] lines) {
        String markdown = "```\n";

        for (MarkdownString line:lines) {
            markdown += line + "\n";
        }

        markdown += "```";

        return markdown;
    }
}
