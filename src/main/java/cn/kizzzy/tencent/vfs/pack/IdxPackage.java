package cn.kizzzy.tencent.vfs.pack;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.tencent.IdxItem;
import cn.kizzzy.vfs.IFileLoader;
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
    protected Object loadImpl(String path, IFileLoader<?> loader) throws Exception {
        Leaf leaf = tree.getLeaf(path);
        if (leaf == null || !(leaf.item instanceof IdxItem)) {
            return null;
        }
        
        IdxItem file = (IdxItem) leaf.item;
        
        String fullPath = Separator.FILE_SEPARATOR.combine(root, dealWithPkgName(file.pkg));
        if (file.getSource() == null) {
            file.setSource(new FileStreamable(fullPath));
        }
        
        try (IFullyReader reader = file.OpenStream()) {
            Object obj = loader.load(this, path, reader, file.originSize);
            if (obj instanceof IStreamable) {
                ((IStreamable) obj).setSource(file);
            }
            return obj;
        }
    }
    
    @Override
    protected <T> boolean saveImpl(String path, T data, IFileSaver<T> saver) throws Exception {
        return false;
    }
    
    protected String dealWithPkgName(String pkg) {
        return pkg;
    }
}
