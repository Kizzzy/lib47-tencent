package cn.kizzzy.tencent.vfs.tree;

import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.TreeBuilderAdapter;

public class IdxTreeBuilder extends TreeBuilderAdapter<IdxFile, IdxFile.Entry> {
    
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
        return buildImpl(idxFile, new Helper<IdxFile, IdxFile.Entry>() {
            
            @Override
            public String idxPath(IdxFile idxFile) {
                return idxFile.path;
            }
            
            @Override
            public Iterable<IdxFile.Entry> entries(IdxFile idxFile) {
                return idxFile.entryKvs.values();
            }
            
            @Override
            public String itemPath(IdxFile.Entry item) {
                return item.path;
            }
        });
    }
}
