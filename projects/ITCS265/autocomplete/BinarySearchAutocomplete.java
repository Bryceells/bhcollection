package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: ß
        elements.addAll(terms);
        elements.sort(Comparator.comparing(CharSequence::toString));
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: ß
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) return result;

        int i = Collections.binarySearch(elements, prefix, Comparator.comparing(CharSequence::toString));
        int start = (i < 0) ? -(i + 1) : i;

        for (int j = start; j < elements.size(); j++) {
            CharSequence term = elements.get(j);
            if (Autocomplete.isPrefixOf(prefix, term)) {
                result.add(term);
            } else {
                break;
            }
        }
        return result;
    }
}