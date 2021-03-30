package com.example.android.devbyteviewer.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class VideoDao_Impl implements VideoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDatabaseVideo;

  public VideoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDatabaseVideo = new EntityInsertionAdapter<DatabaseVideo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `DatabaseVideo`(`url`,`title`,`description`,`updated`,`thumbnail`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DatabaseVideo value) {
        if (value.getUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUrl());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getUpdated() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUpdated());
        }
        if (value.getThumbnail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getThumbnail());
        }
      }
    };
  }

  @Override
  public void insertAll(final List<DatabaseVideo> video) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDatabaseVideo.insert(video);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<DatabaseVideo>> getVideos() {
    final String _sql = "SELECT * FROM databasevideo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"databasevideo"}, false, new Callable<List<DatabaseVideo>>() {
      @Override
      public List<DatabaseVideo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "updated");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final List<DatabaseVideo> _result = new ArrayList<DatabaseVideo>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DatabaseVideo _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpUpdated;
            _tmpUpdated = _cursor.getString(_cursorIndexOfUpdated);
            final String _tmpThumbnail;
            _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            _item = new DatabaseVideo(_tmpUrl,_tmpTitle,_tmpDescription,_tmpUpdated,_tmpThumbnail);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
