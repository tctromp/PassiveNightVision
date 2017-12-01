package org.trompgames.skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.effects.Effect;
import com.herocraftonline.heroes.characters.effects.EffectType;
import com.herocraftonline.heroes.characters.skill.PassiveSkill;

public class PassiveNightVision extends PassiveSkill {

	private Effect effect;
	
	public PassiveNightVision(Heroes plugin) {
		super(plugin, "NightVision");

		effect = new Effect(plugin, this, "NightVision", EffectType.NIGHT_VISION);
		effect.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(6000000, 0), false);
		effect.setPersistent(true);

		Bukkit.getPluginManager().registerEvents(new SkillListener(), plugin);		
	}

	@Override
	public void tryApplying(Hero hero) {
		if (!hero.hasAccessToSkill(this)) {
			effect.removeFromHero(hero);
			return;
		}
		if (hero.canUseSkill(this)) {
			if (!hero.hasEffect(getName())) {				
				effect.applyToHero(hero);
			}
		} else {
			effect.removeFromHero(hero);
		}
	}
	
	@Override
	public String getDescription(Hero hero) {
		return "Passive Night Vision";
	}
	
	/*
	 * Used to make sure that the effect is removed if a player changes classes or somehow loses the ability
	 */	
	public class SkillListener implements Listener{
		
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) {
			Player player = event.getPlayer();
			PotionEffect effect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
			if(effect == null) return;
			if(effect.getDuration() > 5000000) player.removePotionEffect(PotionEffectType.NIGHT_VISION);			
		}
		
	}
	
	
}
