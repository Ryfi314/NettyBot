/*******************************************************************************
 *     Copyright (C) 2015 Jordan Dalton (jordan.8474@gmail.com)
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *******************************************************************************/
package ru.ryfi.bot.world.pathfinding;


import ru.ryfi.bot.world.World;
import ru.ryfi.bot.world.block.Block;
import ru.ryfi.bot.world.block.BlockType;
import ru.ryfi.bot.world.position.BlockLocation;

public class SimpleWorldPhysics implements WorldPhysics {
	private static final BlockLocation[] surrounding = new BlockLocation[] {
			// middle y + 0
			new BlockLocation(-1, 0, 1), new BlockLocation(0, 0, 1), new BlockLocation(1, 0, 1), new BlockLocation(-1, 0, 0), new BlockLocation(1, 0, 0),
			new BlockLocation(-1, 0, -1), new BlockLocation(0, 0, -1),
			new BlockLocation(1, 0, -1),
			// bottom y - 1
			new BlockLocation(-1, -1, 1), new BlockLocation(0, -1, 1), new BlockLocation(1, -1, 1), new BlockLocation(-1, -1, 0), new BlockLocation(0, -1, 0),
			new BlockLocation(1, -1, 0), new BlockLocation(-1, -1, -1), new BlockLocation(0, -1, -1), new BlockLocation(1, -1, -1),
			// top y + 1
			new BlockLocation(-1, 1, 1), new BlockLocation(0, 1, 1), new BlockLocation(1, 1, 1), new BlockLocation(-1, 1, 0), new BlockLocation(0, 1, 0),
			new BlockLocation(1, 1, 0), new BlockLocation(-1, 1, -1), new BlockLocation(0, 1, -1), new BlockLocation(1, 1, -1), };
	//private static final boolean[] emptyBlocks;

//	static {
//		emptyBlocks = new boolean[897];
//		for(BlockType type : BlockType.values())
//			if(type.getId() >= 0 && type.getId() < emptyBlocks.length)
//				emptyBlocks[type.getId()] = type.isTransparent();
//	}

	private final World world;

	public SimpleWorldPhysics(World world) {
		this.world = world;
	}

	@Override
	public BlockLocation[] findAdjacent(BlockLocation location) {
		BlockLocation[] locations = new BlockLocation[surrounding.length];
		for(int i = 0; i < locations.length; i++)
			locations[i] = location.add(surrounding[i]);
		return locations;
	}

	@Override
	public boolean canWalk(BlockLocation location, BlockLocation location2) {
		int x = location.getX(), y = location.getY(), z = location.getZ();
		int x2 = location2.getX(), y2 = location2.getY(), z2 = location2.getZ();

		if(y2 < 0)
			return false;

		boolean valid = true;
		//Физика максимально простая
		valid = valid && isEmpty(x2, y2, z2);
		valid = valid && isEmpty(x2, y2 + 1, z2);

//		int lowerBlock = world.getBlockIdAt(x2, y2 - 1, z2);
//		valid = valid && lowerBlock != 10;
//		valid = valid && lowerBlock != 11;

		if(isEmpty(x, y - 1, z))
			valid = valid && !isEmpty(x2,y2-1,z2);

//		if(isEmpty(x, y - 1, z))
//			valid = valid && !isEmpty(x2,y2-2,z2);
		if(world.getBlock(location2.offset(0,-1,0)).getMaterial() == BlockType.MAGMA_BLOCK){
			valid = false;
		}
		if(y != y2 && (x != x2 || z != z2))
			return false;
		if(x != x2 && z != z2) {
			valid = valid && isEmpty(x2, y, z);
			valid = valid && isEmpty(x, y, z2);
			valid = valid && isEmpty(x2, y + 1, z);
			valid = valid && isEmpty(x, y + 1, z2);
			if(y != y2) {
				valid = valid && isEmpty(x2, y2, z);
				valid = valid && isEmpty(x, y2, z2);
				valid = valid && isEmpty(x, y2, z);
				valid = valid && isEmpty(x2, y, z2);
				valid = valid && isEmpty(x2, y + 1, z2);
				valid = valid && isEmpty(x, y2 + 1, z);
				valid = false;
			}
		} else if(x != x2 && y != y2) {
			valid = valid && isEmpty(x2, y, z);
			valid = valid && isEmpty(x, y2, z);
			if(y > y2)
				valid = valid && isEmpty(x2, y + 1, z);
			else
				valid = valid && isEmpty(x, y2 + 1, z);
			valid = false;
		} else if(z != z2 && y != y2) {
			valid = valid && isEmpty(x, y, z2);
			valid = valid && isEmpty(x, y2, z);
			if(y > y2)
				valid = valid && isEmpty(x, y + 1, z2);
			else
				valid = valid && isEmpty(x, y2 + 1, z);
			valid = false;
		}
//		int nodeBlockUnder = world.getBlockIdAt(x2, y2 - 1, z2);
//		if(nodeBlockUnder == 85 || nodeBlockUnder == 107 || nodeBlockUnder == 113)
//			valid = false;
		return valid;
	}

	@Override
	public boolean canClimb(BlockLocation location) {

		Block block = world.getBlock(location);
		if(block.getMaterial().isTransparent()){
			return true;
		}
//		int id = block.getMaterial().getId();
//		if(id == 26 || id == 169) // Water / Moving Water / Ladder
//			return true;
		if(block.getMaterial() == BlockType.VINE) { // Vines (which require an adjacent solid block)
			if(!isEmpty(location.getX(), location.getY(), location.getZ() + 1) || !isEmpty(location.getX(), location.getY(), location.getZ() - 1)
					|| !isEmpty(location.getX() + 1, location.getY(), location.getZ()) || !isEmpty(location.getX() - 1, location.getY(), location.getZ()))
				return true;
		}
		return false;
	}





	private boolean isEmpty(int x, int y, int z) {
		Block block = world.getBlock(x, y, z);

		return block.getMaterial().isTransparent();
	}

	@Override
	public World getWorld() {
		return world;
	}
}
