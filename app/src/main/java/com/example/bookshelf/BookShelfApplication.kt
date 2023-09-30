package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.di.AppContainer
import com.example.bookshelf.di.DefaultAppContainer

class BookshelfApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}