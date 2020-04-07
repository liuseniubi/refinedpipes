package com.raoulvdberge.refinedpipes.network.pipe.attachment.extractor;

import com.raoulvdberge.refinedpipes.block.FluidPipeBlock;
import com.raoulvdberge.refinedpipes.block.ItemPipeBlock;
import com.raoulvdberge.refinedpipes.network.pipe.attachment.Attachment;
import com.raoulvdberge.refinedpipes.network.pipe.attachment.AttachmentFactory;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

import java.util.List;

public class ExtractorAttachmentFactory implements AttachmentFactory {
    private final ExtractorAttachmentType type;

    public ExtractorAttachmentFactory(ExtractorAttachmentType type) {
        this.type = type;
    }

    @Override
    public Attachment createFromNbt(CompoundNBT tag) {
        Direction dir = Direction.values()[tag.getInt("dir")];

        return new ExtractorAttachment(type, dir);
    }

    @Override
    public Attachment create(Direction dir) {
        return new ExtractorAttachment(type, dir);
    }

    @Override
    public ResourceLocation getItemId() {
        return type.getItemId();
    }

    @Override
    public ResourceLocation getId() {
        return type.getId();
    }

    @Override
    public ResourceLocation getModelLocation() {
        return type.getModelLocation();
    }

    @Override
    public void addInformation(List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent("misc.refinedpipes.tier", new TranslationTextComponent("enchantment.level." + type.getTier())).setStyle(new Style().setColor(TextFormatting.YELLOW)));

        ITextComponent itemsToExtract = new StringTextComponent(type.getItemsToExtract() + " ")
            .appendSibling(new TranslationTextComponent("misc.refinedpipes.item" + (type.getItemsToExtract() == 1 ? "" : "s")))
            .setStyle(new Style().setColor(TextFormatting.WHITE));

        float itemSecondsInterval = type.getItemTickInterval() / 20F;
        ITextComponent itemTickInterval = new StringTextComponent(itemSecondsInterval + " ")
            .appendSibling(new TranslationTextComponent("misc.refinedpipes.second" + (itemSecondsInterval == 1 ? "" : "s")))
            .setStyle(new Style().setColor(TextFormatting.WHITE));

        tooltip.add(new TranslationTextComponent(
            "tooltip.refinedpipes.extractor_attachment.item_extraction_rate",
            itemsToExtract,
            itemTickInterval
        ).setStyle(new Style().setColor(TextFormatting.GRAY)));

        ITextComponent fluidsToExtract = new StringTextComponent(type.getFluidsToExtract() + " mB")
            .setStyle(new Style().setColor(TextFormatting.WHITE));

        float fluidSecondsInterval = type.getFluidTickInterval() / 20F;
        ITextComponent fluidTickInterval = new StringTextComponent(fluidSecondsInterval + " ")
            .appendSibling(new TranslationTextComponent("misc.refinedpipes.second" + (fluidSecondsInterval == 1 ? "" : "s")))
            .setStyle(new Style().setColor(TextFormatting.WHITE));

        tooltip.add(new TranslationTextComponent(
            "tooltip.refinedpipes.extractor_attachment.fluid_extraction_rate",
            fluidsToExtract,
            fluidTickInterval
        ).setStyle(new Style().setColor(TextFormatting.GRAY)));
    }

    @Override
    public boolean canPlaceOnPipe(Block pipe) {
        return pipe instanceof ItemPipeBlock
            || pipe instanceof FluidPipeBlock;
    }
}
