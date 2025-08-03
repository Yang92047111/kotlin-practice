package com.example.spec.amqp

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

/**
 * Base AMQP message for user-related operations
 */
abstract class UserMessage(
    @JsonProperty("messageId")
    open val messageId: String,

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @JsonProperty("routingKey")
    val routingKey: String,

    @JsonProperty("userId")
    open val userId: Long
)

/**
 * AMQP message for user creation notifications
 */
data class UserCreatedMessage(
    @JsonProperty("messageId")
    override val messageId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("username")
    val username: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("fullName")
    val fullName: String,

    @JsonProperty("source")
    val source: String = "user-service"
) : UserMessage(messageId, LocalDateTime.now(), "user.created", userId)

/**
 * AMQP message for user update notifications
 */
data class UserUpdatedMessage(
    @JsonProperty("messageId")
    override val messageId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("updatedFields")
    val updatedFields: Map<String, Any>,

    @JsonProperty("source")
    val source: String = "user-service"
) : UserMessage(messageId, LocalDateTime.now(), "user.updated", userId)

/**
 * AMQP message for user deletion notifications
 */
data class UserDeletedMessage(
    @JsonProperty("messageId")
    override val messageId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("reason")
    val reason: String? = null,

    @JsonProperty("source")
    val source: String = "user-service"
) : UserMessage(messageId, LocalDateTime.now(), "user.deleted", userId)

/**
 * AMQP message for user status change notifications
 */
data class UserStatusChangedMessage(
    @JsonProperty("messageId")
    override val messageId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("oldStatus")
    val oldStatus: String,

    @JsonProperty("newStatus")
    val newStatus: String,

    @JsonProperty("source")
    val source: String = "user-service"
) : UserMessage(messageId, LocalDateTime.now(), "user.status.changed", userId)

/**
 * AMQP message envelope with metadata
 */
data class MessageEnvelope<T>(
    @JsonProperty("headers")
    val headers: Map<String, String>,

    @JsonProperty("properties")
    val properties: MessageProperties,

    @JsonProperty("body")
    val body: T
)

/**
 * AMQP message properties
 */
data class MessageProperties(
    @JsonProperty("contentType")
    val contentType: String = "application/json",

    @JsonProperty("deliveryMode")
    val deliveryMode: Int = 2, // Persistent

    @JsonProperty("priority")
    val priority: Int = 0,

    @JsonProperty("correlationId")
    val correlationId: String? = null,

    @JsonProperty("replyTo")
    val replyTo: String? = null,

    @JsonProperty("expiration")
    val expiration: String? = null,

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()
)