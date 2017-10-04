package com.example.android.moneymonitor.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by W10 on 9/3/2017.
 */

public final class PetContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "pets";

    public static final class PetEntry implements BaseColumns{

        public static final String TABLE_NAME = "pets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";
        public static final String COLUMN_PET_INDIVIDUAL_ID = "individual_id";
        public static final String COLUMN_PET_DATE = "date";
        public static final String COLUMN_PET_ID_TYPE = "user_id_type";

        public static final int I_OWE_YOU = 0;
        public static final int YOU_OWE_ME = 1;
        public static final int I_PAID_YOU = 2;
        public static final int YOU_PAID_ME = 3;

        public static final int ORIGINAL_ID = 1;
        public static final int PSEUDO_ID = 0;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);



        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static boolean isValidGender(int gender) {
            return (gender == I_OWE_YOU ||
                    gender == YOU_OWE_ME ||
                    gender == I_PAID_YOU ||
                    gender == YOU_PAID_ME);
        }
    }

}
