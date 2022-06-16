package cn.kizzzy.tencent.vfs.pack;

import cn.kizzzy.tencent.IdxItem;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.pack.LeafPackage;

public class IdxPackage extends LeafPackage<IdxItem> {
    
    public IdxPackage(String root, ITree tree, String ext) {
        super(root, tree, IdxItem.class, item -> item.pkg.replace(ext, "pkg"));
    }
}
