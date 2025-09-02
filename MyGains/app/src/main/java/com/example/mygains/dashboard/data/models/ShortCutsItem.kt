package com.example.mygains.dashboard.data.models


import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes

sealed class ShortCutsItem(){
    abstract var icon : Int
    abstract var title:String
    abstract var subtitle:String
    abstract var color: Int
    abstract var iconColor: Int
    abstract var route:String



    data class ScannerShortcut(
        override var icon: Int = R.drawable.shortcut_stats,
        override var title: String = "Estadisticas",
        override var subtitle: String = "Tu progreso visualizado",
        override var color: Int = R.color.green_soft ,
        override var iconColor : Int = R.color.green_icon,
        override var route: String = Routes.GainsScanner.routes
    ): ShortCutsItem()


    data class StatsShortcut(
        override var icon: Int = R.drawable.scanner_bar_code,
        override var title: String = "Escanear",
        override var subtitle: String = "Registra tus alimentos",
        override var color: Int = R.color.blue_soft ,
        override var iconColor : Int = R.color.blue_icon,
        override var route: String = Routes.GainsScanner.routes

    ): ShortCutsItem()

    data class ParchuesShortcut(
        override var icon: Int = R.drawable.shortcut_compras,
        override var title: String = "Compra",
        override var subtitle: String = "Tu lista inteligente",
        override var color: Int = R.color.orange_low ,
        override var iconColor : Int = R.color.orange,
        override var route: String = Routes.GainsScanner.routes

    ): ShortCutsItem()

    data class RatsShortcut(
        override var icon: Int = R.drawable.shortcut_ratschat,
        override var title: String = "Rats",
        override var subtitle: String = "Conecta con la comunidad",
        override var color: Int = R.color.purple_soft ,
        override var iconColor : Int = R.color.purple_icond,
        override var route: String = Routes.GainsScanner.routes
    ): ShortCutsItem()


}




