package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GetRootNodeUseCaseTest {

    @Test
    fun `root is not null`() = runBlocking {

        val repository = NodeRepositoryMock()
        val useCase = GetRootNodeUseCase(repository)

        assertNotEquals(null, useCase.invoke())
    }

    @Test
    fun `root node same custom and useCase`() = runBlocking {
        val repository = NodeRepositoryMock()
        repository.createRoot()

        val customRootBrief = repository.getNodeById(1).getOrNull()

        val rootUseCase = GetRootNodeUseCase(repository).invoke().getOrNull()

        assertEquals(
            customRootBrief?.isRoot, rootUseCase?.isRoot
        )
        assertEquals(
            customRootBrief?.id, rootUseCase?.id
        )
        assertEquals(
            customRootBrief?.address, rootUseCase?.address
        )
        assertEquals(
            customRootBrief?.parentId, rootUseCase?.parentId
        )
    }


}