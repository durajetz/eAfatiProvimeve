package com.example.eafatiprovimeve;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AfatiProvimeveDao_Impl implements AfatiProvimeveDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AfatiProvimeve> __insertionAdapterOfAfatiProvimeve;

  private final EntityDeletionOrUpdateAdapter<AfatiProvimeve> __deletionAdapterOfAfatiProvimeve;

  private final EntityDeletionOrUpdateAdapter<AfatiProvimeve> __updateAdapterOfAfatiProvimeve;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllAfatiProvimeve;

  public AfatiProvimeveDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAfatiProvimeve = new EntityInsertionAdapter<AfatiProvimeve>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `afati_provimeve` (`id`,`name`,`viti`,`semestri`,`dita`,`diferenca`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AfatiProvimeve value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getViti());
        stmt.bindLong(4, value.getSemestri());
        if (value.getDita() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDita());
        }
        stmt.bindLong(6, value.getDiferenca());
      }
    };
    this.__deletionAdapterOfAfatiProvimeve = new EntityDeletionOrUpdateAdapter<AfatiProvimeve>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `afati_provimeve` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AfatiProvimeve value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfAfatiProvimeve = new EntityDeletionOrUpdateAdapter<AfatiProvimeve>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `afati_provimeve` SET `id` = ?,`name` = ?,`viti` = ?,`semestri` = ?,`dita` = ?,`diferenca` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AfatiProvimeve value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getViti());
        stmt.bindLong(4, value.getSemestri());
        if (value.getDita() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDita());
        }
        stmt.bindLong(6, value.getDiferenca());
        stmt.bindLong(7, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllAfatiProvimeve = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM afati_provimeve";
        return _query;
      }
    };
  }

  @Override
  public void insert(final AfatiProvimeve afatiProvimeve) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAfatiProvimeve.insert(afatiProvimeve);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final AfatiProvimeve afatiProvimeve) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAfatiProvimeve.handle(afatiProvimeve);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final AfatiProvimeve afatiProvimeve) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAfatiProvimeve.handle(afatiProvimeve);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllAfatiProvimeve() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllAfatiProvimeve.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllAfatiProvimeve.release(_stmt);
    }
  }

  @Override
  public LiveData<List<AfatiProvimeve>> getAllProvimet() {
    final String _sql = "SELECT * FROM afati_provimeve ORDER BY diferenca ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"afati_provimeve"}, false, new Callable<List<AfatiProvimeve>>() {
      @Override
      public List<AfatiProvimeve> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfViti = CursorUtil.getColumnIndexOrThrow(_cursor, "viti");
          final int _cursorIndexOfSemestri = CursorUtil.getColumnIndexOrThrow(_cursor, "semestri");
          final int _cursorIndexOfDita = CursorUtil.getColumnIndexOrThrow(_cursor, "dita");
          final int _cursorIndexOfDiferenca = CursorUtil.getColumnIndexOrThrow(_cursor, "diferenca");
          final List<AfatiProvimeve> _result = new ArrayList<AfatiProvimeve>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AfatiProvimeve _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpViti;
            _tmpViti = _cursor.getInt(_cursorIndexOfViti);
            final int _tmpSemestri;
            _tmpSemestri = _cursor.getInt(_cursorIndexOfSemestri);
            final String _tmpDita;
            if (_cursor.isNull(_cursorIndexOfDita)) {
              _tmpDita = null;
            } else {
              _tmpDita = _cursor.getString(_cursorIndexOfDita);
            }
            final int _tmpDiferenca;
            _tmpDiferenca = _cursor.getInt(_cursorIndexOfDiferenca);
            _item = new AfatiProvimeve(_tmpName,_tmpViti,_tmpSemestri,_tmpDita,_tmpDiferenca);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
