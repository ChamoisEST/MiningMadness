package com.chamoisest.miningmadness.compat.ghostfilterhandlers;

import com.chamoisest.miningmadness.client.screen.VoidFilterScreen;
import com.chamoisest.miningmadness.common.container.VoidFilterMenu;
import com.chamoisest.miningmadness.common.container.customslot.FilterVoidSlot;
import com.chamoisest.miningmadness.networking.MMMessages;
import com.chamoisest.miningmadness.networking.packet.GhostFilterVoidC2SPacket;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GhostFilterVoid implements IGhostIngredientHandler<VoidFilterScreen> {

    @Override
    public <I> List<Target<I>> getTargets(VoidFilterScreen gui, I ingredient, boolean doStart) {
        List<Target<I>> targets = new ArrayList<>();

        for(Slot slot : gui.getMenu().slots){
            if(!slot.isActive()){
                continue;
            }

            Rect2i bounds = new Rect2i(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 16, 16);

            if(ingredient instanceof ItemStack && (slot instanceof FilterVoidSlot)){
                targets.add(new Target<I>() {
                    @Override
                    public Rect2i getArea() {
                        return bounds;
                    }

                    @Override
                    public void accept(I ingredient) {
                        boolean canAccept = true;
                        for(int i = VoidFilterMenu.VANILLA_SLOT_COUNT; i <= VoidFilterMenu.VANILLA_SLOT_COUNT + VoidFilterMenu.SLOTS - 1; i++) {
                            if(gui.getMenu().getSlot(i).getItem().sameItem((ItemStack) ingredient)){
                                canAccept = false;
                            }
                        }

                        if(canAccept){
                            slot.set((ItemStack) ingredient);
                            MMMessages.sendToServer(new GhostFilterVoidC2SPacket(slot.index, (ItemStack) ingredient));
                        }
                    }
                });
            }
        }
        return targets;
    }

    @Override
    public void onComplete() {

    }
}
