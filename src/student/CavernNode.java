package student;

/**
 * Created by Alexander Worton on 05/02/2017.
 */
public interface CavernNode {
    /**
     * used to determine if the final value of the nodes path value has been set
     * @return true if path value is golden, false otherwise
     */
    boolean isGoldenValue();

    /**
     * Getter for the node path value
     * @return path value
     */
    int getPathValue();

    /**
     * Setter for the node path value
     * @param pathValue
     */
    void setPathValue(int pathValue);

    /**
     * Getter for the node id
     * @return id
     */
    long getId();

    /**
     * Setter for the node id
     * @param pathValue
     */
    void setId(long pathValue);
}