package cn.kizzzy.tencent;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.io.ByteArrayInputStreamReader;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.IStreamable;

public class IdxItem implements IStreamable {
    public int pathLength;
    public String path;
    public int reserved01;
    public long offset;
    public int originSize;
    public int size;
    
    public String pkg;
    private IStreamable source;
    
    @Override
    public IStreamable getSource() {
        return source;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.source = source;
    }
    
    @Override
    public FullyReader OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        FullyReader reader = new SliceFullReader(source.OpenStream(), offset, size);
        byte[] buffer = new byte[size];
        reader.read(buffer);
        return new SliceFullReader(new ByteArrayInputStreamReader(ZlibHelper.uncompress(buffer)));
    }
}
