package me.luligabi.magicfungi.common.block.crafting.moldingcauldron;

import me.luligabi.magicfungi.common.block.BlockRegistry;
import me.luligabi.magicfungi.common.block.util.ClientSyncedBlockEntity;
import me.luligabi.magicfungi.common.item.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoldingCauldronBlockEntity extends ClientSyncedBlockEntity implements Inventory {

    public MoldingCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.MOLDING_CAULDRON_BLOCK_ENTITY_TYPE, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MoldingCauldronBlockEntity blockEntity) {
        if(blockEntity.isEmpty()) return;
        if(blockEntity.standBy) {
            blockEntity.standBy = false;
            blockEntity.processTime = blockEntity.getAdjustedProcessTime(blockEntity.getFoodComponent());
            markDirty(world, blockEntity.pos, blockEntity.getCachedState());
        } else {
            blockEntity.processTime--;
            if(blockEntity.processTime <= 0) {
                craft(world, blockEntity);
            }
            markDirty(world, blockEntity.pos, blockEntity.getCachedState());
        }
    }

    private static void craft(World world, MoldingCauldronBlockEntity blockEntity) {
        int i =  blockEntity.getMoldOutput(blockEntity.getFoodComponent());
        blockEntity.inventory.set(0, new ItemStack(ItemRegistry.CLYPEUS_ESSENCE, i));
        blockEntity.standBy = true;
        blockEntity.processTime = 0;
        markDirty(world, blockEntity.pos, blockEntity.getCachedState());
    }

    private int getAdjustedProcessTime(FoodComponent foodComponent) {
        int i = 600; // initial 30 seconds

        i += foodComponent.getHunger() * 8; // additional time for the item's hunger value (i.e. cooked beef has 8 hunger, so 600 + 8*8 = 664)
        if(foodComponent.isSnack()) i /= 2; // snacks usually have very low hunger values, so we want to cut the time in half for them

        return i;
    }

    private int getMoldOutput(FoodComponent foodComponent) {
        int i = 1;

        if(foodComponent.isMeat() && foodComponent.getHunger() >= 4) i += 2 + foodComponent.getHunger()/2; // hunger requirement avoids granting a bigger output to uncooked meats
        if(foodComponent.isSnack()) i = 1;

        return Math.max(i, 1);
    }

    private FoodComponent getFoodComponent() {
        return inventory.get(0).getItem().getFoodComponent();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(!isClientSide()) {
            sync();
        }
    }

    @Override
    public void toTag(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        nbt.putShort("ProcessTime", (short) processTime);
        nbt.putBoolean("StandBy", standBy);
    }

    @Override
    public void fromTag(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory);
        this.processTime = nbt.getShort("ProcessTime");
        this.standBy = nbt.getBoolean("StandBy");
    }

    @Override
    public void toClientTag(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void fromClientTag(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory);
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    public int size() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        return this.inventory.get(0).isEmpty();
    }

    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < this.inventory.size() ? this.inventory.get(slot) : ItemStack.EMPTY;
    }

    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
    }

    public void clear() {
        this.inventory.clear();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player.canModifyAt(world, getPos());
    }


    private DefaultedList<ItemStack> inventory;
    int processTime;
    boolean standBy = true;

}