package cn.kizzzy.tencent.vfs.pack;

import cn.kizzzy.tencent.IdxItem;
import cn.kizzzy.vfs.IFileSaver;
import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;
import cn.kizzzy.vfs.pack.AbstractPackage;
import cn.kizzzy.vfs.streamable.FileStreamable;
import cn.kizzzy.vfs.tree.Leaf;

public class IdxPackage extends AbstractPackage {
    
    public IdxPackage(String root, ITree tree) {
        super(root, tree);
    }
    
    @Override
    public boolean exist(String path) {
        return tree.getLeaf(path) != null;
    }
    
    @Override
    protected IStreamable getStreamableImpl(String path) {
        Leaf leaf = tree.getLeaf(path);
        if (leaf == null || !(leaf.item instanceof IdxItem)) {
            return null;
        }
        
        IdxItem idxItem = (IdxItem) leaf.item;
        
        String fullPath = Separator.FILE_SEPARATOR.combine(root, dealWithPkgName(idxItem.pkg));
        if (idxItem.getSource() == null) {
            idxItem.setSource(new FileStreamable(fullPath));
        }
        
        return idxItem;
    }
    
    @Override
    protected <T> boolean saveImpl(String path, T data, IFileSaver<T> saver) throws Exception {
        return false;
    }
    
    protected String dealWithPkgName(String pkg) {
        return pkg;
    }
}
