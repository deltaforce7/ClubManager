package delta7.clubmanager

import com.google.gson.Gson
import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.blob.CloudBlobContainer
import delta7.clubmanager.model.Announcement
import delta7.clubmanager.model.Club
import delta7.clubmanager.model.Model
import delta7.clubmanager.model.Person
import java.lang.Exception


class Repository {
    private val gson = Gson()

    fun getPerson(id: String): Response<Person> {
        return try {
            val container = getPersonContainer()
            val blob = container.getBlockBlobReference(id)

            if (blob.exists()) {
                Response.Success(gson.fromJson(blob.downloadText(), Person::class.java))
            } else {
                Response.NotExist()
            }

        } catch (e: Exception) {
            Response.Failure()
        }
    }

    fun createPerson(person: Person): Response<Person> {
        return try {
            val container = getPersonContainer()
            val blob = container.getBlockBlobReference(person.id)

            if (blob.exists()) {
                Response.AlreadyExist()
            } else {
                blob.uploadText(gson.toJson(person))
                Response.Success(person)
            }
        } catch (e: Exception) {
            Response.Failure()
        }
    }

    fun updatePerson(person: Person): Response<Person> {
        return try {
            val container = getPersonContainer()
            val blob = container.getBlockBlobReference(person.id)

            if (!blob.exists()) {
                Response.NotExist()
            } else {
                blob.uploadText(gson.toJson(person))
                Response.Success(person)
            }
        } catch (e: Exception) {
            Response.Failure()
        }
    }


    fun getClub(clubId: String): Response<Club> {
        return try {
            val blobContainer = getClubContainer()
            val blob = blobContainer.getBlockBlobReference(clubId)

            if (blob.exists()) {
                Response.Success(gson.fromJson(blob.downloadText(), Club::class.java))
            } else {
                Response.NotExist()
            }
        } catch (e: Exception) {
            Response.Failure()
        }
    }

    fun createClub(club: Club): Response<Club> {
        return try {
            val container = getClubContainer()
            val blob = container.getBlockBlobReference(club.roomId)

            if (blob.exists()) {
                Response.AlreadyExist()
            } else {
                blob.uploadText(gson.toJson(club))
                Response.Success(club)
            }
        } catch (e: Exception) {
            Response.Failure()
        }
    }

    fun updateClub(club: Club): Response<Club> {
        return try {
            val container = getClubContainer()
            val blob = container.getBlockBlobReference(club.roomId)

            if (!blob.exists()) {
                Response.NotExist()
            } else {
                blob.uploadText(gson.toJson(club))
                Response.Success(club)
            }
        } catch (e: Exception) {
            Response.Failure()
        }
    }

    fun updateAnnouncement(clubId: String, announcement: Announcement): Response<Club> {
        return try {
            val container = getClubContainer()
            val blob = container.getBlockBlobReference(clubId)
            val club = gson.fromJson(blob.downloadText(), Club::class.java)
            club.announcements.add(announcement)
            blob.uploadText(gson.toJson(club))
            Response.Success(club)
        } catch (e: Exception) {
            Response.Failure()
        }
    }

    private fun getPersonContainer(): CloudBlobContainer {
        val account = CloudStorageAccount.parse(Constant.StorageConnectionString)
        val blobClient = account.createCloudBlobClient()
        return blobClient.getContainerReference(Constant.StoragePersonContainer)
    }

    private fun getClubContainer(): CloudBlobContainer {
        val account = CloudStorageAccount.parse(Constant.StorageConnectionString)
        val blobClient = account.createCloudBlobClient()
        return blobClient.getContainerReference(Constant.StorageClubContainer)
    }
}

sealed class Response<T>(val data: T? = null) {
    class Success<T>(data: T): Response<T>(data)
    class Failure<T>: Response<T>()
    class NotExist<T>: Response<T>()
    class AlreadyExist<T>: Response<T>()
}
