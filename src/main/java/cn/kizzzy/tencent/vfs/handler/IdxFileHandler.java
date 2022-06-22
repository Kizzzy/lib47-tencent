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
        reader.setLittleEndian(true);
        
        IdxFile idx = new IdxFile(path);
        idx.magic = reader.readInt();
        idx.itemCount = reader.readUnsignedInt();
        idx.itemPosition = reader.readUnsignedInt();
        idx.itemSize = reader.readUnsignedInt();
        
        reader.seek(idx.itemPosition, SeekType.BEGIN);
        
        for (int i = 0; i < idx.itemCount; ++i) {
            IdxFile.Entry entry = new IdxFile.Entry(path);
            entry.pathLength = reader.readShort();
            entry.path = reader.readString(entry.pathLength, charset);
            entry.reserved01 = reader.readInt();
            entry.offset = reader.readUnsignedInt();
            entry.originSize = reader.readInt();
            entry.size = reader.readInt();
            
            idx.entryKvs.put(entry.path, entry);
        }
        return idx;
    }
    
    @Override
    public boolean save(IPackage vfs, String path, IFullyWriter writer, IdxFile data) throws Exception {
        return false;
    }
}
