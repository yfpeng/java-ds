package com.pengyifan.commons.math;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.linear.RealMatrix;

import Jama.Matrix;

public final class MatrixUtils2 {

  private MatrixUtils2() throws InstantiationException {
    throw new InstantiationException("This class is not for instantiation");    
  }

  public static String printInOneline(String[][] matrix) {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = 0; i < matrix.length; i++) {
      sb.append('[');
      for (int j = 0; j < matrix[i].length; j++) {
        sb.append(matrix[i][j]);
        if (j < matrix[i].length - 1) {
          sb.append(", ");
        }
      }
      if (i < matrix.length - 1) {
        sb.append("], ");
      }
    }
    sb.append(']');
    return sb.toString();
  }

  public static String printSparseInOneline(RealMatrix matrix) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < matrix.getRowDimension(); i++) {
      for (int j = 0; j < matrix.getColumnDimension(); j++) {
        if (matrix.getEntry(i, j) != 0.0) {
          sb.append(String.format(
              "(%d, %d, %.2f) ",
              i,
              j,
              matrix.getEntry(i, j)));
        }
      }
    }
    return sb.toString();
  }

  public static String print(Matrix m) {
    return print(m, 4, 2);
  }

  /**
   * 
   * @param m Matrix
   * @param w Column width.
   * @param d Number of digits after the decimal.
   * @return string of the matrix
   */
  public static String print(Matrix m, int w, int d) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    m.print(pw, w, d);
    return StringUtils.strip(sw.toString(), System.lineSeparator());
  }

  public static String print(RealMatrix m) {
    return print(m, 4, 2);
  }

  /**
   * 
   * @param m Matrix
   * @param w Column width.
   * @param d Number of digits after the decimal.
   */
  public static String print(RealMatrix m, int w, int d) {
    DecimalFormat format = new DecimalFormat();
    format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
    format.setMinimumIntegerDigits(1);
    format.setMaximumFractionDigits(d);
    format.setMinimumFractionDigits(d);
    format.setGroupingUsed(false);
    return print(m, format, w + 2);
  }

  public static String print(RealMatrix m, NumberFormat format, int width) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < m.getRowDimension(); i++) {
      for (int j = 0; j < m.getColumnDimension(); j++) {
        String s = format.format(m.getEntry(i, j)); // format the number
        int padding = Math.max(1, width - s.length()); // At _least_ 1 space
        sb.append(StringUtils.repeat(' ', padding));
        sb.append(s);
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}
