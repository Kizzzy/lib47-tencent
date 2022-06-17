package cn.kizzzy.tencent.vfs.handler;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.IFullyWriter;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.IFileHandler;
import cn.kizzzy.vfs.IPackage;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IdxFileHandler implements IFileHandler<IdxFile> {
    
    private final Charset charset;
    
    public IdxFileHandler() {
        this(StandardCharsets.UTF_8);
    }
    
    public IdxFileHandler(Charset charset) {
        this.charset = charset;
    }
    
    @Override
    public IdxFile load(IPackage vfs, String path, IFullyReader reader, long size) throws Exception {
        IdxFile idx = new IdxFile(path);
        idx.magic = reader.readIntEx();
        idx.itemCount = reader.readUnsignedIntEx();
        idx.itemPosition = reader.readUnsignedIntEx();
        idx.itemSize = reader.readUnsignedIntEx();
        
        reader.seek(idx.itemPosition, SeekType.BEGIN);
        
        for (int i = 0; i < idx.itemCount; ++i) {
            IdxFile.Entry entry = new IdxFile.Entry(path);
            entry.pathLength = reader.readShortEx();
            entry.path = reader.readString(entry.pathLength, charset);
            entry.reserved01 = reader.readIntEx();
            entry.offset = reader.readUnsignedIntEx();
            entry.originSize = reader.readIntEx();
            entry.size = reader.readIntEx();
            
            idx.entryKvs.put(entry.path, entry);
        }
        return idx;
    }
    
    @Override
    public boolean save(IPackage vfs, String path, IFullyWriter writer, IdxFile data) throws Exception {
        return false;
    }
}
