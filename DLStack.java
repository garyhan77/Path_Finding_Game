/**
 * This class represents a stackADT implemented as a doubly linked list. Includes all operations the stack need for this assignment
 * @author Tianyi Han
 */
public class DLStack<T> implements DLStackADT<T>{
    /**
     * Reference to the node at the top of the stack
     */
    private DoubleLinkedNode<T> top;
    /**
     * number of data items stored in the stack
     */
    private int numItems;

    /**
     * Constructor to create an empty stack, top must be set to null and numItems to zero
     */
    public DLStack(){
        top = null;
        numItems = 0;
    }

    /**
     * This method represents a stacks push operation
     * @param dataItem element to be pushed onto stack
     */
    public void push(T dataItem){
        DoubleLinkedNode<T> newNode = new DoubleLinkedNode<>(dataItem);
        if(!(isEmpty())){
            newNode.setPrevious(top);
            top.setNext(newNode);
        }
        top = newNode;
        numItems++;
    }

    /**
     * This method represents a stacks pop operation, it will throw an exception if the stack is empty
     * @return top dataItem of the stack
     * @throws EmptyStackException
     */
    public T pop() throws EmptyStackException{
        if(isEmpty()){
            throw new EmptyStackException("The stack is empty");
        }

        DoubleLinkedNode<T> tempNode = top;
        top = top.getPrevious();
        if(!(isEmpty())){
            top.setNext(null);
        }
        numItems--;

        return tempNode.getElement();
    }

    /**
     * This is a special pop operation specific to the assignment
     * The method removes and returns the k-th data item from the top of the stack
     * A InvalidItemException will be thrown if k is out of bound
     * @param k
     * @return k-th data item from top of stack
     * @throws InvalidItemException
     */
    public T pop(int k) throws InvalidItemException{
        DoubleLinkedNode<T> curNode;
        DoubleLinkedNode<T> nextNode;
        DoubleLinkedNode<T> prevNode;

        curNode = top;

        if(isEmpty()){
            throw new InvalidItemException("Error, empty stack");
        }

        if(k < 1 || k > numItems){
            throw new InvalidItemException("K is not a valid value");
        }

        for (int i = 1; i < k; i++){
            curNode = curNode.getPrevious();
        }

        if (curNode == top){
            top = curNode.getPrevious();
        }
        else {

            if(curNode == null){
                return null;
            }
            nextNode = curNode.getNext();
            prevNode = curNode.getPrevious();

            if(prevNode != null){
                prevNode.setNext(nextNode);
                nextNode.setPrevious(prevNode);
            }
        }
        numItems--;
        return curNode.getElement();
    }

    /**
     * This method represents the peek operation of a stack. It will throw an exception if the stack is empty
     * @return top dataItem of stack
     * @throws EmptyStackException
     */
    public T peek() throws EmptyStackException{
        if(isEmpty()){
            throw new EmptyStackException("The stack is empty");
        }
        return top.getElement();
    }

    /**
     * This method will check if the stack is empty
     * @return true or false respectively
     */
    public boolean isEmpty(){
        if(top == null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method finds the size of the stack and returns it
     * @return size of stack
     */
    public int size(){
        return numItems;
    }

    /**
     * This method returns the data item at the top of the stack. It sort of like a peek operation
     * @return data item at the top of the stack
     */
    public DoubleLinkedNode<T> getTop(){
        return top;
    }

    /**
     * This toString method returns string representation of the stack in the form of "[data1 data2 ... datan]"
     * @return String representation of stack
     */
    public String toString() {
        DoubleLinkedNode<T> curNode = new DoubleLinkedNode<T>();
        String elemList = "";
        curNode = top;
        for (int i = 0; i < numItems; i++) {
            elemList += curNode.getElement() + " ";
            curNode = curNode.getNext();
        }
        elemList.trim();
        return "[" + elemList + "]";
    }
}
