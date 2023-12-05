package algonquin.cst2335.butl0109;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


public interface DetailDAO {


    public long insertDetail(Detail d);


    public List<Detail> getAllDetails();


    void deleteDetails(Detail d);

}
