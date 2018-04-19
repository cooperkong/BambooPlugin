package presenter

import javax.swing.Icon

interface IconPresenter {
    fun getIcon(index: Int): Icon
}