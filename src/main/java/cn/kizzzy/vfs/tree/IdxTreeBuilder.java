package cn.kizzzy.vfs.tree;

import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.tencent.IdxItem;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;

public class IdxTreeBuilder extends TreeBuilderAdapter<IdxFile, IdxItem> {
    
    private final IdxFile idxFile;
    
    public IdxTreeBuilder(IdxFile idxFile) {
        this(idxFile, new IdGenerator());
    }
    
    public IdxTreeBuilder(IdxFile idxFile, IdGenerator idGenerator) {
        super(Separator.BACKSLASH_SEPARATOR_LOWERCASE, idGenerator);
        this.idxFile = idxFile;
    }
    
    @Override
    public ITree build() {
        return buildImpl(idxFile, new Helper<IdxFile, IdxItem>() {
            
            @Override
            public String idxPath(IdxFile idxFile) {
                return idxFile.path;
            }
            
            @Override
            public Iterable<IdxItem> entries(IdxFile idxFile) {
                return idxFile.itemKvs.values();
            }
            
            @Override
            public String itemPath(IdxItem item) {
                return item.path;
            }
        });
    }
}
