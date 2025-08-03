package com.example.spec.events

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDateTime

/**
 * Base class for all user-related WebSocket events
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "eventType"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = UserCreatedEvent::class, name = "USER_CREATED"),
    JsonSubTypes.Type(value = UserUpdatedEvent::class, name = "USER_UPDATED"),
    JsonSubTypes.Type(value = UserDeletedEvent::class, name = "USER_DELETED"),
    JsonSubTypes.Type(value = UserStatusChangedEvent::class, name = "USER_STATUS_CHANGED")
)
abstract class UserEvent(
    @JsonProperty("eventId")
    open val eventId: String,

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @JsonProperty("userId")
    open val userId: Long
)

/**
 * Event fired when a new user is created
 */
data class UserCreatedEvent(
    @JsonProperty("eventId")
    override val eventId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("username")
    val username: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("fullName")
    val fullName: String
) : UserEvent(eventId, LocalDateTime.now(), userId)

/**
 * Event fired when a user is updated
 */
data class UserUpdatedEvent(
    @JsonProperty("eventId")
    override val eventId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("updatedFields")
    val updatedFields: Map<String, Any>
) : UserEvent(eventId, LocalDateTime.now(), userId)

/**
 * Event fired when a user is deleted
 */
data class UserDeletedEvent(
    @JsonProperty("eventId")
    override val eventId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("reason")
    val reason: String? = null
) : UserEvent(eventId, LocalDateTime.now(), userId)

/**
 * Event fired when user status changes
 */
data class UserStatusChangedEvent(
    @JsonProperty("eventId")
    override val eventId: String,

    @JsonProperty("userId")
    override val userId: Long,

    @JsonProperty("oldStatus")
    val oldStatus: String,

    @JsonProperty("newStatus")
    val newStatus: String
) : UserEvent(eventId, LocalDateTime.now(), userId)

/**
 * WebSocket message wrapper
 */
data class WebSocketMessage<T>(
    @JsonProperty("type")
    val type: String,

    @JsonProperty("payload")
    val payload: T,

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()
)