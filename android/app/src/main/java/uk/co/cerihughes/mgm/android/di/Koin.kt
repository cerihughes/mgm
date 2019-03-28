package uk.co.cerihughes.mgm.android.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import uk.co.cerihughes.mgm.android.ui.MainViewModel
import uk.co.cerihughes.mgm.common.repository.Repository
import uk.co.cerihughes.mgm.common.repository.RepositoryImpl
import uk.co.cerihughes.mgm.common.repository.local.LocalDataSource
import uk.co.cerihughes.mgm.common.repository.local.LocalDataSourceImpl
import uk.co.cerihughes.mgm.common.repository.remote.RemoteDataSource
import uk.co.cerihughes.mgm.common.repository.remote.RemoteDataSourceImpl
import uk.co.cerihughes.mgm.common.viewmodel.AlbumScoresViewModel
import uk.co.cerihughes.mgm.common.viewmodel.LatestEventViewModel

val appModule = module {

    // single instance of RemoteDataSource
    single<RemoteDataSource> { RemoteDataSourceImpl() }

    // single instance of LocalDataSource
    single<LocalDataSource> { LocalDataSourceImpl(androidContext()) }

    // single instance of Repository
    single<Repository> { RepositoryImpl(get(), get()) }

    viewModel { MainViewModel() }
    viewModel { LatestEventViewModel(get()) }
    viewModel { AlbumScoresViewModel(get()) }
}
