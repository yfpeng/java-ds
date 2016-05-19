package com.pengyifan.commons.lang;

import static com.google.common.base.Preconditions.checkArgument;

public final class IndentStringFormatter {

  private int indent;
  private int width;
  private boolean splitAtWhiteSpaces;
  private boolean isHangIndent;

  private IndentStringFormatter() {
    indent = 2;
    width = 80;
    splitAtWhiteSpaces = true;
    isHangIndent = false;
  }

  /**
   * Returns a new format.
   * <p>
   * Indent is 2, width is 80, splitWithSpaces is true, hang indent is false
   */
  public static IndentStringFormatter newFormat() {
    return new IndentStringFormatter();
  }

  /**
   * Indent indentation. indent >= 0
   */
  public IndentStringFormatter withIndent(int indent) {
    checkArgument(indent >= 0, "Indent should not be negative: %s", indent);
    this.indent = indent;
    return this;
  }

  /**
   * The width of formatted paragraph
   */
  public IndentStringFormatter withWidth(int width) {
    checkArgument(
        width > 0,
        "text width should not be negative or zero: %s",
        width);
    this.width = width;
    return this;
  }

  /**
   * Set true if only split at white spaces.
   */
  public IndentStringFormatter withSplitAtWhiteSpaces(boolean splitAtWhiteSpaces) {
    this.splitAtWhiteSpaces = splitAtWhiteSpaces;
    return this;
  }

  /**
   * If true, format a paragraph to that has all lines but the first indented.
   * Otherwise, format a paragraph to that has the first indented.
   */
  public IndentStringFormatter withHangIndent(boolean isHangIndent) {
    this.isHangIndent = isHangIndent;
    return this;
  }

  public String format(String text) {
    checkArgument(indent < width,
        "indent should not be less than width: indent=%s, width=%s",
        indent, width);
    if (isHangIndent) {
      return hangIndent(text);
    } else {
      return indent(text);
    }
  }

  private String hangIndent(String text) {
    StringBuilder sb = new StringBuilder(text.substring(0, indent));
    // Needed to handle last line correctly.
    // Will be trimmed at last
    text = text.substring(indent) + "\n";
    // hang indent
    String spaces = org.apache.commons.lang3.StringUtils
        .repeat(' ', indent);
    String replacement = spaces + "$1\n";
    String regex = "(.{1," + (width - indent) + "})";
    if (splitAtWhiteSpaces) {
      regex += "\\s+";
    }
    text = text.replaceAll(regex, replacement);
    // remove first spaces and last "\n"
    text = text.substring(indent, text.length() - 1);
    if (!splitAtWhiteSpaces) {
      text = text.substring(0, text.length() - 1);
    }
    return sb.append(text).toString();
  }

  private String indent(String text) {
    String spaces = org.apache.commons.lang3.StringUtils.repeat(' ', indent);

    // Needed to handle last line correctly.
    // Will be trimmed at last
    text = spaces + text + "\n";
    // split
    String replacement = "$1\n";
    String regex = "(.{1," + width + "})";
    if (splitAtWhiteSpaces) {
      regex += "\\s+";
    }
    text = text.replaceAll(regex, replacement);
    // remove first spaces and last "\n"
    text = text.substring(0, text.length() - 1);
    if (!splitAtWhiteSpaces) {
      text = text.substring(0, text.length() - 1);
    }
    return text;
  }

}
