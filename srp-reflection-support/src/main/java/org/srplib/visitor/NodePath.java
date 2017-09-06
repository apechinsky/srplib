package org.srplib.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Path structure.
 *
 * <p>Structure is natively recursive: contains current node and a reference to parent path.</p>
 *
 * <p>Useful for recursive algorithms such as tree traversal.</p>
 *
 * @author Anton Pechinsky
 */
public class NodePath<T> implements Iterable<T> {

    private final NodePath<T> parent;

    private final T current;

    private NodePath(NodePath<T> parent, T node) {
        this.parent = parent;
        this.current = node;
    }

    public NodePath(T node) {
        this(null, node);
    }

    public T getCurrent() {
        return current;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public NodePath<T> getParent() {
        return parent;
    }

    public NodePath<T> add(T node) {
        return new NodePath<>(this, node);
    }

    public Iterator<T> iterator() {
        return new MyIterator<>(this);
    }

    public String format(String separator) {
        List<T> nodes = getNodes();

        Collections.reverse(nodes);

        StringBuilder sb = new StringBuilder();
        for (Iterator<T> iterator = nodes.iterator(); iterator.hasNext(); ) {
            sb.append(iterator.next());
            sb.append(iterator.hasNext() ? separator : "");
        }

        return sb.toString();
    }

    private List<T> getNodes() {
        List<T> nodes = new ArrayList<>();
        for (T node : this) {
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public String toString() {
        return format(" -> ");
    }

    private static class MyIterator<T> implements Iterator<T> {

        private NodePath<T> path;

        public MyIterator(NodePath<T> path) {
            this.path = path;
        }

        @Override
        public boolean hasNext() {
            return path != null;
        }

        @Override
        public T next() {
            T current = path.getCurrent();
            path = path.getParent();
            return current;
        }
    }
}
