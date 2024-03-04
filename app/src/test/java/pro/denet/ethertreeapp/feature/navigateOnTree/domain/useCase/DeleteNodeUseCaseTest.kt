package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.DeleteRootException

class DeleteNodeUseCaseTest {

    @Test
    fun `delete node`() = runBlocking {

        val repository = NodeRepositoryMock()
        repository.createRoot()

        repository.addNodeToParent(1)

        val node = repository.getNodeById(2)

        assertNotEquals(null, node.getOrNull())

        val useCase = DeleteNodeUseCase(repository)
        useCase(2)

        val deleteNode = repository.getNodeById(2)

        assertEquals(null, deleteNode.getOrNull())
    }

    @Test
    fun `delete root`() = runTest {
        val repository = NodeRepositoryMock()
        repository.createRoot()


        val node = repository.getNodeById(1)

        assertNotEquals(null, node.getOrNull())

        val useCase = DeleteNodeUseCase(repository)

        val result = useCase(1)


        assertEquals(
            DeleteRootException()::class.java,
            result.exceptionOrNull()?.javaClass
        )
        assertEquals(true, result.isFailure)

    }
}
