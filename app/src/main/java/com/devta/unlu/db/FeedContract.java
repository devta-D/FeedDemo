package com.devta.unlu.db;

import android.provider.BaseColumns;

/**
 * Created on : Jun, 25, 2020 at 03:05
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class FeedContract {

    private FeedContract() {}

    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_EVENT_NAME = "name";
        public static final String COLUMN_NAME_EVENT_DATE = "date";
        public static final String COLUMN_NAME_EVENT_VIEWS = "views";
        public static final String COLUMN_NAME_EVENT_LIKES = "likes";
        public static final String COLUMN_NAME_EVENT_SHARES = "shares";
        public static final String COLUMN_NAME_PAGE_NUM = "page_num";
    }

}
