package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: ß
        for (CharSequence term : terms) {
            if (term != null && term.length() > 0) {
                overallRoot = insert(overallRoot, term, 0);
            }
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: ß
        List<CharSequence> results = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) return results;

        Node node = getNode(overallRoot, prefix, 0);
        if (node == null) return results;

        if (node.isTerm) {
            results.add(prefix);
        }

        collect(node.mid, new StringBuilder(prefix), results);
        return results;
    }

    // Additional methods required by allMatches, I couldn't find any that were already done
    private Node insert(Node node, CharSequence word, int index) {
        char c = word.charAt(index);
        if (node == null) {
            node = new Node(c);
        }

        if (c < node.data) {
            node.left = insert(node.left, word, index);
        } else if (c > node.data) {
            node.right = insert(node.right, word, index);
        } else {
            if (index == word.length() - 1) {
                node.isTerm = true;
            } else {
                node.mid = insert(node.mid, word, index + 1);
            }
        }
        return node;
    }

    private Node getNode(Node node, CharSequence prefix, int index) {
        if (node == null) return null;

        char c = prefix.charAt(index);
        if (c < node.data) {
            return getNode(node.left, prefix, index);
        } else if (c > node.data) {
            return getNode(node.right, prefix, index);
        } else {
            if (index == prefix.length() - 1) {
                return node;
            } else {
                return getNode(node.mid, prefix, index + 1);
            }
        }
    }

    private void collect(Node node, StringBuilder prefix, List<CharSequence> results) {
        if (node == null) return;

        collect(node.left, prefix, results);

        prefix.append(node.data);
        if (node.isTerm) {
            results.add(prefix.toString());
        }
        collect(node.mid, prefix, results);
        prefix.deleteCharAt(prefix.length() - 1);

        collect(node.right, prefix, results);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
