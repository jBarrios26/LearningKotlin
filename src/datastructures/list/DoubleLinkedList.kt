package datastructures.list

data class DoubleNode<T>(var content:T?, var previous: DoubleNode<T>?, var next: DoubleNode<T>?){
    companion object EmptyNode{
        fun <T> createEmptyNode(): DoubleNode<T> {
            return DoubleNode(null,null,null)
        }
    }

    fun hasNext():Boolean = next != null

    fun hasPrevious():Boolean = previous != null

    fun isEmpty():Boolean = (previous == null) && (next == null) && (content == null)

    override fun toString(): String {
        return content.toString()
    }
}


class DoubleLinkedList<T>():MutableIterable<DoubleNode<T>> {

    private var head: DoubleNode<T> = DoubleNode.createEmptyNode()
    private var tail: DoubleNode<T> = DoubleNode.createEmptyNode()
    private var size = 0;

    fun isEmpty():Boolean = size == 0

    fun size():Int = size

    fun addFirst(data:T){
        if (head.isEmpty()){
            val newNode = DoubleNode<T>(data,null,null)
            head = newNode
            tail = head
        }else{
            val newNode = head.copy(content= data, next = head, previous = null)
            head.previous = newNode
            head = newNode
        }
        size++
    }

    fun addLast(data:T){
        if (size == 0)
            addFirst(data)
        else{
            val newNode = tail.copy(content = data, previous = tail)
            tail.next = newNode
            tail = newNode
        }
        size++
    }

    fun add(data:T, index:Int){
        if (index == (size-1) || index == 0)
            addInExtremes(data,index)
        else{
            val previousNode: DoubleNode<T> = moveThroughList(index)
            val newNode = DoubleNode.createEmptyNode<T>().copy(content = data,next = previousNode, previous = previousNode.previous)
            previousNode.previous!!.next = newNode
            size++
        }
    }

    private fun addInExtremes(data: T, index: Int) {
        if(index == 0)
            addFirst(data)
        else
            addLast(data)
    }

    private fun moveThroughList(index: Int): DoubleNode<T> {

        var count = 0
        var iteratorNode = move(head, count, index)

        if (!iteratorNode.isEmpty())
            return iteratorNode
        else
            throw IndexOutOfBoundsException()
    }

    private fun move(initialNode: DoubleNode<T>, counter: Int, index: Int): DoubleNode<T> {
        var iteratorNode = initialNode
        var count = counter
        while (iteratorNode.hasNext()) {
            if (count++ == index)
                return iteratorNode
            iteratorNode = iteratorNode.next!!
        }
        return DoubleNode.createEmptyNode()
    }

    fun add(data:T) = addLast(data)

    fun addAll(index:Int, elements:Collection<T>) {
        elements.forEachIndexed{elementIndex:Int, element:T -> add(element, index + elementIndex) }
    }

    fun clear(){
        head = DoubleNode.createEmptyNode()
        tail = head
    }

    fun contains(o:T):Boolean{
        var auxNode = head.copy()
        while (auxNode.hasNext()) {
            if (auxNode.content == o)
                return true
        }
        throw NoSuchElementException()
    }

    fun element():T{
        if(head.isEmpty())
            throw NoSuchElementException()
        return head.content!!
    }

    fun get(index:Int):T?{
        if (index >= size || index < 0)
           throw IndexOutOfBoundsException()
        else {
            var auxNode = head.copy()
            move(auxNode, 0, index)
            return auxNode.content
        }
    }

    fun set(data:T, index: Int):Boolean{
        var nodeToBeSet = move(head,0,index)
        return if (!nodeToBeSet.isEmpty()) {
            nodeToBeSet.content = data
            true
        } else {
            false
        }
    }

    fun removeFirst():T?{
        if(head.isEmpty())
            return null
        else if (size == 1){
            return removeFirstOneElement()
        }
        else{
            return removeFirstMultipleElements()
        }
    }

    fun removeFirstOneElement():T?{
        size--
        val oldHead = head.copy()
        head = DoubleNode.createEmptyNode()
        tail = DoubleNode.createEmptyNode()
        return oldHead.content!!
    }

    fun removeFirstMultipleElements():T?{
        size--
        val oldHead = head.copy()
        head = head.next!!
        head.previous = null
        return oldHead.content!!
    }

    fun removeLast():T?{
        if(head.isEmpty() || size == 1)
            return removeEspecialCase()
        else{
            val oldTail = tail.copy()
            tail = oldTail.previous!!
            tail.next = null
            return oldTail.content!!
        }
    }

    fun removeEspecialCase():T?{
        if(isEmpty())
            return null
        else
            return removeFirst()
    }

    fun remove(index:Int):T?{
        if(isEmpty() || size == 1)
            return removeEspecialCase()
        else if (index < size)
            return removeNthNode(index)

        throw IndexOutOfBoundsException()
    }

    private fun removeNthNode(index:Int):T?{
        size--
        val removedNode =  move(head, 0, index)
        removedNode.previous!!.next = removedNode.next
        removedNode.next!!.previous = removedNode.previous
        return removedNode.content
    }


    override fun toString(): String {
        var msg:String = "head->"
        var i = 0
        var auxNode = head
        while (auxNode.hasNext()){
            msg += "$i:$auxNode ->"
            auxNode = auxNode.next!!
            i++
        }
        return "$msg + $i:$tail-> <-tail"
    }

    override fun iterator(): MutableIterator<DoubleNode<T>> {
       return DoubleListIterator(head)
    }

    inner class DoubleListIterator(var node:DoubleNode<T>):MutableIterator<DoubleNode<T>>{
        override fun hasNext(): Boolean {
            return node.hasNext()
        }

        override fun next(): DoubleNode<T> {
            if(node.hasNext())
                return node.next!!
            throw NoSuchElementException()
        }

        override fun remove() {
            node.previous = node.next
            node.next = node.previous
        }

    }
}

fun main(){
    val lista: DoubleLinkedList<Int> = DoubleLinkedList()
    for (i:Int in 0..10){
        lista.addFirst(i)
    }
    println(lista.toString())
    lista.add(69,2)
}

