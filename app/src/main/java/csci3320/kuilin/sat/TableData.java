package csci3320.kuilin.sat;

import android.provider.BaseColumns;

/**
 * Created by kuilin on 4/19/15.
 */
public class TableData {
    public TableData() {

    }
    public static abstract class TableInfo implements BaseColumns {
        public static final String USER_NAME = "user_name";
        public static final String USER_SCORE = "user_score";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "reg_info";
    }
}
