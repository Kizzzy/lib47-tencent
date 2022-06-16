package cn.kizzzy.tencent;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.io.ByteArrayInputStreamReader;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.IInputStreamGetter;

public class IdxItem implements IInputStreamGetter {
    public int pathLength;
    public String path;
    public int reserved01;
    public long offset;
    public int originSize;
    public int size;
    
    public String pkg;
    private IInputStreamGetter source;
    
    @Override
    public IInputStreamGetter getSource() {
        return source;
    }
    
    @Override
    public void setSource(IInputStreamGetter source) {
        this.source = source;
    }
    
    @Override
    public IFullyReader getInput() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        
        IFullyReader reader = new SliceFullReader(source.getInput(), offset, size);
        byte[] buffer = reader.readBytes(size);
        buffer = ZlibHelper.uncompress(buffer);
        return new ByteArrayInputStreamReader(buffer);
    }
}
