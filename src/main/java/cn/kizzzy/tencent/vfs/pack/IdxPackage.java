package cn.kizzzy.tencent.vfs.pack;

import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.IStreamGetterFactory;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.pack.LeafPackage;

public class IdxPackage extends LeafPackage<IdxFile.Entry> {
    
    public IdxPackage(ITree tree, IStreamGetterFactory factory, String ext) {
        super(tree, factory, IdxFile.Entry.class, item -> item.pack.replace(ext, "pkg"));
    }
}
