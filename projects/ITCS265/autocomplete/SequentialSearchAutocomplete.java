package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: ß Code written by Bryce
        elements.addAll(terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: ß Code written by Bryce
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) return result;

        for (CharSequence term : elements) {
            if (Autocomplete.isPrefixOf(prefix, term)) {
                result.add(term);
            }
        }
        return result;
    }
}
