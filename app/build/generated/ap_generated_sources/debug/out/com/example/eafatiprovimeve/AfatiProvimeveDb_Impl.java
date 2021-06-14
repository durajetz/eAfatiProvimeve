package com.example.eafatiprovimeve;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AfatiProvimeveDb_Impl extends AfatiProvimeveDb {
  private volatile AfatiProvimeveDao _afatiProvimeveDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `afati_provimeve` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `viti` INTEGER NOT NULL, `semestri` INTEGER NOT NULL, `dita` TEXT, `diferenca` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '351457f4e60434dead75b63b6c2e5385')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `afati_provimeve`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsAfatiProvimeve = new HashMap<String, TableInfo.Column>(6);
        _columnsAfatiProvimeve.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAfatiProvimeve.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAfatiProvimeve.put("viti", new TableInfo.Column("viti", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAfatiProvimeve.put("semestri", new TableInfo.Column("semestri", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAfatiProvimeve.put("dita", new TableInfo.Column("dita", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAfatiProvimeve.put("diferenca", new TableInfo.Column("diferenca", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAfatiProvimeve = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAfatiProvimeve = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAfatiProvimeve = new TableInfo("afati_provimeve", _columnsAfatiProvimeve, _foreignKeysAfatiProvimeve, _indicesAfatiProvimeve);
        final TableInfo _existingAfatiProvimeve = TableInfo.read(_db, "afati_provimeve");
        if (! _infoAfatiProvimeve.equals(_existingAfatiProvimeve)) {
          return new RoomOpenHelper.ValidationResult(false, "afati_provimeve(com.example.eafatiprovimeve.AfatiProvimeve).\n"
                  + " Expected:\n" + _infoAfatiProvimeve + "\n"
                  + " Found:\n" + _existingAfatiProvimeve);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "351457f4e60434dead75b63b6c2e5385", "090463e6c496ef26dac3103c97709e69");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "afati_provimeve");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `afati_provimeve`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AfatiProvimeveDao.class, AfatiProvimeveDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public AfatiProvimeveDao afatiProvimeveDao() {
    if (_afatiProvimeveDao != null) {
      return _afatiProvimeveDao;
    } else {
      synchronized(this) {
        if(_afatiProvimeveDao == null) {
          _afatiProvimeveDao = new AfatiProvimeveDao_Impl(this);
        }
        return _afatiProvimeveDao;
      }
    }
  }
}
