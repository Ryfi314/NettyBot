/*
 * Ryfi  2022.
 */

/*
 * Ryfi  2021.
 */

package ru.ryfi.bot.world;


import lombok.Getter;
import ru.ryfi.bot.world.block.Block;
import ru.ryfi.bot.world.block.BlockType;
import ru.ryfi.bot.world.chunk.Chunk;
import ru.ryfi.bot.world.chunk.Column;

import ru.ryfi.bot.world.entity.Entity;
import ru.ryfi.bot.world.entity.PlayerEntity;
import ru.ryfi.bot.world.pathfinding.EuclideanHeuristic;
import ru.ryfi.bot.world.pathfinding.PathSearchProvider;
import ru.ryfi.bot.world.pathfinding.SimpleWorldPhysics;
import ru.ryfi.bot.world.pathfinding.astar.AStarPathSearchProvider;
import ru.ryfi.bot.world.position.ChunkLocation;
import ru.ryfi.bot.world.position.BlockLocation;

import java.util.*;
import java.util.jar.Manifest;

public class World {
   private HashMap<ChunkLocation, Column> columns = new HashMap<>();
   @Getter
   private HashMap<Integer,Entity> entities;

   public void addEntity(Entity e){
      entities.put(e.getId(),e);
   }
   public void removeEntity(int id){
      entities.remove(id);
   }
   public Entity getEntityById(int id){
      return entities.get(id);
   }
   public PlayerEntity getPlayerEntityByUUID(UUID uuid){
      for(Entity entity : entities.values()){
         if(entity instanceof PlayerEntity entity1){
            if(entity1.getUuid().equals(uuid)){
               return entity1;
            }
         }
      }
      return null;
   }

   public void loadChunk(ChunkLocation location , Column chunk){
      columns.put(location,chunk);
   }
   public void unloadChunk(ChunkLocation location){
      columns.remove(location);
   }

   @Getter
   private PathSearchProvider pathFinder;
   public World(){
      entities = new HashMap<>();
      pathFinder = new AStarPathSearchProvider(new EuclideanHeuristic(),new SimpleWorldPhysics(this));
   }

   public void tick(){
      for(Entity entity : entities.values()){
         if(!entity.isDead()) {
            entity.update();
         }
      }
   }

   public void setBlock(BlockLocation pos, int state) {
      try {
         int bx = toSectionRelativeCoordinate(pos.toBlockX());
         int by = toSectionRelativeCoordinate(pos.toBlockY());
         int bz = toSectionRelativeCoordinate(pos.toBlockZ());
         int chunkX = pos.toBlockX() >> 4;
         int chunkY = pos.toBlockY() >> 4;
         int chunkZ = pos.toBlockZ() >> 4;

         Column column = getChunk(new ChunkLocation(chunkX, chunkZ));
         Chunk cc = getChunkSelection(column,chunkY);
         cc.set(bx, by, bz, state);
        // System.out.println(state + "  " + pos.getX() + " "+ pos.getY() + " "+ pos.getZ() + " ");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   public void setBlock(BlockLocation pos, Block block) {
      try {
         int bx = toSectionRelativeCoordinate(pos.toBlockX());
         int by = toSectionRelativeCoordinate(pos.toBlockY());
         int bz = toSectionRelativeCoordinate(pos.toBlockZ());
         int chunkX = pos.toBlockX() >> 4;
         int chunkY = pos.toBlockY() >> 4;
         int chunkZ = pos.toBlockZ() >> 4;

         Column column = getChunk(new ChunkLocation(chunkX, chunkZ));
         Chunk cc = getChunkSelection(column,chunkY);
         cc.set(bx, by, bz, cc.getPalette().idToState(block.getMaterial().getId()));
         //System.out.println(cc.getPalette().stateToId(state) + "  " + pos.getX() + " "+ pos.getY() + " "+ pos.getZ() + " ");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public Block getBlock(BlockLocation pos) {
      try {
         int bx = toSectionRelativeCoordinate(pos.toBlockX());
         int by = toSectionRelativeCoordinate(pos.toBlockY());
         int bz = toSectionRelativeCoordinate(pos.toBlockZ());
         int chunkX = (int)pos.getX() >> 4;
         int chunkY = (int)pos.getY() >> 4;
         int chunkZ = (int)pos.getZ() >> 4;

         Column column = columns.get(new ChunkLocation(chunkX, chunkZ));
         Chunk cc = column.getChunks()[chunkY];
            if (cc == null) return new Block(pos, BlockType.AIR);
            int state = cc.get(bx, by, bz);
          //  System.out.println(state);
         return new Block(pos, BlockType.getByStateId(state));
      } catch (Exception e) {
         //e.printStackTrace();
         return new Block(pos, BlockType.AIR);
      }
   }

   public Block getBlock(int x,int y,int z) {
      try {
         int bx = toSectionRelativeCoordinate(x);
         int by = toSectionRelativeCoordinate(y);
         int bz = toSectionRelativeCoordinate(z);
         int chunkX = x >> 4;
         int chunkY = y >> 4;
         int chunkZ = z >> 4;

         Column column = columns.get(new ChunkLocation(chunkX, chunkZ));
         Chunk cc = column.getChunks()[chunkY];
         if (cc == null) return new Block(new BlockLocation(x, y, z), BlockType.AIR);
         int state = cc.get(bx, by, bz);
         //  System.out.println(state);
         return new Block(new BlockLocation(x, y, z), BlockType.getByStateId(state));
      } catch (Exception e) {
         //e.printStackTrace();
         return new Block(new BlockLocation(x, y, z), BlockType.AIR);
      }
   }


   public int getBlockIdAt(int x,int y,int z) {
      try {
         int bx = toSectionRelativeCoordinate(x);
         int by = toSectionRelativeCoordinate(y);
         int bz = toSectionRelativeCoordinate(z);
         int chunkX = x >> 4;
         int chunkY = y >> 4;
         int chunkZ = z >> 4;

         Column column = columns.get(new ChunkLocation(chunkX, chunkZ));
         Chunk cc = column.getChunks()[chunkY];
         if (cc == null) return 0;
         int state = cc.get(bx, by, bz);
         //  System.out.println(state);
         return BlockType.getByStateId(state).getId();
      } catch (Exception e) {
         //e.printStackTrace();
         return 0;
      }
   }
   public int getBlockIdAt(BlockLocation location) {
      try {
         int bx = toSectionRelativeCoordinate(location.getX());
         int by = toSectionRelativeCoordinate(location.getY());
         int bz = toSectionRelativeCoordinate(location.getZ());
         int chunkX = location.getX() >> 4;
         int chunkY = location.getY() >> 4;
         int chunkZ = location.getZ() >> 4;

         Column column = columns.get(new ChunkLocation(chunkX, chunkZ));
         Chunk cc = column.getChunks()[chunkY];
         if (cc == null) return 0;
         int state = cc.get(bx, by, bz);
         //  System.out.println(state);
         return BlockType.getByStateId(state).getId();
      } catch (Exception e) {
         //e.printStackTrace();
         return 0;
      }
   }



   public Column getChunk(ChunkLocation loc){
      Column column = columns.get(loc);
      return column;
   }
   public Chunk getChunkSelection(Column column,int chunkY){
      Chunk cc = column.getChunks()[chunkY];
      if(cc == null){
         column.getChunks()[chunkY] = new Chunk();
         cc = column.getChunks()[chunkY];
      }
      return cc;
   }
   public boolean isBlockLoaded(BlockLocation b) {
      int chunkX = (int) (b.getX() / 16.0);
      int chunkZ = (int) (b.getZ() / 16.0);
      ChunkLocation coords = new ChunkLocation(chunkX, chunkZ);
      return columns.containsKey(coords);
   }
   public int toChunkPosition(int xyz){

      return xyz / 16;
   }
   public int toSectionRelativeCoordinate(int xyz) {
      xyz %= 16;
      if (xyz < 0) {
         xyz += 16;
      }
      return xyz;
   }





}
