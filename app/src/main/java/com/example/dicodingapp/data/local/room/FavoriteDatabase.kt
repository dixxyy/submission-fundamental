package com.example.dicodingapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dicodingapp.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 3, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "event_database"
                )
                    .addMigrations(MIGRATION_2_3) // Tambahkan migrasi di sini
                    .fallbackToDestructiveMigration() // Ini akan menghapus database jika migrasi tidak tersedia
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Definisikan migrasi dari versi 2 ke 3
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Jika Anda menambahkan kolom baru, tambahkan di sini
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN beginTime TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN endTime TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN ownerName TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN imageLogo TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN summary TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN description TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN category TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN cityName TEXT")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN quota INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN registrants INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE favorite_event ADD COLUMN link TEXT")
            }
        }
    }
}
