/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.fabric.mixin;

import com.sk89q.worldedit.extension.platform.Watchdog;
import com.sk89q.worldedit.fabric.internal.ExtendedMinecraftServer;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.file.Path;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer implements Watchdog, ExtendedMinecraftServer {

    @Shadow
    private long nextTickTime;
    @Final
    @Shadow
    protected LevelStorageSource.LevelStorageAccess storageSource;

    @Override
    public void tick() {
        nextTickTime = Util.getMillis();
    }

    @Override
    public Path getStoragePath(Level world) {
        return storageSource.getDimensionPath(world.dimension());
    }

    @Accessor
    @Override
    public abstract ServerResources getResources();
}
