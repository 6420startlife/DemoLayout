package com.ptithcm.thuan6420.basecleanarchitecture.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleModule {
    @Singleton
    @Provides
    fun provideGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
    }

    @Singleton
    @Provides
    fun provideGoogleSignInClient(@ApplicationContext context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }
}