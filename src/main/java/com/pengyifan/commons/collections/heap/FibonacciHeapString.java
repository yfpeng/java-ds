package com.pengyifan.commons.collections.heap;

import com.pengyifan.commons.lang.StringUtils;

public final class FibonacciHeapString {

  private FibonacciHeapString() throws InstantiationException {
    throw new InstantiationException("This class is not for instantiation");
  }

  public static StringBuilder toString(FibonacciHeap heap, StringBuilder sb) {
    if (heap.minimum() == null) {
      return sb;
    }
    return toStringPreOrder(heap.minimum(), new StringBuilder());
  }

  private static StringBuilder toStringPreOrder(FibonacciHeap.Entry node,
      StringBuilder sb) {
    if (node != null) {
      for (FibonacciHeap.Entry cur : node.nodelist()) {
        String s = new String();
        for (FibonacciHeap.Entry p = cur.parent; p != null; p = p.parent) {
          s = StringUtils.BAR + " " + s;
        }
        if (cur.right != node) {
          s += StringUtils.MIDDLE + " ";
        } else {
          s += StringUtils.END + " ";
        }
        sb.append(s + cur + "\n");
        toStringPreOrder(cur.child, sb);
      }
    }
    return sb;
  }
}
