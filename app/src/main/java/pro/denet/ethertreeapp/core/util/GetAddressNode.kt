package pro.denet.ethertreeapp.core.util

interface GetAddressNode {

    operator fun invoke(id: Int, parentId: Int): String

    class Mock(
        private val privateKey: PrivateKey
    ): GetAddressNode {
        @OptIn(ExperimentalStdlibApi::class)
        override operator fun invoke(id: Int, parentId: Int): String{
            val inString = "${privateKey.getPrivateKey()}$id" + parentId
            return inString.digestKeccak(KeccakParameter.KECCAK_256).toHexString(startIndex = 12)
        }
    }

}
