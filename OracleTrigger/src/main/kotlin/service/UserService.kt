package com.practice.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.RestClientException
import com.practice.repository.UserRepository
import com.practice.model.User

@Service
class UserService(private val userRepository: UserRepository, private val restTemplate: RestTemplate) {

    @Transactional
    fun create(user: User): User {
        userRepository.insert(user)

        try {
            // 模擬發送其他 API 請求
            val response = restTemplate.postForEntity("https://external.api/service", user, String::class.java)
            if (!response.statusCode.is2xxSuccessful) {
                throw RuntimeException("External API failed with status: ${response.statusCode}")
            }
        } catch (ex: RestClientException) {
            // 擲出例外會觸發 rollback
            throw RuntimeException("External API call failed", ex)
        }

        return user
    }

    @Transactional(readOnly = true)
    fun read(id: Long): User {
        return userRepository.findById(id)
            ?: throw NoSuchElementException("User with ID $id not found")
    }

    @Transactional
    fun update(id: Long, user: User): User {
        userRepository.findById(id)
            ?: throw IllegalArgumentException("User not found")

        userRepository.insert(user.copy(id = id))

        try {
            val response = restTemplate.postForEntity("https://external.api/notify", user, String::class.java)
            if (!response.statusCode.is2xxSuccessful) {
                throw RuntimeException("External API failed with status: ${response.statusCode}")
            }
        } catch (ex: RestClientException) {
            throw RuntimeException("External API call failed", ex)
        }

        // 回傳更新後的 user（可以從參數回傳）
        return user.copy(id = id)
    }

    @Transactional
    fun delete(id: Long) {
        val user = userRepository.findById(id)
            ?: throw IllegalArgumentException("User not found")

        userRepository.delete(id)

        try {
            // 假設你想在刪除後通知其他系統
            val response = restTemplate.postForEntity("https://external.api/deleteNotify", user, String::class.java)
            if (!response.statusCode.is2xxSuccessful) {
                throw RuntimeException("External API failed with status: ${response.statusCode}")
            }
        } catch (ex: RestClientException) {
            throw RuntimeException("External API call failed", ex)
        }

        // 若有歷史紀錄寫入，應該也放在這裡的 transactional 範圍內
    }
}