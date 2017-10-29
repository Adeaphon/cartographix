package com.wabradshaw.cartografix.tiler.tree

import org.junit.Assert
import org.junit.Test

/**
 * A set of tests for the QuadNode data structure.
 *
 * Needed to make sure that nodes are moved sensibly, and nulls can be handled appropriately.
 */
class QuadNodeTest {

    /**
     * Tests that a node with no child nodes can be built.
     */
    @Test
    fun testEmptyNode() {
        val root = QuadNode(null, null, null, null)
        Assert.assertEquals(1, root.size)
        Assert.assertNull(root.getTopLeft())
        Assert.assertNull(root.getTopRight())
        Assert.assertNull(root.getBottomLeft())
        Assert.assertNull(root.getBottomRight())
        Assert.assertTrue(root.isComplete)
        Assert.assertNull(root.parent)
    }

    /**
     * Tests that a node can have a single child in it (e.g. BR in a 3x3 space).
     */
    @Test
    fun testSingleNested() {
        val leaf = QuadNode(null, null, null, null)
        Assert.assertEquals(1, leaf.size)
        Assert.assertNull(leaf.getTopLeft())
        Assert.assertNull(leaf.getTopRight())
        Assert.assertNull(leaf.getBottomLeft())
        Assert.assertNull(leaf.getBottomRight())
        Assert.assertTrue(leaf.isComplete)
        Assert.assertNull(leaf.parent)

        val root = QuadNode(leaf, null, null, null)
        Assert.assertEquals(2, root.size)
        Assert.assertEquals(leaf, root.getTopLeft())
        Assert.assertNull(root.getTopRight())
        Assert.assertNull(root.getBottomLeft())
        Assert.assertNull(root.getBottomRight())
        Assert.assertFalse(root.isComplete)
        Assert.assertNull(root.parent)

        Assert.assertEquals(root, leaf.parent)
    }



    /**
     * Tests that a node can have 4 children in it.
     */
    @Test
    fun testFullyNested() {
        val leafTL = QuadNode(null, null, null, null)
        val leafTR = QuadNode(null, null, null, null)
        val leafBL = QuadNode(null, null, null, null)
        val leafBR = QuadNode(null, null, null, null)

        val root = QuadNode(leafTL, leafTR, leafBL, leafBR)
        Assert.assertEquals(2, root.size)
        Assert.assertEquals(leafTL, root.getTopLeft())
        Assert.assertEquals(leafTR, root.getTopRight())
        Assert.assertEquals(leafBL, root.getBottomLeft())
        Assert.assertEquals(leafBR, root.getBottomRight())
        Assert.assertTrue(root.isComplete)
        Assert.assertNull(root.parent)

        Assert.assertEquals(root, leafTL.parent)
        Assert.assertEquals(root, leafTR.parent)
        Assert.assertEquals(root, leafBL.parent)
        Assert.assertEquals(root, leafBR.parent)
    }

    /**
     * Tests that nodes can nest repeatedly.
     */
    @Test
    fun testGrandparent() {
        val leaf = QuadNode(null, null, null, null)

        val parent = QuadNode(leaf, null, null, null)
        Assert.assertNull(parent.parent)
        Assert.assertEquals(parent, leaf.parent)

        val root = QuadNode(parent, null, null, null)
        Assert.assertEquals(4, root.size)
        Assert.assertEquals(parent, root.getTopLeft())
        Assert.assertNull(root.getTopRight())
        Assert.assertNull(root.getBottomLeft())
        Assert.assertNull(root.getBottomRight())
        Assert.assertFalse(root.isComplete)
        Assert.assertNull(root.parent)

        Assert.assertEquals(root, parent.parent)
    }

    /**
     * Tests that a node can be moved from one node to another.
     */
    @Test
    fun testSwitchParent() {
        val leaf = QuadNode(null, null, null, null)

        val originalParent = QuadNode(leaf, null, null, null)
        Assert.assertEquals(leaf, originalParent.getTopLeft())
        Assert.assertEquals(originalParent, leaf.parent)

        val newParent = QuadNode(leaf, null, null, null)
        Assert.assertEquals(leaf, newParent.getTopLeft())
        Assert.assertEquals(newParent, leaf.parent)
        Assert.assertNull(originalParent.getTopLeft())
    }

    /**
     * Tests that when a node moves from a complete parent it will become incomplete.
     */
    @Test
    fun testSwitchFromComplete() {
        val leafTL = QuadNode(null, null, null, null)
        val leafTR = QuadNode(null, null, null, null)
        val leafBL = QuadNode(null, null, null, null)
        val leafBR = QuadNode(null, null, null, null)

        val originalParent = QuadNode(leafTL, leafTR, leafBL, leafBR)
        Assert.assertTrue(originalParent.isComplete)

        val newParent = QuadNode(leafTL, null, null, null)
        Assert.assertFalse(originalParent.isComplete)
        Assert.assertFalse(newParent.isComplete)
    }

    /**
     * Tests that when a node moves from a complete parent it will become incomplete.
     */
    @Test
    fun testSwitchToComplete() {
        val leafTL = QuadNode(null, null, null, null)
        val leafTR = QuadNode(null, null, null, null)
        val leafBL = QuadNode(null, null, null, null)
        val leafBR = QuadNode(null, null, null, null)

        val originalParent = QuadNode(leafTL, null, null, null)
        Assert.assertFalse(originalParent.isComplete)

        val newParent = QuadNode(null, leafTR, leafBL, leafBR)
        Assert.assertFalse(newParent.isComplete)

        newParent.setTopLeft(leafTL)
        Assert.assertTrue(newParent.isComplete)
    }

    /**
     * Tests that a node moved into a leaf node will error.
     */
    @Test(expected = IllegalArgumentException::class)
    fun testLeafIntoLeaf() {
        val leaf = QuadNode(null, null, null, null)
        val otherLeaf = QuadNode(null, null, null, null)

        otherLeaf.setBottomLeft(leaf)
    }

    /**
     * Tests that a leaf node moved into a node high up in the tree will error.
     */
    @Test(expected = IllegalArgumentException::class)
    fun testLeafIntoGrandparent() {
        val leaf = QuadNode(null, null, null, null)
        val parent = QuadNode(leaf, null, null, null)
        val grandparent = QuadNode(parent, null, null, null)

        val otherLeaf = QuadNode(null, null, null, null)
        grandparent.setBottomLeft(leaf)
    }

    /**
     * Tests that when a node moves from a complete parent it will become incomplete.
     */
    @Test
    fun testDisplace() {
        val leafTL = QuadNode(null, null, null, null)
        val leafTR = QuadNode(null, null, null, null)
        val leafBL = QuadNode(null, null, null, null)
        val leafBR = QuadNode(null, null, null, null)

        val parent = QuadNode(leafTL, leafTR, leafBL, leafBR)

        val newLeaf = QuadNode(null, null, null, null)

        parent.setBottomRight(newLeaf)
        Assert.assertNull(leafBR.parent)
        Assert.assertEquals(parent, newLeaf.parent)
        Assert.assertTrue(parent.isComplete)
    }

}