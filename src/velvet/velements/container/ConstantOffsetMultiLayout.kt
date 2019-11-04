package velvet.velements.container

class ConstantOffsetMultiLayout(private val baseLayout: ContainerLayout,
                                private val offset: ContainerLayout) : ContainerMultiLayout {

    override fun createLayout(index: Int): ContainerLayout {
        return ContainerLayout(baseLayout.pos.add(offset.pos.multiply(index.toDouble())),
                baseLayout.size.add(offset.size.multiply(index.toDouble())),
                baseLayout.origin.add(offset.origin.multiply(index.toDouble())),
                baseLayout.angle + offset.angle*index)
    }
}