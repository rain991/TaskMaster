package com.example.taskmaster.data.components.files

interface Downloader {
    fun downloadFile(url: String): Long
}