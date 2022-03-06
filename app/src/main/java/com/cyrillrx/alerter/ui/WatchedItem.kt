package com.cyrillrx.alerter.ui

class WatchedItem(
    val url: String,
    val validator: (String) -> Boolean,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
)
