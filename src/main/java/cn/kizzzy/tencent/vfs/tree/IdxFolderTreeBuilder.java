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

public class IdxFolderTreeBuilder extends FolderTreeBuilder<IdxFile> {
    
    public IdxFolderTreeBuilder(String root, String folder) {
        this(getRootVfs(root), folder);
    }
    
    public IdxFolderTreeBuilder(IPackage rootVfs, String folder) {
        this(rootVfs, folder, new IdGenerator());
    }
    
    public IdxFolderTreeBuilder(IPackage rootVfs, String folder, IdGenerator idGenerator) {
        super(rootVfs, folder, IdxFile.class, idGenerator);
    }
    
    @Override
    protected boolean acceptLeaf(Leaf leaf) {
        return leaf.path.endsWith(".pkg");
    }
    
    @Override
    protected ITree buildTree(IdxFile idxFile, IdGenerator idGenerator) {
        return new IdxTreeBuilder(idxFile, idGenerator).build();
    }
    
    private static IPackage getRootVfs(String root) {
        IPackage vfs = new FilePackage(root, new FileTreeBuilder(root).build());
        vfs.addHandler(IdxFile.class, new IdxFileHandler());
        return vfs;
    }
}
