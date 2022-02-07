package cn.kizzzy.vfs.handler;

import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.tencent.IdxItem;
import cn.kizzzy.vfs.IPackage;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IdxFileHandler extends StreamFileHandler<IdxFile> {
    
    private final Charset charset;
    
    public IdxFileHandler() {
        this(StandardCharsets.UTF_8);
    }
    
    public IdxFileHandler(Charset charset) {
        this.charset = charset;
    }
    
    @Override
    protected IdxFile loadImpl(IPackage vfs, String path, FullyReader reader) throws Exception {
        IdxFile idx = new IdxFile();
        idx.path = path;
        idx.magic = reader.readIntEx();
        idx.itemCount = reader.readUnsignedIntEx();
        idx.itemPosition = reader.readUnsignedIntEx();
        idx.itemSize = reader.readUnsignedIntEx();
        
        reader.seek(idx.itemPosition, SeekType.BEGIN);
        
        for (int i = 0; i < idx.itemCount; ++i) {
            IdxItem item = new IdxItem();
            item.pkg = path;
            
            item.pathLength = reader.readShortEx();
            item.path = reader.readString(item.pathLength, charset).toLowerCase();
            item.reserved01 = reader.readIntEx();
            item.offset = reader.readUnsignedIntEx();
            item.originSize = reader.readIntEx();
            item.size = reader.readIntEx();
            
            idx.itemKvs.put(item.path, item);
        }
        return idx;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, IdxFile data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}
