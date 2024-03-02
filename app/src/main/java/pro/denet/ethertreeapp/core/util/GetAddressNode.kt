package pro.denet.ethertreeapp.core.util

object GetAddressNode {
    @OptIn(ExperimentalStdlibApi::class)
    operator fun invoke(id: Int, parentId: Int): String{
        val inString = "${PrivateKey.getPrivateKey()}$id" + parentId
        return inString.digestKeccak(KeccakParameter.KECCAK_256).toHexString(startIndex = 12)
    }
}