package velvet.ui.events

data class UIInputState(val hovered: Boolean,
                        val focused: Boolean){

    companion object{
        fun new() = UIInputState(hovered = false, focused = false)
    }
}