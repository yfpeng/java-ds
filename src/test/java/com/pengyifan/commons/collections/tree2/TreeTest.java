package com.pengyifan.commons.collections.tree2;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TreeTest {

  private final StringTree a = new StringTree("A");
  private final StringTree b = new StringTree("B");
  private final StringTree c = new StringTree("C");
  private final StringTree d = new StringTree("D");
  private final StringTree e = new StringTree("E");
  private final StringTree f = new StringTree("F");

  @Before
  public void setUp() {
    // A (B (C F) D E)
    a.add(b);
    a.add(d);
    a.add(e);

    b.add(c);
    b.add(f);
  }

  @Test
  public void testAddIntTreeNode() {
    StringTree dst = a.deepCopy(new StringTreeFactory());
    dst.add(0, new StringTree("G"));
    assertEquals("AGBCFDE", toString(dst.preorderIterator()));
  }

  @Test
  public void testAddTreeNode() {
    StringTree dst = a.deepCopy(new StringTreeFactory());
    dst.add(new StringTree("G"));
    assertEquals("ABCFDEG", toString(dst.preorderIterator()));
  }

  @Test
  public void testBreadthFirstIterator() {
    assertEquals("ABDECF", toString(a.breadthFirstIterator()));
  }

  @Test
  public void testChildrenIterator() {
    assertEquals("BDE", toString(a.childrenIterator()));
  }

  @Test
  public void testDeepCopy() {
    StringTree dst = a.deepCopy(new StringTreeFactory());
    assertEquals(
        "deep copy is incorrect",
        toString(a.postorderIterator()),
        toString(dst.postorderIterator()));
  }

  @Test
  public void testGetChild() {
    assertEquals(d, a.getChild(1));
  }

  @Test
  public void testGetChildAfter() {
    assertEquals(f, b.getChildAfter(c));
    assertNull(b.getChildAfter(f));
  }

  @Test
  public void testGetChildBefore() {
    assertEquals(c, b.getChildBefore(f));
    assertNull(b.getChildBefore(c));
  }

  @Test
  public void testGetChildCount() {
    assertEquals(3, a.getChildCount());
  }

  @Test
  public void testGetDepth() {
    assertEquals(2, a.getDepth());
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetFirstChild() {
    assertEquals(c, b.getFirstChild());
    c.getFirstChild();
  }

  @Test
  public void testGetFirstLeaf() {
    assertEquals(c, a.getFirstLeaf());
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetLastChild() {
    assertEquals(e, a.getLastChild());
    f.getLastChild();
  }

  @Test
  public void testGetLastLeaf() {
    assertEquals(e, a.getLastLeaf());
  }

  @Test
  public void testGetLevel() {
    assertEquals(0, a.getLevel());
    assertEquals(2, c.getLevel());
  }

  @Test
  public void testGetNextSibling() {
    assertEquals(f, c.getNextSibling());
    assertNull(f.getNextSibling());
  }

  @Test
  public void testGetObject() {
    assertEquals("B", b.getObject());
  }

  @Test
  public void testGetParent() {
    assertEquals(a, b.getParent());
    assertNull(a.getParent());
  }

  @Test
  public void testGetPathFromRoot() {
    assertEquals("ABF", toString(f.getPathFromRoot().iterator()));
  }

  @Test
  public void testGetPathToRoot() {
    assertEquals("FBA", toString(f.getPathToRoot().iterator()));
  }

  @Test
  public void testHasNextSiblingNode() {
    assertTrue(c.hasNextSiblingNode());
    assertFalse(f.hasNextSiblingNode());
  }

  @Test(expected = NullPointerException.class)
  public void testIndexOf() {
    assertEquals(2, a.indexOf(e));
    assertEquals(-1, a.indexOf(c));
    a.indexOf(null);
  }

  @Test
  public void testIsLeaf() {
    assertTrue(c.isLeaf());
    assertFalse(a.isLeaf());
  }

  @Test
  public void testIsNodeAncestor() {
    assertFalse(b.isNodeAncestor(c));
    assertTrue(c.isNodeAncestor(a));
  }

  @Test
  public void testIsNodeChild() {
    assertTrue(b.isNodeChild(c));
    assertFalse(c.isNodeChild(a));
  }

  @Test
  public void testIsNodeSibling() {
    assertFalse(c.isNodeSibling(e));
    assertTrue(b.isNodeSibling(e));
  }

  @Test
  public void testIsRoot() {
    assertEquals(true, a.isRoot());
    assertFalse(c.isRoot());
  }

  @Test
  public void testIterator() {
    assertEquals("ABCFDE", toString(a.iterator()));
  }

  @Test
  public void testLeavesIterator() {
    assertEquals("CFDE", toString(a.leavesIterator()));
  }

  @Test
  public void testPostorderIterator() {
    assertEquals("CFBDEA", toString(a.postorderIterator()));
  }

  @Test
  public void testPreorderIterator() {
    assertEquals("ABCFDE", toString(a.preorderIterator()));
  }

  @Test
  public void testRemoveInt() {
    StringTree dst = a.deepCopy(new StringTreeFactory());
    dst.remove(1);
    assertEquals("ABCFE", toString(dst.preorderIterator()));
  }

  @Test
  public void testRemoveTreeNode() {
    StringTree dst = a.deepCopy(new StringTreeFactory());
    dst.remove(dst.getChild(0));
    assertEquals("ADE", toString(dst.preorderIterator()));
  }

  @Test
  public void testReversal() {
    StringTree dst = a.deepCopy(new StringTreeFactory());
    dst.reversal();
    assertEquals("AEDBFC", toString(dst.preorderIterator()));
  }

  @Test
  public void testToString() {
    assertEquals("B", b.toString());
  }

  String toString(Iterator<StringTree> itr) {
    String str = "";
    while (itr.hasNext()) {
      str += (itr.next().getObject());
    }
    return str;
  }

  class StringTree extends Tree<String, StringTree> {
    StringTree(String s) {
      setObject(s);
    }
  }

  class StringTreeFactory implements TreeFactory<String, StringTree> {

    @Override
    public StringTree newTree(String s) {
      return new StringTree(s);
    }
  }
}