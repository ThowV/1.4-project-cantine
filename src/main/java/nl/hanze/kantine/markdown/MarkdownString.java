package nl.hanze.kantine.markdown;

public class MarkdownString {
    private String string;
    private EmphasisType emphasisType;
    private ListIndent listIndent;

    /**
     * Constructor
     * @param string The string to be transformed into a MarkdownString
     */
    public MarkdownString(String string) {
        this.string = string;
        emphasisType = EmphasisType.STANDARD;
        listIndent = ListIndent.NONE;
    }

    /**
     * Turns the string into a bold string.
     * @return Returns a markdown version of the bold string.
     */
    public MarkdownString toBold() { return setEmphasisType(EmphasisType.BOLD); }

    /**
     * Turns the string into an italic string.
     * @return Returns a markdown version of the italic string.
     */
    public MarkdownString toItalic() { return setEmphasisType(EmphasisType.ITALIC); }

    /**
     * Turns the string into both a bold and an italic string.
     * @return Returns a markdown version of the italic bold string.
     */
    public MarkdownString totBoldItalic() { return setEmphasisType(EmphasisType.BOLD_ITALIC); }

    /**
     * Turns the string into a strikethrough string.
     * @return Returns a markdown version of the strikethrough string.
     */
    public MarkdownString toStrikeThrough() { return setEmphasisType(EmphasisType.STRIKE_THROUGH); }

    /**
     * Turns the string into a blockquote string.
     * @return Returns a markdown version of the blockquote string.
     */
    public MarkdownString toBlockquote() { return setEmphasisType(EmphasisType.BLOCKQUOTE); }

    /**
     * Turns the given list indent to a string.
     * @param listIndent The indent the string has to have in a list.
     * @return Returns a markdown version of the indent in a list.
     */
    private static String generateListIndent(ListIndent listIndent) {
        switch (listIndent) {
            case NONE:
                return "";
            case I1:
                return "* ";
            case I2:
                return "\t- ";
            case I3:
                return "\t\t+ ";
            case I4:
                return "\t\t\t* ";
            case I5:
                return "\t\t\t\t- ";
        }

        return "";
    }

    /**
     * Turns the given emphasis type to a string.
     * @param emphasisType The emphasis type the string has to be turned into.
     * @param string The string that has to be turned into one of the emphasis types.
     * @return Returns the string with the markdown emphasis.
     */
    private static String generateEmphasisType(EmphasisType emphasisType, String string) {
        switch (emphasisType) {
            case STANDARD:
                return string;
            case BOLD:
                return "**" + string + "**";
            case ITALIC:
                return "*" + string + "*";
            case BOLD_ITALIC:
                return "**_" + string + "_**";
            case STRIKE_THROUGH:
                return "~~" + string + "~~";
            case BLOCKQUOTE:
                return "> " + string;
        }

        return string;
    }

    /**
     * Sets the emphasis type.
     * @param emphasisType The emphasis type the markdown string should posses.
     * @return Returns a markdown string with the correct emphasis type.
     */
    public MarkdownString setEmphasisType(EmphasisType emphasisType) {
        this.emphasisType = emphasisType;
        return this;
    }

    /**
     * Sets the list indent.
     * @param listIndent The list indent the markdown string should posses.
     * @return Returns a markdown string with the correct list indent.
     */
    public MarkdownString setListIndent(ListIndent listIndent) {
        this.listIndent = listIndent;
        return this;
    }

    /**
     * @return Returns the list indent of the string.
     */
    public ListIndent getListIndent() {
        return this.listIndent;
    }

    public String toString() {
        return generateListIndent(listIndent) + generateEmphasisType(emphasisType, string);
    }
}
