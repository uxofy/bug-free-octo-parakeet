package dev.maceshush.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MaceCore пишет логи через player.sendMessage(text, false) -> ChatHud.addMessage.
 * Каждое такое сообщение начинается с иконки из шрифта: U+E012 (free) или U+E018 (premium).
 * Одноаргументный addMessage(Text) делегирует в этот трёхаргументный, так что хватает одного хука.
 */
@Mixin(ChatHud.class)
public class ChatHudMixin {
	@Inject(
		method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private void maceshush$filter(Text message, MessageSignatureData sig, MessageIndicator indicator, CallbackInfo ci) {
		String s = message.getString();
		if (s.indexOf('\uE012') >= 0 || s.indexOf('\uE018') >= 0) {
			ci.cancel();
		}
	}
}
