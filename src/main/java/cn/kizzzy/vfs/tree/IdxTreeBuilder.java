package cn.kizzzy.vfs.tree;

import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.tencent.IdxItem;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;

public class IdxTreeBuilder extends TreeBuilder {
    
    private final IdxFile idx;
    
    public IdxTreeBuilder(IdxFile idx, IdGenerator idGenerator) {
        super(Separator.BACKSLASH_SEPARATOR_LOWERCASE, idGenerator);
        this.idx = idx;
    }
    
    public ITree build() {
        Root root = new Root(idGenerator.getId(), idx.path);
        for (IdxItem file : idx.itemKvs.values()) {
            listImpl(root, root, file);
        }
        return new Tree(root, separator);
    }
    
    private void listImpl(Root root, Node parent, IdxItem item) {
        String[] names = separator.split(item.path);
        int i = 0;
        for (String name : names) {
            Node child = parent.children.get(name);
            if (child == null) {
                if (i == names.length - 1) {
                    Leaf leaf = new Leaf(idGenerator.getId(), name, root.name, item.path, item);
                    root.fileKvs.put(leaf.path, leaf);
                    child = leaf;
                } else {
                    child = new Node(idGenerator.getId(), name);
                }
                root.folderKvs.put(child.id, child);
                parent.children.put(name, child);
            }
            parent = child;
            i++;
        }
    }
}
