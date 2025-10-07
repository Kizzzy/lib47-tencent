package cn.kizzzy.tencent.vfs.tree;

import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.tencent.vfs.handler.IdxFileHandler;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.FileTreeBuilder;
import cn.kizzzy.vfs.tree.FolderTreeBuilder;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.Leaf;

public class IdxFolderTreeBuilder extends FolderTreeBuilder {
    
    public IdxFolderTreeBuilder(String root, String folder) {
        this(root, folder, new IdGenerator());
    }
    
    public IdxFolderTreeBuilder(String root, String folder, IdGenerator idGenerator) {
        super(root, folder, idGenerator);
    }
    
    @Override
    protected ITree buildTree(IPackage rootVfs, Leaf leaf, IdGenerator idGenerator) {
        if (leaf.path.endsWith(".pkg")) {
            IdxFile idxFile = rootVfs.load(leaf.path, IdxFile.class);
            if (idxFile != null) {
                return new IdxTreeBuilder(idxFile, idGenerator).build();
            }
        }
        return null;
    }
    
    @Override
    protected IPackage getRootVfs(String root) {
        IPackage vfs = new FilePackage(root, new FileTreeBuilder(root).build());
        vfs.addHandler(IdxFile.class, new IdxFileHandler());
        return vfs;
    }
}
