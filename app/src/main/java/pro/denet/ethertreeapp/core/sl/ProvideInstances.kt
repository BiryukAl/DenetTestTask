package pro.denet.ethertreeapp.core.sl

import android.content.Context
import pro.denet.ethertreeapp.core.db.CacheModule
import pro.denet.ethertreeapp.core.util.GetAddressNode
import pro.denet.ethertreeapp.core.util.PrivateKey
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.AddNodeToParentUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.DeleteNodeUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetNodeWithChildrenUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetRootNodeUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel


private fun provideInstances(context: Context, isMock: Boolean = false){

    ServiceLocator.register<Context>(context)

    // Util
    ServiceLocator.register<GetAddressNode>(GetAddressNode.Mock(PrivateKey.Mock))

    //Data
    if (isMock) {
        ServiceLocator.register<CacheModule>(CacheModule.Mock(locate()))
    } else {
        ServiceLocator.register<CacheModule>(CacheModule.Base(locate()))
    }

//        ServiceLocator.register<NodeRepository>()

    //Presentation
    ServiceLocator.register<AddNodeToParentUseCase>(AddNodeToParentUseCase(locate()))
    ServiceLocator.register<DeleteNodeUseCase>(DeleteNodeUseCase(locate()))
    ServiceLocator.register<GetNodeWithChildrenUseCase>(GetNodeWithChildrenUseCase(locate()))
    ServiceLocator.register<GetRootNodeUseCase>(GetRootNodeUseCase(locate()))

    ServiceLocator.register<MainNavigationOnTreeViewModel>(
        MainNavigationOnTreeViewModel(locate(), locate(), locate(), locate())
    )

}

interface ProvideInstances {

    class Base(
        private val context: Context
    ) : ProvideInstances {
        init {
            provideInstances(context)
        }
    }

    class Mock(
        private val context: Context
    ): ProvideInstances{
        init {
            provideInstances(context, isMock = true)
        }
    }
}
