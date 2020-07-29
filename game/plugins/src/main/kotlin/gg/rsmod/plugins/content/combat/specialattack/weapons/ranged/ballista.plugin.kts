package gg.rsmod.plugins.content.combat.specialattack.weapons.ranged

import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.ExecutionType
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.ranged.RangedProjectile


val SPECIAL_REQUIREMENT = 65

SpecialAttacks.register(intArrayOf(Items.LIGHT_BALLISTA, Items.HEAVY_BALLISTA), SPECIAL_REQUIREMENT, ExecutionType.EXECUTE_ON_ATTACK) {
    player.animate(id = 7224)
    fireBallista(player, target)
}
fun fireBallista(player: Player, target : Pawn) {
    val world = player.world

    val ammo = player.getEquipment(EquipmentType.AMMO)

    val ammoProjectile = if (ammo != null) RangedProjectile.values.firstOrNull {  ammo.id  in it.items } else null

    if (ammoProjectile != null) {
        val projectile = player.createProjectile(target, ammoProjectile.gfx, ammoProjectile.type)
        ammoProjectile.drawback?.let { drawback -> player.graphic(drawback) }
        world.spawn(projectile)

        world.spawn(AreaSound(tile = player.tile, id = 2693, radius = 10, volume = 1))

        val maxHit = RangedCombatFormula.getMaxHit(player, target)
        val accuracy = RangedCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.25)
        val landHit = accuracy >= world.randomDouble()
        val delay = RangedCombatStrategy.getHitDelay(target.getCentreTile(), target.tile.transform(target.getSize() / 2, target.getSize() / 2))
        player.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = delay)
    }

}