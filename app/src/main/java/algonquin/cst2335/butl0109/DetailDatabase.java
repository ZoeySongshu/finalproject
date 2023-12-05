package algonquin.cst2335.butl0109;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Detail.class}, version=1)
public abstract class DetailDatabase extends RoomDatabase {
    public abstract DetailDAO detailDAO();
}
