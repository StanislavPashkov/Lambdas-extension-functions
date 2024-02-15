package org.example

data class Chat(val messages: MutableList<Messages> = mutableListOf())

data class Messages(val text: String, var read: Boolean = false)

class NoChatException(message: String) : Exception(message)

object ChatService {
    private var chats = mutableMapOf(10 to Chat())

    fun clear() {
        chats = mutableMapOf()
    }

    fun getUnreadChatsCount() =
        chats.values.count { chat -> chat.messages.any { !it.read } }

    fun getChats() = chats.values.map { chats.values.joinToString("\n***\n") }.lastOrNull()

    fun getLastMessages() =
        chats.values.map { it.messages.lastOrNull()?.text ?: "No Messages" }

    fun getMessages(userId: Int, count: Int): List<Messages> {
        val chat = chats[userId] ?: throw NoChatException("NO chat")
        return chat.messages.takeLast(count).onEach { it.read = true }
    }

    fun addChatMessage(userId: Int, message: Messages) =
        chats.getOrPut(userId) { Chat() }.messages.plusAssign(message.copy())

    fun deleteMessage(userId: Int, message: Int) =
        chats.getOrPut(userId) { Chat() }.messages.removeAt(message - 1)

    fun deleteChat(userId: Int): Int {
        chats.remove(userId)
        return chats.values.size
    }

    //chats.remove(userId)?:throw NoChatException("No Chat")
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
    println(ChatService.deleteMessage(1, 1))
    ChatService.print()
    println("Удалить чат")
    println(ChatService.deleteChat(1))
    ChatService.print()


}