package pro.denet.ethertreeapp.core.util


interface PrivateKey {
    fun getPrivateKey(): String

    object Mock : PrivateKey {
        override fun getPrivateKey(): String =
            "gripe" + "breve" + "matrix" + "miles"

    }

}
