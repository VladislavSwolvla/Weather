package startandroid.ru.weatherapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Item)
    @Query("SELECT * FROM city")
    fun getAllItem(): Flow<List<Item>>
    @Query("UPDATE city SET `temp` = :temp, text = :text WHERE name = :name")
    fun update(name: String, temp: String, text: String)
    @Query("SELECT * FROM city")
    fun getItems(): List<Item>

}