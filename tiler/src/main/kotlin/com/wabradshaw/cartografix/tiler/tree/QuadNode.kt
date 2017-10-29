package com.wabradshaw.cartografix.tiler.tree

class QuadNode(topLeft: QuadNode?, topRight: QuadNode?, bottomLeft: QuadNode?, bottomRight: QuadNode?) {

    val size: Int
    var isComplete: Boolean = false

    var parent: QuadNode? = null
    var content: String? = null

    private val children: Array<QuadNode?> = arrayOfNulls(4)

    init {
        setTopLeft(topLeft)
        setTopRight(topRight)
        setBottomLeft(bottomLeft)
        setBottomRight(bottomRight)

        val existingChildren = children.filterNotNull()
        if(existingChildren.isEmpty()){
            isComplete = true
            size = 1
        } else {
            if (existingChildren.map{x -> x.size}.distinct().size > 1){
                throw IllegalArgumentException ("A QuadNode was built with nodes of different sizes." +
                        " Children are: $children")
            }
            this.size = existingChildren.map{x -> x.size}.max()!!*2
        }
    }

    /**
     * Gets the top left child node inside this node.
     *
     * @return The QuadNode in the top left
     */
    fun getTopLeft(): QuadNode? {
        return children[0]
    }

    /**
     * Gets the top right child node inside this node.
     *
     * @return The QuadNode in the top right
     */
    fun getTopRight(): QuadNode? {
        return children[1]
    }

    /**
     * Gets the bottom left child node inside this node.
     *
     * @return The QuadNode in the bottom left
     */
    fun getBottomLeft(): QuadNode? {
        return children[2]
    }

    /**
     * Gets the bottom right child node inside this node.
     *
     * @return The QuadNode in the bottom right
     */
    fun getBottomRight(): QuadNode? {
        return children[3]
    }

    /**
     * Sets the top left child node inside this node.
     *
     * @param content : The QuadNode in the top left
     */
    fun setTopLeft(content: QuadNode?) {
        setChild(0, content)
    }

    /**
     * Sets the top right child node inside this node.
     *
     * @param content : The QuadNode in the top right
     */
    fun setTopRight(content: QuadNode?) {
        setChild(1, content)
    }

    /**
     * Sets the bottom left child node inside this node.
     *
     * @param content : The QuadNode in the bottom left
     */
    fun setBottomLeft(content: QuadNode?) {
        setChild(2, content)
    }

    /**
     * Sets the bottom right child node inside this node.
     *
     * @param content : The QuadNode in the bottom right
     */
    fun setBottomRight(content: QuadNode?) {
        setChild(3, content)
    }

    /**
     * Private function that moves a QuadNode to a certain location in the list of children. This method is used to
     * make sure that the QuadNode has this node set as the parent, and that the QuadNode is removed from any other
     * trees. If the new node is displacing an old node, that node will lose its parent.
     */
    private fun setChild(location: Int, content: QuadNode?) {

        children[location]?.parent = null

        if (content != null) {
            if(this.size != 0 && content.size != this.size / 2){
                throw IllegalArgumentException("Nodes can only contain children half their size. " +
                        "A size $size node had a size ${content.size} child.")
            }
            content.parent?.removeChild(content)
            content.parent = this
        }

        children[location] = content

        isComplete = size == 1 || children.filterNotNull().size == 4
    }

    /**
     * Private function that removes the target child. If the node is not a child of this node, nothing will happen.
     *
     * Only one node needs to be removed as a node can only appear in the tree once.
     */
    private fun removeChild(content: QuadNode){
        val index = children.indexOf(content)
        if (index >= 0) {
            setChild(index, null)
        }
    }

    override fun toString(): String {
        return when (size) {
            1 -> "[]"
            else -> "[${getTopLeft()},${getTopRight()}|${getBottomLeft()},${getBottomRight()}]"
        }
    }
}
