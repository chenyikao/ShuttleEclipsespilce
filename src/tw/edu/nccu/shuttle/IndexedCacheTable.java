/**
 * 
 */
package tw.edu.nccu.shuttle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @aspect Caching
 * 
 * @author Kao, Chen-yi
 *
 */
public class IndexedCacheTable<Index, CachedObject> {

	// TODO: static private class NoSuchAdapterException extends Exception {
	// }?
	//	
	static final public NoSuchElementException NOT_CACHED_EXCEPTION = 
		new NoSuchElementException();

	
	
	/**
	 * An efficient table for monitoring/re-accessing matched objects (for
	 * accessing monitored ME's)
	 */
	private Hashtable<Index, Set<CachedObject>> cacheTable;

	
	
	public IndexedCacheTable() {
		cacheTable = new Hashtable<Index, Set<CachedObject>>();
	}
	
	@SuppressWarnings("unchecked")
	public IndexedCacheTable(IndexedCacheTable<Object, Boolean> tableToClone) {
		cacheTable = (Hashtable<Index, Set<CachedObject>>) tableToClone.cacheTable.clone();
	}

	
	
	/**
	 * @param index
	 * @return The first (and supposedly the only) cached object of the indexed cached set.
	 * @throws NoSuchElementException
	 */
	public CachedObject getCache(Index index) throws NoSuchElementException {
		// if the inner cache table has the set, then it SHOULD NOT be empty
		return getCacheSet(index).iterator().next();
	}

	/**
	 * @param index
	 * @return Monitored cache {@link Set}
	 * @throws NoSuchElementException
	 */
	public Set<CachedObject> getCacheSet(Index index) throws NoSuchElementException {
		Set<CachedObject> indexedSet = cacheTable.get(index);
		// if the cache table has the set, then return it
		if (indexedSet == null) throw NOT_CACHED_EXCEPTION;
		else return indexedSet;
	}
	
	public void indexAndCache(Index index, CachedObject object) {
		Set<CachedObject> indexedSet = cacheTable.get(index);
		if (indexedSet == null) {
			// {@link HashSet} is not thread-safe and needs synchronized for public usage
			Set<CachedObject> newCacheSet = Collections.synchronizedSet(new HashSet<CachedObject>());
			newCacheSet.add(object);
			cacheTable.put(index, newCacheSet);
		} else {
			indexedSet.add(object);
		}
	}

	/**
	 * @param index
	 * @param set - For possible retrieval in the future the input set will be synchronized. 
	 */
	public void indexAndCacheSet(Index index, Set<CachedObject> set) {
		cacheTable.put(index, Collections.synchronizedSet(set));
	}

}
