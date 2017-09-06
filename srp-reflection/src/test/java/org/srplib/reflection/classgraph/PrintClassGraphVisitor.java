package org.srplib.reflection.classgraph;

import org.srplib.visitor.NodePath;

/**
 * @author Q-APE
 */
class PrintClassGraphVisitor implements ClassGraphVisitor<ClassGraphNode> {

    @Override
    public void visit(NodePath<ClassGraphNode> path) {
        for (ClassGraphNode node : path) {
            String fieldName = getFieldName(node);
            System.out.printf("field: '%s' class: %s / ", fieldName, node.getType());
        }
        System.out.println();
    }

//    @Override
//    public ClassGraphNode createNode(Class type, Field field) {
//        return new ClassGraphNode(type, field);
//    }

    private String getFieldName(ClassGraphNode node) {
        return node.getField() != null ? node.getField().getName() : "none";
    }

    @Override
    public ClassGraphNode resolveNode(ClassGraphNode node) {
        return node;
    }
}
