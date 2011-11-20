package analysis.tree;

/**
 *
 * @author Aloren
 */
public class MarkovLink {

    private String name;
    private Marking parent;
    private Marking child;

    public MarkovLink(String name, Marking parent, Marking child){
        this.name = name;
        this.parent = parent;
        this.child = child;
    }

    @Override
    public String toString(){
        //String addS = "["+parent.toString()+";"+child.toString()+"]";
        return name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the parent
     */
    public Marking getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Marking parent) {
        this.parent = parent;
    }

    /**
     * @return the child
     */
    public Marking getChild() {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(Marking child) {
        this.child = child;
    }

}
