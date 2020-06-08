package com.example.servicedesk.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.servicedesk.database.interfaces.TicketDao
import com.example.servicedesk.database.interfaces.UserDao
import com.example.servicedesk.model.Comment
import com.example.servicedesk.model.Ticket
import com.example.servicedesk.model.User
import com.example.servicedesk.model.UserTickets

@TypeConverters(Converters::class)
@Database(entities = [User::class,Ticket::class,Comment::class], version = 1, exportSchema = false)

abstract class ServiceDeskRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun ticketDao(): TicketDao

    companion object    {
        private const val DATABASE_NAME = "SERVICEDESK_DATABASE"

        @Volatile
        private var INSTANCE: ServiceDeskRoomDatabase? = null

        fun getDatabase(context: Context) : ServiceDeskRoomDatabase?   {
            if (INSTANCE == null)   {
                synchronized(ServiceDeskRoomDatabase::class.java)   {
                    if (INSTANCE == null)   {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ServiceDeskRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}