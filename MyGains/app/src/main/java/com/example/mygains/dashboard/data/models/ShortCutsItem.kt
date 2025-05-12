package com.example.mygains.dashboard.data.models


import com.example.mygains.R

sealed class ShortCutsItem(){
    abstract var icon : Int
    abstract var title:String
    abstract var color: Int
    abstract var iconColor: Int



    data class ScannerShortcut(
        override var icon: Int = R.drawable.shortcut_stats,
        override var title: String = "Estadisticas",
        override var color: Int = R.color.green_soft ,
        override var iconColor : Int = R.color.green_icon
    ): ShortCutsItem()


    data class StatsShortcut(
        override var icon: Int = R.drawable.scanner_bar_code,
        override var title: String = "Escanear",
        override var color: Int = R.color.blue_soft ,
        override var iconColor : Int = R.color.blue_icon
    ): ShortCutsItem()

    data class ParchuesShortcut(
        override var icon: Int = R.drawable.shortcut_compras,
        override var title: String = "Compra",
        override var color: Int = R.color.orange_low ,
        override var iconColor : Int = R.color.orange
    ): ShortCutsItem()

    data class RatsShortcut(
        override var icon: Int = R.drawable.shortcut_ratschat,
        override var title: String = "Rats",
        override var color: Int = R.color.purple_soft ,
        override var iconColor : Int = R.color.purple_icond
    ): ShortCutsItem()


}




