package kz.app.hero_datasource.cache

import kz.app.hero_domain.Hero
import kz.app.hero_domain.HeroRole
import kz.app.hero_domain.getHeroAttackType
import kz.app.hero_domain.getHeroAttrFromAbbreviation
import kz.app.herodatasource.cache.Hero_Entity

fun Hero_Entity.toHero(): Hero {
    return Hero(
        id = id.toInt(),
        localizedName = localizedName,
        primaryAttribute = getHeroAttrFromAbbreviation(primaryAttribute),
        attackType = getHeroAttackType(attackType),
        roles = rolesToList(
            carry = roleCarry == 1L,
            escape = roleEscape == 1L,
            nuker = roleNuker == 1L,
            initiator = roleInitiator == 1L,
            durable = roleDurable == 1L,
            disabler = roleDisabler == 1L,
            jungler = roleJungler == 1L,
            support = roleSupport == 1L,
            pusher = rolePusher == 1L,
        ),
        img = img,
        icon = icon,
        baseHealth = baseHealth.toFloat(),
        baseHealthRegen = baseHealthRegen?.toFloat(),
        baseMana = baseMana.toFloat(),
        baseManaRegen = baseManaRegen?.toFloat(),
        baseArmor = baseArmor.toFloat(),
        baseMoveRate = baseMoveRate.toFloat(),
        baseAttackMin = baseAttackMin.toInt(),
        baseAttackMax = baseAttackMax.toInt(),
        baseStr = baseStr.toInt(),
        baseAgi = baseAgi.toInt(),
        baseInt = baseInt.toInt(),
        strGain = strGain.toFloat(),
        agiGain = agiGain.toFloat(),
        intGain = intGain.toFloat(),
        attackRange = attackRange.toInt(),
        projectileSpeed = projectileSpeed.toInt(),
        attackRate = attackRate.toFloat(),
        moveSpeed = moveSpeed.toInt(),
        turnRate = turnRate?.toFloat(),
        legs = legs.toInt(),
        turboPicks = turboPicks.toInt(),
        turboWins = turboWins.toInt(),
        proWins = proWins.toInt(),
        proPick = proPick.toInt(),
        firstPick = firstPick.toInt(),
        firstWin = firstWin.toInt(),
        secondPick = secondPick.toInt(),
        secondWin = secondWin.toInt(),
        thirdPick = thirdPick.toInt(),
        thirdWin = thirdWin.toInt(),
        fourthPick = fourthPick.toInt(),
        fourthWin = fourthWin.toInt(),
        fifthPick = fifthPick.toInt(),
        fifthWin = fifthWin.toInt(),
        sixthPick = sixthPick.toInt(),
        sixthWin = sixthWin.toInt(),
        seventhPick = seventhPick.toInt(),
        seventhWin = seventhWin.toInt(),
        eighthWin = eighthWin.toInt(),
        eighthPick = eighthPick.toInt(),
    )
}


fun rolesToList(
    carry: Boolean,
    escape: Boolean,
    nuker: Boolean,
    initiator: Boolean,
    durable: Boolean,
    disabler: Boolean,
    jungler: Boolean,
    support: Boolean,
    pusher: Boolean,
): List<HeroRole> {
    val roles: MutableList<HeroRole> = mutableListOf()
    if (carry) roles.add(HeroRole.Carry)
    if (escape) roles.add(HeroRole.Escape)
    if (nuker) roles.add(HeroRole.Nuker)
    if (initiator) roles.add(HeroRole.Initiator)
    if (durable) roles.add(HeroRole.Durable)
    if (disabler) roles.add(HeroRole.Disabler)
    if (jungler) roles.add(HeroRole.Jungler)
    if (support) roles.add(HeroRole.Support)
    if (pusher) roles.add(HeroRole.Pusher)
    return roles.toList()
}