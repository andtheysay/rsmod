package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World

fun Tile.isMulti(world: World): Boolean {
    val region = regionId
    val chunk = chunkCoords.hashCode()
    return world.getMultiCombatChunks().contains(chunk) || world.getMultiCombatRegions().contains(region)
}

fun Tile.getWildernessLevel(): Int {
    if (x !in 2941..3392 || y !in 3524..3968) {
        return 0
    }

    val z = if (this.y > 6400) this.y - 6400 else this.y
    return (((z - 3525) shr 3) + 1)
}