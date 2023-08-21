package com.example.freekino.data.repository

import com.example.freekino.domain.models.VideoBasicInfo
import com.source.GetmediaQuery
import com.source.RefreshQuery

fun GetmediaQuery.Getmedium.toSimpleFilm(): VideoBasicInfo{
    return VideoBasicInfo(
        name = name,
        img = img,
        link = link,
        year = year,
    )
}

fun RefreshQuery.Refresh.toSimpleFilm(): VideoBasicInfo{
    return VideoBasicInfo(
        name = name,
        img = img,
        link = link,
        year = year,
    )
}