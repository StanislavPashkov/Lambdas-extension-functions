import org.example.ChatService
import org.example.Messages
import org.example.NoChatException
import org.junit.Before
import org.junit.Test
import kotlin.test.*


class ChatServiceTest {

    private val service = ChatService

    private val messages1 = Messages("Hi")
    private val messages2 = Messages("Hello")
    private val messages3 = Messages("Bye")

    @Before
    fun cleanMap() = ChatService.clear()

    @Test(expected = NoChatException::class)
    fun shouldShowMessage() {
        service.getMessages(1, 1)
    }

    @Test
    fun showUnreadMessages() {
        service.addChatMessage(1, messages1)
        service.addChatMessage(2, messages2)
        service.addChatMessage(2, messages3)

        assertEquals(2, service.getUnreadChatsCount())
    }

    @Test
    fun showAllChats() {
        service.addChatMessage(1, messages1)
        service.addChatMessage(2, messages2)
        service.addChatMessage(2, messages3)

        assertNotNull(service.getChats())
    }

    @Test
    fun showMessageChatID() {
        service.addChatMessage(1, messages1)
        service.addChatMessage(1, messages2)
        service.getLastMessages()

        assertEquals(1, service.getLastMessages().size)
    }

    @Test
    fun deleteTest() {
        service.addChatMessage(1, messages1)
        service.addChatMessage(3, messages1)
        service.addChatMessage(4, messages1)
        service.addChatMessage(2, messages2)
        service.addChatMessage(2, messages3)

        assertEquals(messages2,service.deleteMessage(2,1))

        assertEquals(3,service.deleteChat(1))
    }
}