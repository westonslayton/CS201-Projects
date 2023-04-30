import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.SwingUtilities;

/**
 * Main class for the Autocomplete program.
 * 
 * @author Austin Lu
 * @author Owen Astrachan
 *
 */
public class AutocompleteMain {

	final static String BRUTE_AUTOCOMPLETE = "BruteAutocomplete";
	final static String BINARY_SEARCH_AUTOCOMPLETE = "BinarySearchAutocomplete";
	final static String HASHLIST_AUTOCOMPLETE = "HashListAutocomplete";

	/* Modify name of Autocompletor implementation as necessary */

	
	//final static String AUTOCOMPLETOR_CLASS_NAME = BRUTE_AUTOCOMPLETE;
	final static String AUTOCOMPLETOR_CLASS_NAME = BINARY_SEARCH_AUTOCOMPLETE;
	//final static String AUTOCOMPLETOR_CLASS_NAME = HASHLIST_AUTOCOMPLETE;
	
	public static void main(String[] args) throws FileNotFoundException {
		final int K = 10;

		File file = FileSelector.selectFile();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AutocompleteGUI(file.getAbsolutePath(),
					K, AUTOCOMPLETOR_CLASS_NAME).setVisible(true);
			}
		});
	}
}
