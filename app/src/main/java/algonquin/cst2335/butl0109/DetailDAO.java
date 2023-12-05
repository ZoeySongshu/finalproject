package algonquin.cst2335.butl0109;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DetailDAO {

    @Insert
    public long insertDetail(Detail d);

    @Query("Select * from Detail")
    public List<Detail> getAllDetails();

    @Delete
    void deleteDetails(Detail d);

}
