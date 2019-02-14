package gg.rsmod.plugins.osrs.content.inter.attack

import gg.rsmod.plugins.osrs.api.EquipmentType
import gg.rsmod.plugins.osrs.api.ext.player
import gg.rsmod.plugins.osrs.api.ext.setVarp
import gg.rsmod.plugins.osrs.api.ext.toggleVarp

/**
 * Attack style buttons
 */
on_button(parent = 593, child = 3) {
    val p = it.player()
    p.setVarp(AttackTab.ATTACK_STYLE_VARP, 0)
}

on_button(parent = 593, child = 7) {
    val p = it.player()
    p.setVarp(AttackTab.ATTACK_STYLE_VARP, 1)
}

on_button(parent = 593, child = 11) {
    val p = it.player()
    p.setVarp(AttackTab.ATTACK_STYLE_VARP, 2)
}

on_button(parent = 593, child = 15) {
    val p = it.player()
    p.setVarp(AttackTab.ATTACK_STYLE_VARP, 3)
}

/**
 * Toggle auto-retaliate button.
 */
on_button(parent = 593, child = 29) {
    val p = it.player()
    p.toggleVarp(AttackTab.AUTO_RETALIATE_VARP)
}

/**
 * Toggle special attack.
 */
on_button(parent = 593, child = 35) {
    val p = it.player()
    p.toggleVarp(AttackTab.SPECIAL_ATTACK_VARP)
}

/**
 * Disable special attack when switching weapons.
 */
on_equip_to_slot(EquipmentType.WEAPON.id) {
    it.player().setVarp(AttackTab.SPECIAL_ATTACK_VARP, 0)
}

/**
 * Disable special attack on log-out.
 */
on_logout {
    it.player().setVarp(AttackTab.SPECIAL_ATTACK_VARP, 0)
}