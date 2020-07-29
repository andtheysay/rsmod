package gg.rsmod.game.model.region.update

/**
 *
 * TODO(Tom): externalize the id as it changes every revision
 *
 * @author Tom <rspsmods@gmail.com>
 */
enum class EntityUpdateType(val id: Int) {
    MAP_ANIM(id = 0),
    UPDATE_GROUND_ITEM(id = 1),
    SPAWN_OBJECT(id = 2),
    UNKNOWN(id = 3),
    REMOVE_OBJECT(id = 4),
    SPAWN_PROJECTILE(id = 5),
    SPAWN_GROUND_ITEM(id = 6),
    REMOVE_GROUND_ITEM(id = 7),
    ANIMATE_OBJECT(id = 8),
    PLAY_TILE_SOUND(id = 9)
}