package org.example

data class Chat(var id: Int, val messages: MutableList<Messages> = mutableListOf())

data class Messages(val text: String, var read: Boolean = false, var deleted: Boolean = false)

class NoChatException(message: String) : Exception(message)


object ChatService {
    private var chats = mutableMapOf<Int, Chat>()

    fun clear() {
        chats = mutableMapOf()
    }

    fun getUnreadChatsCount() =
        chats.values.count { chat -> chat.messages.any { !it.read } }

    fun getChats(): List<Chat> = chats.map { it.value }

    fun getLastMessages() =
        chats.values.map { it.messages.lastOrNull()?.text ?: "No Messages" }

    fun getMessages(userId: Int, count: Int): List<Messages> {
        val chat = chats[userId] ?: throw NoChatException("NO chat")
        return chat.messages.takeLast(count).onEach { it.read = true }
    }

    fun addChatMessage(userId: Int, message: Messages) =
        chats.getOrPut(userId) { Chat(userId) }.messages.plusAssign(message.copy())

    fun deleteMessage(userId: Int): Boolean {
        chats[userId]?.messages?.onEach { it.deleted = true }
            ?: throw NoChatException("There is no chat with this ID")
        return true
    }


    fun deleteChat(userId: Int): Int {
        chats.remove(userId)
        return chats.values.size
    }

    fun print() =
        println("$chats\n******************************************************")

}

fun main() {
    ChatService.addChatMessage(1, Messages("Hi"))
    ChatService.addChatMessage(2, Messages("Hello"))
    ChatService.addChatMessage(2, Messages("Hi"))
    ChatService.print()
    println(ChatService.getMessages(2, 1))
    ChatService.print()
    println("сколько чатов не прочитано")
    println(ChatService.getUnreadChatsCount())
    println("Получить список сообщений из чата")
    println(ChatService.getLastMessages())
    println("Получить список чатов")
    println(ChatService.getChats())
    println("Удалить сообщение")
    println(ChatService.deleteMessage(2))

    println("Удалить чат")
    println(ChatService.deleteChat(1))
    ChatService.print()


}