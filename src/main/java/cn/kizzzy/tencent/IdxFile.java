package cn.kizzzy.tencent;

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
     * original file name
     */
    public String path;
    
    /**
     * all items
     */
    public final Map<String, IdxItem> itemKvs
        = new HashMap<>();
}
