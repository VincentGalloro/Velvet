package experiments

import velvet.main.Velvet
import velvet.main.VelvetState
import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.components.BasicEventComponent
import velvet.ui.components.functional.OnClickComponent
import velvet.ui.components.graphical.FadeInComponent
import velvet.ui.premade.OutlinedTextNode
import velvet.ui.premade.ScrollableListNode
import velvet.ui.velements.SquareElement
import velvet.util.types.VColor
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Size
import velvet.util.types.spatial.Vector
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.min
import kotlin.streams.toList

class FolderNode(val path: Path) : UINode() {

    private val otn = OutlinedTextNode(path.fileName.toString())

    init{
        otn.squareElement.fillColor = VColor.fromHSB(path.hashCode()%1000/1000.0, 0.3, 1.0)
        otn.textElement.setFontResolution(60)
        add(otn)
        add(BasicEventComponent{
            onHoverStart = { otn.squareElement.outlineColor = VColor.grayScale(200) }
            onHoverEnd = { otn.squareElement.outlineColor = VColor.BLACK }
        })
    }
}

class FolderListNode : UINode(){

    var onPathChange: ((Path?)->Unit)? = null
    var onUnload: (()->Unit)? = null

    fun unload(){
        clearSubNodes()
        add(SquareElement())

        onUnload?.invoke()
    }

    fun reload(paths: List<Path>){
        clearSubNodes()

        val list = ScrollableListNode<FolderNode>()
        val listLayout = Layout.new().padCenter(20.0).columnList().fixedHeight(60.0, sep=10.0)
                .bind(list.scrollComponent).add { it, index ->
                    it.globalMove(Vector((1-min(abs(index-5-list.scrollComponent.scroll), 1.0))*120,0))
                            .setWidth(220.0, 0.0)
                }

        list.scrollComponent.onScrollChange = {
            paths.getOrNull(it+5)?.let {
                onPathChange?.invoke(it)
            }
        }
        list.loadNodes(paths.mapIndexed { index, path ->
            FolderNode(path).also {
                it.add(FadeInComponent())
                it.add(OnClickComponent.left {
                    list.scrollComponent.targetScroll = index-5
                })
            }
        }, listLayout)

        add(list)
        add(SquareElement())
    }
}

class FileExplorer(velvetState: VelvetState) : Velvet(velvetState) {

    init{
        val folderListListNode = ScrollableListNode<FolderListNode>()
        val layout = Layout.new().rowList().fixedWidth(360.0, sep=10.0)
                .bind(folderListListNode.scrollComponent)
        folderListListNode.scrollComponent.disableUserControl()
        folderListListNode.scrollComponent.targetScroll = -1

        val folderListNodes = List(20){ FolderListNode() }

        folderListNodes.forEachIndexed { index, folderListNode ->
            folderListNode.unload()
            folderListNode.onPathChange = {
                folderListListNode.scrollComponent.targetScroll = index-1
                if(it != null && Files.isDirectory(it)) {
                    try {
                        folderListNodes.getOrNull(index + 1)?.reload(Files.list(it).toList())
                    } catch (e: java.nio.file.AccessDeniedException){}
                    folderListNodes.getOrNull(index + 2)?.unload()
                }
                else{
                    folderListNodes.getOrNull(index+1)?.unload()
                }
            }
            folderListNode.onUnload = {
                folderListNodes.getOrNull(index+1)?.unload()
            }
        }
        folderListNodes[0].reload(Files.list(Path.of("C:\\")).toList())

        folderListListNode.loadNodes(folderListNodes, layout)

        rootNode.add(folderListListNode, Layout.of { _, _ -> Bounds.fromStartOfSize(Vector(), size.toArea()) })
    }

    override fun onClose() {
    }
}

fun main() {
    Velvet.start({ FileExplorer(it) }, Size(1500, 832), "Fire Explorer")
}