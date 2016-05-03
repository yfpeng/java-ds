package com.pengyifan.commons.collections.tree;

import java.util.List;

public final class TreeUtils {

  private TreeUtils() {

  }

  /**
   * returns the node of a tree which represents the lowest common ancestor of
   * nodes t1 and t2 dominated by root. If either t1 or t2 is not dominated by
   * root, returns null.
   * 
   * @param t1
   * @param t2
   */
  public static <E, T extends Tree<E, T>> T getLowestCommonAncestor(T t1, T t2) {
    List<T> t1Path = t1.getPathFromRoot();
    List<T> t2Path = t2.getPathFromRoot();
    if (t1Path.isEmpty() || t2Path.isEmpty()) {
      return null;
    }

    int min = Math.min(t1Path.size(), t2Path.size());
    T commonAncestor = null;
    for (int i = 0; i < min && t1Path.get(i).equals(t2Path.get(i)); ++i) {
      commonAncestor = t1Path.get(i);
    }

    return commonAncestor;
  }

  /**
   * Returns the positional index of the left edge of a tree <i>t</i> within a
   * given root, as defined by the size of the yield of all material preceding
   * <i>t</i>.
   */
  public static <E, T extends Tree<E, T>> int leftEdge(T t, T root) {
    int[] i = new int[]{0};
    if (leftEdge(t, root, i)) {
      return i[0];
    } else {
      throw new RuntimeException("Tree is not a descendant of root.");
    }
  }

  static <E, T extends Tree<E, T>> boolean leftEdge(T t, T t1, int[] i) {
    if (t == t1) {
      return true;
    } else if (t1.isLeaf()) {
      int j = t1.getLeaves().size(); // so that empties don't add size
      i[0] = i[0] + j;
      return false;
    } else {
      for (T kid : t1.children()) {
        if (leftEdge(t, kid, i)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * Returns the positional index of the right edge of a tree <i>t</i> within a
   * given root, as defined by the size of the yield of all material preceding
   * <i>t</i> plus all the material contained in <i>t</i>.
   */
  public static <E, T extends Tree<E, T>> int rightEdge(T t, T root) {
    int[] i = new int[]{root.getLeaves().size()};
    if (rightEdge(t, root, i)) {
      return i[0];
    } else {
      throw new RuntimeException("Tree is not a descendant of root.");
      // return root.yield().size() + 1;
    }
  }

  static <E, T extends Tree<E, T>> boolean rightEdge(T t, T t1, int[] i) {
    if (t == t1) {
      return true;
    } else if (t1.isLeaf()) {
      int j = t1.getLeaves().size(); // so that empties don't add size
      i[0] = i[0] - j;
      return false;
    } else {
      List<T> kids = t1.children();
      for (int j = kids.size() - 1; j >= 0; j--) {
        if (rightEdge(t, kids.get(j), i)) {
          return true;
        }
      }
      return false;
    }
  }
}
