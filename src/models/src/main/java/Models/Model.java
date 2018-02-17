/*****************************************************
 * Date: 2/16/2018
 * iEatWhat-Backend
 * Summary: This class, model, will serve as a base
 * class for the model, something which other models
 * can inherit from.
 *****************************************************/

package Models;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Date createdOn;
    @Column
    private Date lastUpdated;


    public Model() {
        this.id = 0;
        this.createdOn = new Date();
        this.lastUpdated = createdOn;
    }

    Model(int uniqeID) {
        this.id = uniqeID;
        this.createdOn = new Date();
        this.lastUpdated = createdOn;
    }

    public void update() {
        this.lastUpdated = new Date();
    }
}
