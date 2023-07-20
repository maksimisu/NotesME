package com.maksimisu.notesme.di

import android.content.Context
import com.maksimisu.notesme.data.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Provides
    fun provideNoteRepository(@ApplicationContext appContext: Context): NotesRepository =
        NotesRepository(appContext)

}