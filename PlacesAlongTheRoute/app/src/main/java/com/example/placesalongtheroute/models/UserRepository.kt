package com.example.placesalongtheroute.models

import com.example.placesalongtheroute.entityClasses.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val firestore = Firebase.firestore

    private val storageRef = FirebaseStorage.getInstance("gs://musicplayer-fd3fd.appspot.com").reference

    suspend fun createUser(user: User): String {
        try {
            val usersCollectionRef = firestore.collection("Users")
            val newUser = hashMapOf("name" to user.name, "email" to user.email, "password" to user.password, "mobileNumber" to user.mobileNumber, "searchLimit" to 0)
            val documentReference = usersCollectionRef.add(newUser).await()
            return documentReference.id
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun updateUserDetails(user: User, password: String): String {
        try {
            val usersCollectionRef = firestore.collection("Users")
            val documentReference = usersCollectionRef.document(user.userId) // Assuming you have a userId field in your User model

            val updatedData = hashMapOf(
                "name" to user.name,
                "email" to user.email,
                "password" to password,
                "mobileNumber" to user.mobileNumber,
                "searchLimit" to user.searchLimit
            )

            documentReference.set(updatedData, SetOptions.merge()).await()
            return "Password updated successfully"
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAllUsers(): List<User> {
        try {
            val usersList = mutableListOf<User>()
            val result = firestore.collection("Users").get().await()
            for (document in result) {
                val userId = document.id
                val name = document.getString("name") ?: ""
                val email = document.getString("email") ?: ""
                val password = document.getString("password") ?: ""
                val mobileNumber = document.getString("mobileNumber") ?: ""
                val searchLimit = document.getLong("searchLimit") ?: 0
                val user = User(userId, name, email, password, mobileNumber)
                if (email.isNotBlank() && password.isNotBlank())
                    usersList.add(User(userId, name, email, password, mobileNumber, searchLimit))
            }
            return usersList
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun incrementSearchLimit(userId: String): String {
        try {
            val usersCollectionRef = firestore.collection("Users")
            val documentReference = usersCollectionRef.document(userId)
            val userDoc = documentReference.get().await()

            val currentSearchLimit = userDoc.getLong("searchLimit") ?: 0
            val updatedSearchLimit = currentSearchLimit + 1

            val updatedData = hashMapOf(
                "searchLimit" to updatedSearchLimit
            )

            documentReference.set(updatedData, SetOptions.merge()).await()
            return "Search limit incremented successfully"
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun getSearchLimit(userId: String): Long {
        try {
            val documentReference = firestore.collection("Users").document(userId)
            val userDoc = documentReference.get().await()
            return userDoc.getLong("searchLimit") ?: 0
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun resetSearchLimit(userId: String): String {
        try {
            val usersCollectionRef = firestore.collection("Users")
            val documentReference = usersCollectionRef.document(userId)

            val updatedData = hashMapOf(
                "searchLimit" to 0
            )

            documentReference.set(updatedData, SetOptions.merge()).await()
            return "Search limit reset successfully"
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun addSearchHistory(userId: String, origin: String, destination: String): String {
        try {
            val searchHistoryCollectionRef = firestore.collection("Users").document(userId).collection("SearchHistory")
            val newSearchHistory = hashMapOf(
                "origin" to origin,
                "destination" to destination
            )
            val documentReference = searchHistoryCollectionRef.add(newSearchHistory).await()
            return documentReference.id
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getSearchHistory(userId: String): List<Map<String, Any>> {
        try {
            val searchHistoryCollectionRef = firestore.collection("Users").document(userId).collection("SearchHistory")
            val result = searchHistoryCollectionRef.get().await()
            val searchHistoryList = mutableListOf<Map<String, Any>>()
            for (document in result) {
                searchHistoryList.add(document.data)
            }
            return searchHistoryList
        } catch (e: Exception) {
            throw e
        }
    }
}