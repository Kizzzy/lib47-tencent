package cn.kizzzy.tencent;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.io.ByteArrayInputStreamReader;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.stream.HolderInputStreamGetter;

import java.util.HashMap;
import java.util.Map;

public class IdxFile {
    
    /**
     * magic number
     */
    public int magic;
    
    /**
     * item count
     */
    public long itemCount;
    
    /**
     * item start position
     */
    public long itemPosition;
    
    /**
     * item size
     */
    public long itemSize;
    
    /**
     * all items
     */
    public final Map<String, Entry> entryKvs
        = new HashMap<>();
    
    // -------------------- extra field --------------------
    
    /**
     * original file name
     */
    public String path;
    
    public IdxFile(String path) {
        this.path = path;
    }
    
    public static class Entry extends HolderInputStreamGetter {
        
        public int pathLength;
        
        public String path;
        
        public int reserved01;
        
        public long offset;
        
        public int originSize;
        
        public int size;
        
        // -------------------- extra field --------------------
        
        public String pack;
        
        public Entry(String pack) {
            this.pack = pack;
        }
        
        @Override
        public IFullyReader getInput() throws Exception {
            if (getSource() == null) {
                throw new NullPointerException("source is null");
            }
            
            IFullyReader reader = new SliceFullReader(getSource().getInput(), offset, size);
            byte[] buffer = reader.readBytes(size);
            buffer = ZlibHelper.uncompress(buffer);
            return new ByteArrayInputStreamReader(buffer);
        }
    }
}
