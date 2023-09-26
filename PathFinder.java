/**
 * This class represents a program that finds a path from the entrance of the park to all treasure chambers
 * @author Tianyi Han
 */

/**
 * import commands for exceptions
 */
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * First I had to implement a stackADT using doubly linked list. Implementing all the stack operations were fairly simple
 * however when I got the pop(int k) I reached some problems with the references. This was solved by switching around all of
 * the getPrevious and getNext. When constructing the PathFinder class one notable issue came when implementing best chamber.
 * I tried to implement it using a combination of while loop and nested for loop which was not necessary at all. Since each
 * chamber will always have six sides we can hard code the for loop to run six times only. For the different conditions we can use
 * three for loops to check them differently and return null if none of the for loop returns anything. The path method was achieved
 * with the implementation of loop while loop. First count the number of treasure chamber that has already been found, then we break
 * out of the while loop if we have found all of the treasures. The next part needs to work with bestChamber. If bestChamber is not null
 * we will push it into the stack and mark it as pushed. Otherwise we will pop the top chamber and mark it as popped
 */

public class PathFinder {
    /**
     * A reference to an object of the provided class Map that represents the chambers of the Pyramid Falls National Park
     */
    private Map pyramidMap;

    /**
     * This contructor takes in an input file and stores in the instance variable pyramidMap. It also catches any exceptions that may occur
     * @param fileName
     */
    public PathFinder(String fileName){
        try {
            pyramidMap = new Map(fileName);
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException e){
            System.out.println("File error");
        } catch (Exception e){
            System.out.println("Error");
        }
    }

    /**
     * This method finds a path from the entrance to all the treasure chambers that can be reached by satisfying constraints
     * @return a DLStack that stores all the chambers visited in order
     */
    public DLStack<Chamber> path(){
        DLStack<Chamber> pathStack = new DLStack<>();
        Chamber entrance = pyramidMap.getEntrance();
        int nTreasure = pyramidMap.getNumTreasures();
        Chamber curChamber;
        Chamber c;
        Chamber poppedChamber;
        int nTreasureFound = 0;

        entrance.markStart();
        pathStack.push(entrance);
        entrance.markPushed();

        while (!(pathStack.isEmpty())){
            curChamber = (Chamber)pathStack.peek();
            c = bestChamber(curChamber);
            if(curChamber.isTreasure()) {
                nTreasureFound++;
            }
            if(curChamber.isTreasure() && nTreasure == nTreasureFound){
                break;
            }
            if(c != null){
                pathStack.push(c);
                c.markPushed();
            }
            else{
                poppedChamber =(Chamber)pathStack.pop();
                poppedChamber.markPopped();
            }

        }
        return pathStack;

    }

    /**
     * Accessor method to get the value of pyramidMap
     * @return value of pyramidMap
     */
    public Map getMap(){
        return pyramidMap;
    }

    /**
     * This method checks currentChambers neighbours for a lighted chamber, of a lighted chamber is found then currentChamber is dim
     * otherwise current chamber is not dim
     * @param currentChamber
     * @return true or false respectively
     */
    public boolean isDim(Chamber currentChamber){
        if(currentChamber != null && !(currentChamber.isSealed()) && !(currentChamber.isLighted())){
            for (int i = 0; i<6; i++){
                if(currentChamber.getNeighbour(i) != null && currentChamber.getNeighbour(i).isLighted()){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * This method selects the best chamber to move to from currentChamber by checking a variety of requirements
     * @param currentChamber
     * @return the best chamber to move to
     */
    public Chamber bestChamber(Chamber currentChamber){

        for (int i = 0; i < 6; i++){
            if(!(currentChamber.getNeighbour(i) == null) && !(currentChamber.getNeighbour(i).isMarked()) && currentChamber.getNeighbour(i).isTreasure()){
                return currentChamber.getNeighbour(i);
            }
        }

        for (int i = 0; i < 6; i++){
            if(!(currentChamber.getNeighbour(i) == null) && !(currentChamber.getNeighbour(i).isMarked()) && currentChamber.getNeighbour(i).isLighted()){
                return currentChamber.getNeighbour(i);
            }
        }

        for (int i = 0; i < 6; i++){
            if(!(currentChamber.getNeighbour(i) == null) && !(currentChamber.getNeighbour(i).isMarked()) && this.isDim(currentChamber.getNeighbour(i))){
                return currentChamber.getNeighbour(i);
            }
        }

        return null;
    }
}
