package velvet.velements.impl

import velvet.main.VGraphics
import velvet.structs.DenseGrid
import velvet.structs.Position
import velvet.structs.Vector
import velvet.velements.VElement

class GridElement(private val gridSize: Position,
                  private val elementFactory: (Position)->VElement) : VElement{

    companion object{

        fun <T : Any> ofDenseGrid(grid: DenseGrid<T>, elementFactory: (T)->VElement) =
                GridElement(grid.size){ elementFactory(grid[it]) }
    }

    override val size: Vector? = null

    override fun render(g: VGraphics, targetSize: Vector) {
        val tileSize = targetSize / gridSize.toVector()
        gridSize.gridIterate {
            g.save()
            g.translate(it.toVector() * tileSize)
            elementFactory(it).render(g, tileSize)
            g.reset()
        }
    }
}

