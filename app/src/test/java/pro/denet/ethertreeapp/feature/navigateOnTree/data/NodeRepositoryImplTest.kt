package pro.denet.ethertreeapp.feature.navigateOnTree.data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import pro.denet.ethertreeapp.core.db.NodeDao
import pro.denet.ethertreeapp.core.db.NodeEntity
import pro.denet.ethertreeapp.core.util.CalculateAddressNode
import kotlin.test.assertEquals

class NodeRepositoryImplTest {

    @MockK
    private lateinit var nodeDao: NodeDao

    private val calculateAddressNode: CalculateAddressNode = CalculateAddressNodeMock()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @Test
    fun `Node is null getNodeWithChildren`() = runTest {

        val idNode = 3
        val nulNode: NodeEntity? = null

        coEvery {
            nodeDao.getNodeById(idNode)
        } returns nulNode

        val repository = NodeRepositoryImpl(nodeDao, calculateAddressNode)

        val result = repository.getNodeWithChildren(idNode)

        assertTrue(result.isFailure)

    }

    @Test
    fun `Node is haven't children getNodeWithChildren`() {
        val idNode = 3
        val node3 = NodeEntity(idNode, "a", 0)

        val childrenNode: Flow<List<NodeEntity>> = flowOf(
            listOf<NodeEntity>()

        )

        coEvery {
            nodeDao.getNodeById(idNode)
        } returns node3

        coEvery {
            nodeDao.getChildrenByParentId(idNode)
        } returns childrenNode

        val repository = NodeRepositoryImpl(nodeDao, calculateAddressNode)

        runTest {

            val result = repository.getNodeWithChildren(idNode)

            assertTrue(result.isSuccess)
            assertEquals(
                0,
                result.getOrNull()!!.children.first().size
            )
        }
    }

    @Test
    fun `Node node does not exist getNodeById`() {
        val idNode = 3
        val node3: NodeEntity? = null


        coEvery {
            nodeDao.getNodeById(idNode)
        } returns node3

        val repositoryImpl = NodeRepositoryImpl(nodeDao, calculateAddressNode)

        runTest {
            val result = repositoryImpl.getNodeById(idNode)

            assertTrue(result.isFailure)
        }
    }

    @Test
    fun `The node exists getNodeById`() {
        val idNode = 3
        val node3 = NodeEntity(
            idNode,
            "a",
            1
        )

        coEvery {
            nodeDao.getNodeById(idNode)
        } returns node3

        val repositoryImpl = NodeRepositoryImpl(nodeDao, calculateAddressNode)

        runTest {
            val result = repositoryImpl.getNodeById(idNode)

            assertTrue(result.isSuccess)
            val resultNode = result.getOrNull()!!
            assertEquals(node3.id, resultNode.id)
            assertEquals(node3.parentId, resultNode.parentId)
            assertEquals(node3.address, resultNode.address)
            assertEquals(false, resultNode.isRoot)
        }
    }


    @Test
    fun `Node node does not exist deleteNode`() {
        val idNode = 3
        val delRow = 0

        coEvery {
            nodeDao.deleteNode(idNode)
        } returns delRow

        val repositoryImpl = NodeRepositoryImpl(nodeDao, calculateAddressNode)

        runTest {
            val result = repositoryImpl.deleteNode(idNode)
            assertTrue(result.isFailure)
        }
    }

    @Test
    fun `The node exists deleteNode`() {
        val idNode = 3
        val delRow = 1

        coEvery {
            nodeDao.deleteNode(idNode)
        } returns delRow

        val repositoryImpl = NodeRepositoryImpl(nodeDao, calculateAddressNode)

        runTest {
            val result = repositoryImpl.deleteNode(idNode)
            assertTrue(result.isSuccess)
            assertEquals(Unit, result.getOrNull()!!)
        }
    }


    class CalculateAddressNodeMock : CalculateAddressNode {
        override fun invoke(id: Int, parentId: Int): String {
            return "asd"
        }
    }

}