package model;

/**
 *
 * @author Aloren
 */
public class LinkWeight implements java.io.Serializable{
    
    private Place place;
    private int numberOfLinks;    
    
    public LinkWeight(Place place, int numberOfLinks){
        this.place = place;
        this.numberOfLinks = numberOfLinks;
    }

    /**
     * @return the place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * @return the numberOfLinks
     */
    public int getNumberOfLinks() {
        return numberOfLinks;
    }

    /**
     * @param numberOfLinks the numberOfLinks to set
     */
    public void setNumberOfLinks(int numberOfLinks) {
        this.numberOfLinks = numberOfLinks;
    }
    
    public void incNumberOfLinks(){
        this.numberOfLinks++;
    }
    
    public void decNumberOfLinks(){
        this.numberOfLinks--;
    }

}
