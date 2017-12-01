package org.terasology.lavalandgenerator;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

public class TerrainRasterizer implements WorldRasterizer {
    Block stone, hardStone, mantleStone;
    
    @Override
    public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        hardStone = CoreRegistry.get(BlockManager.class).getBlock("Core:HardStone");
        mantleStone = CoreRegistry.get(BlockManager.class).getBlock("Core:MantleStone");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        for(Vector3i position : chunkRegion.getRegion()) {
            float surfaceHeight = surfaceHeightFacet.getWorld(position.x,position.z);
            if(position.y < 3 && position.y < surfaceHeight) {//Bottom of the world
                chunk.setBlock(ChunkMath.calcBlockPos(position),mantleStone);
            }
            if(surfaceHeight - position.y > 5) {    //Beneath Surface
                chunk.setBlock(ChunkMath.calcBlockPos(position),hardStone);
            }
            else if(position.y < surfaceHeight) {   //Surface
                chunk.setBlock(ChunkMath.calcBlockPos(position),stone);
            }
        }
    }

}
